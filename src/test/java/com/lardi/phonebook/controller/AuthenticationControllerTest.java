package com.lardi.phonebook.controller;

import com.lardi.phonebook.model.User;
import com.lardi.phonebook.repository.IUserRepository;
import com.lardi.phonebook.service.UserService;
import com.lardi.phonebook.validator.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static com.lardi.phonebook.controller.Util.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    private MockMvc mockMvcWithMockClass;
    private AuthenticationController authenticationControllerMock;
    private UserService userServiceMock;
    private IUserRepository userRepositoryMock= mock(IUserRepository.class);
    private UserValidator userValidatorMock;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setup() {
        this.userServiceMock = new UserService(
                this.userRepositoryMock,
                this.bCryptPasswordEncoder);
        this.userValidatorMock = new UserValidator(this.userServiceMock);
        this.authenticationControllerMock = new AuthenticationController(this.userServiceMock, this.userValidatorMock);
        this.mockMvcWithMockClass = MockMvcBuilders.standaloneSetup(this.authenticationControllerMock).build();
    }

    @Test
    public void getLoginPageTest() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(status()
                        .isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("/loginPage"))
                .andExpect(content()
                        .string(getLoginPage ()));
    }

    @Test
    public void getRegisterPage_WithModelUserForm_NewUser_Test() throws Exception {
        this.mockMvc.perform(get("/register"))
                .andExpect(status()
                        .isOk())
                .andExpect(view().name("/registerPage"))
                .andExpect(content()
                        .string(getRegisterPage ()));
    }

    @Test
    public void postRegisterPage_WithModelUserForm_UserValidateOk_RedirectTologin_UserAddOk_Test() throws Exception {
        User user = getUserValidOk(null);
        this.mockMvc.perform(post("/register")
                .flashAttr("userForm", user))
                .andExpect(status()
                        .isFound())
                .andExpect(view().name("redirect:/login"));

        String responseJson = mockMvc
                .perform(get("/test/user/" + user.getLogin()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        User userAdd = mapFromJson(responseJson, User.class);
        assertNotNull(userAdd);
        assertEquals(user.getLogin(), userAdd.getLogin());
    }

    @Test
    public void postRegisterPage_WithModelUserForm_UserValidateBad4_ReturnErrors4_ReturnToRegisterPage_Test() throws Exception {
        User user =  getUserValidBad4();
        MvcResult responseJson =  this.mockMvcWithMockClass.perform(post("/register")
                .flashAttr("userForm", user))
                .andExpect(status()
                        .isOk())
                .andExpect(view().name("/registerPage"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        ModelAndView modelView = responseJson.getModelAndView();
        Map<String, Object> model = modelView.getModel();
        User userAfterValid = (User) model.get("userForm");
        BindingResult resultValid = (BindingResult) model.get("org.springframework.validation.BindingResult.userForm");
        List errors = resultValid.getAllErrors();
        assertTrue(errors.size()==4);
    }

    @Test
    public void postRegisterPage_WithModelUserForm_UserValidateBad1_ReturnErrors1_ReturnToRegisterPage_Test() throws Exception {
        User user =  getUserValidOk(null);
        user.setLogin("nickA");
        when(this.userRepositoryMock.existsUserByLogin(user.getLogin())).thenReturn(true);
        ModelAndView modelView = this.mockMvcWithMockClass.perform(post("/register")
                .flashAttr("userForm", user))
                .andExpect(status()
                        .isOk())
                .andExpect(view().name("/registerPage"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getModelAndView();
        Map<String, Object> model = modelView.getModel();
        User userAfterValid = (User) model.get("userForm");
        assertEquals(user, userAfterValid);
        List errors =  ((BindingResult) model.get("org.springframework.validation.BindingResult.userForm")).getAllErrors();
        assertTrue(errors.size()==1);
        String valCode1 = ((FieldError) errors.get(0)).getCodes()[0];
        assertEquals(valCode1,"Duplicate.userForm.login.userForm.login");
    }

    private String getLoginPage () {
        return "<!DOCTYPE html>\r\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n" +
                "<head>\r\n" +
                "    <title>Phonebook Login </title>\r\n" +
                "    <div>\r\n" +
                "            <!-- this is header-css -->\r\n" +
                "            <link rel=\"stylesheet\" href=\"/webjars/bootstrap/3.3.7/css/bootstrap.min.css\"/>\r\n" +
                "            <link rel=\"stylesheet\" href=\"../../css/main.css\"/>\r\n" +
                "<!--            <link rel=\"stylesheet\" href=\"../../css/jquery.alerts.css\" />-->\r\n" +
                "    </div>\r\n" +
                "</head>\r\n" +
                "<body>\r\n" +
                "     <div>\r\n" +
                "    <!-- this is header -->\r\n" +
                "    <nav class=\"navbar navbar-inverse\">\r\n" +
                "        <div class=\"container\">\r\n" +
                "            <div class=\"navbar-header\">\r\n" +
                "                <a class=\"navbar-brand\" href=\"/\">Phonebook</a>\r\n" +
                "            </div>\r\n" +
                "            <div id=\"navbar\" class=\"collapse navbar-collapse\">\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/home\">Home</a></li>\r\n" +
                "                </ul>\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/login\">Login</a></li>\r\n" +
                "                </ul>\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/register\">Registration</a></li>\r\n" +
                "                </ul>\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/about\">About</a></li>\r\n" +
                "                </ul>\r\n" +
                "            </div>\r\n" +
                "        </div>\r\n" +
                "    </nav>\r\n" +
                "</div>\r\n" +
                "    <!-- /.container -->\r\n" +
                "    <div class=\"container\">\r\n" +
                "        <div class=\"row\" style=\"margin-top:20px\">\r\n" +
                "            <div class=\"col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3\">\r\n" +
                "                <form action=\"/login\" method=\"post\">\r\n" +
                "                    <fieldset>\r\n" +
                "                        <h1>Please Sign In</h1>\r\n" +
                "                        \r\n" +
                "                        \r\n" +
                "                        <div class=\"form-group\">\r\n" +
                "                            <input type=\"text\" name=\"login\" id=\"login\" class=\"form-control input-lg\"\r\n" +
                "                                   placeholder=\"Login\" required=\"true\" autofocus=\"true\"/>\r\n" +
                "                        </div>\r\n" +
                "                        <div class=\"form-group\">\r\n" +
                "                            <input type=\"password\" name=\"password\" id=\"password\" class=\"form-control input-lg\"\r\n" +
                "                                   placeholder=\"Password\" required=\"true\"/>\r\n" +
                "                        </div>\r\n" +
                "                        <div class=\"row\">\r\n" +
                "                            <div class=\"col-xs-12 col-sm-12 col-md-12\">\r\n" +
                "                                <div  class=\"pull-right\">\r\n" +
                "                                    <input type=\"submit\" class=\"btn btn-lg btn-primary\" value=\"Sign In\"/>\r\n" +
                "                                </div>\r\n" +
                "                                <div>\r\n" +
                "                                    <a href=\"/register\" id=\"register\" name=\"register\" class=\"btn btn-lg btn-info\">Registration</a>\r\n" +
                "                                    <input type=\"button\" class=\"btn btn-lg btn-warning\" name=\"reset\" value=\"Reset\" id=\"resetLogIn\" onclick=\"customReset();\"/>\r\n" +
                "                                </div>\r\n" +
                "                            </div>\r\n" +
                "                        </div>\r\n" +
                "                    </fieldset>\r\n" +
                "                </form>\r\n" +
                "            </div>\r\n" +
                "        </div>\r\n" +
                "    </div>\r\n" +
                "    <div>\r\n" +
                "\r\n" +
                "    <div class=\"container\">\r\n" +
                "        <footer>\r\n" +
                "            <!-- this is footer -->\r\n" +
                "            &copy; 2019 AS21.com\r\n" +
                "            <span sec:authorize=\"isAuthenticated()\"> |\r\n" +
                "<!--                Logged user: <span sec:authentication=\"name\"></span> |-->\r\n" +
                "<!--                Roles: <span sec:authentication=\"principal.authorities\"></span> |-->\r\n" +
                "                <a href=\"/logout\">Sign Out</a>\r\n" +
                "            </span>\r\n" +
                "        </footer>\r\n" +
                "    </div>\r\n" +
                "</div>\r\n" +
                "     <script src=\"/js/reset.js\"></script>\r\n" +
                "</body>\r\n" +
                "</html>\r\n";
    }

    private String getRegisterPage () {
        return  "<!DOCTYPE html>\r\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n" +
                "<head>\r\n" +
                "    <title>Phonebook Registration </title>\r\n" +
                "    <div>\r\n" +
                "            <!-- this is header-css -->\r\n" +
                "            <link rel=\"stylesheet\" href=\"/webjars/bootstrap/3.3.7/css/bootstrap.min.css\"/>\r\n" +
                "            <link rel=\"stylesheet\" href=\"../../css/main.css\"/>\r\n" +
                "<!--            <link rel=\"stylesheet\" href=\"../../css/jquery.alerts.css\" />-->\r\n" +
                "    </div>\r\n" +
                "</head>\r\n" +
                "<body>\r\n" +
                "    <div>\r\n" +
                "    <!-- this is header -->\r\n" +
                "    <nav class=\"navbar navbar-inverse\">\r\n" +
                "        <div class=\"container\">\r\n" +
                "            <div class=\"navbar-header\">\r\n" +
                "                <a class=\"navbar-brand\" href=\"/\">Phonebook</a>\r\n" +
                "            </div>\r\n" +
                "            <div id=\"navbar\" class=\"collapse navbar-collapse\">\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/home\">Home</a></li>\r\n" +
                "                </ul>\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/login\">Login</a></li>\r\n" +
                "                </ul>\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/register\">Registration</a></li>\r\n" +
                "                </ul>\r\n" +
                "                <ul class=\"nav navbar-nav\">\r\n" +
                "                    <li class=\"active\"><a href=\"/about\">About</a></li>\r\n" +
                "                </ul>\r\n" +
                "            </div>\r\n" +
                "        </div>\r\n" +
                "    </nav>\r\n" +
                "</div>\r\n" +
                "    <!--container - ограничивает блок максимальной шириной около 1200px-->\r\n" +
                "    <!--container-fluid - растягивает блок (контент) по всей ширине экрана (рабочей области браузера)-->\r\n" +
                "    <div class=\"container-fluid\">\r\n" +
                "        <!-- /.container -->\r\n" +
                "        <!--    .col-xs-* маленькие устройства (мобильные)-->\r\n" +
                "        <!--    .col-md-* сроедние устройства (планшеты)-->\r\n" +
                "        <!--    .col-sm-* большие устройства (настольные)-->\r\n" +
                "        <!--    col-xs-12 мобильные: одна колонка 12 (на всю ширину) -->\r\n" +
                "        <!--    col-md-6  планшеты: первая колонка 6, вторая колонка 6 -->\r\n" +
                "        <!--    col-md-offset-3 планшеты: 3 отступ + 6 колнка + 3 отступ -->\r\n" +
                "        <!--    col-sm-8  настольные: первая колнка 8, вторая колонка 4 -->\r\n" +
                "        <!--    col-sm-offset-2 настольные: 2отступ + 8 колнка + 2 отступ-->\r\n" +
                "        <div class=\"row\" style=\"margin-top:20px\">\r\n" +
                "            <div class=\"col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3\">\r\n" +
                "                <form action=\"/register\" method=\"post\">\r\n" +
                "                    <fieldset>\r\n" +
                "                        <h1>Please Registration</h1>\r\n" +
                "                        <div class=\"form-group\">\r\n" +
                "                            <input type=\"text\" name=\"login\" id=\"login\" class=\"form-control input-lg\"\r\n" +
                "                                   placeholder=\"Login\" autofocus=\"true\"/>\r\n" +
                "                        </div>\r\n" +
                "                        \r\n" +
                "\r\n" +
                "                        <div class=\"form-group\">\r\n" +
                "                            <input type=\"text\" name=\"pib\" id=\"pib\" class=\"form-control input-lg\"\r\n" +
                "                                   placeholder=\"PIB (ФИО)\"/>\r\n" +
                "                        </div>\r\n" +
                "                        \r\n" +
                "\r\n" +
                "                        <div class=\"form-group\">\r\n" +
                "                            <input type=\"password\" name=\"password\" id=\"password\" class=\"form-control input-lg\"\r\n" +
                "                                   placeholder=\"Password\"/>\r\n" +
                "                        </div>\r\n" +
                "                        \r\n" +
                "\r\n" +
                "                        <div class=\"form-group\">\r\n" +
                "                            <input type=\"password\" name=\"confirmPassword\" id=\"confirmPassword\" class=\"form-control input-lg\"\r\n" +
                "                                   placeholder=\"confirmPassword\"/>\r\n" +
                "                        </div>\r\n" +
                "                        \r\n" +
                "                        <!--btn-primary^ style  primary blue-->\r\n" +
                "                        <!--btn-lg: style big buttom-->\r\n" +
                "                        <!--btn-sm: style small buttom-->\r\n" +
                "                        <!--btn-block: занимают полную ширину родительского элемента-->\r\n" +
                "                        <!-- Buttons -->\r\n" +
                "                        <div class=\"row\">\r\n" +
                "                            <div class=\"col-xs-12 col-sm-12 col-md-12\">\r\n" +
                "                                <div class=\"pull-right\">\r\n" +
                "                                    <button id=\"create\" name=\"submit\" class=\"btn btn-lg btn-success\" value=\"1\">Create User\r\n" +
                "                                    </button>\r\n" +
                "                                </div>\r\n" +
                "                                <div>\r\n" +
                "                                    <a href=\"/login\" id=\"cancel\" name=\"cancel\" class=\"btn btn-lg btn-info\">Cancel</a>\r\n" +
                "                                    <input type=\"button\" class=\"btn btn-lg btn-warning\" name=\"reset\" value=\"Reset\"\r\n" +
                "                                           id=\"resetRegister\" onclick=\"customReset()\"/>\r\n" +
                "                                </div>\r\n" +
                "                            </div>\r\n" +
                "                        </div>\r\n" +
                "                    </fieldset>\r\n" +
                "                </form>\r\n" +
                "            </div>\r\n" +
                "        </div>\r\n" +
                "    </div>\r\n" +
                "    <div>\r\n" +
                "\r\n" +
                "    <div class=\"container\">\r\n" +
                "        <footer>\r\n" +
                "            <!-- this is footer -->\r\n" +
                "            &copy; 2019 AS21.com\r\n" +
                "            <span sec:authorize=\"isAuthenticated()\"> |\r\n" +
                "<!--                Logged user: <span sec:authentication=\"name\"></span> |-->\r\n" +
                "<!--                Roles: <span sec:authentication=\"principal.authorities\"></span> |-->\r\n" +
                "                <a href=\"/logout\">Sign Out</a>\r\n" +
                "            </span>\r\n" +
                "        </footer>\r\n" +
                "    </div>\r\n" +
                "</div>\r\n" +
                "    <script src=\"/js/reset.js\"></script>\r\n" +
                "</body>\r\n" +
                "</html>\r\n";
    }

}
