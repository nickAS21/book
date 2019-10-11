package com.lardi.phonebook.controller;


import com.lardi.phonebook.model.User;
import com.lardi.phonebook.repository.IUserRepository;
import com.lardi.phonebook.service.UserService;
import com.lardi.phonebook.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.lardi.phonebook.controller.Util.mapFromJson;
import static com.lardi.phonebook.controller.Util.mapToJson;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private MockMvc mockMvcWithMockClass;
    private UserController userController;
    private UserService userServiceMock;
    private IUserRepository userRepositoryMock= mock(IUserRepository.class);
    private UserValidator userValidatorMock;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private SecurityContextHolder securityContextHolderMock = mock(SecurityContextHolder.class);

    @Before
    public void setup() {
        this.userServiceMock = new UserService(
                this.userRepositoryMock,
                this.bCryptPasswordEncoder);
        this.userValidatorMock = new UserValidator(this.userServiceMock);
        this.userController = new UserController(this.userServiceMock);
        this.mockMvcWithMockClass = MockMvcBuilders.standaloneSetup(this.userController).build();
    }

    @Test
    public void getUserContactsPage_NotAuthentication_ReturnForbidden_Test() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andExpect(status()
                        .isForbidden())
                .andExpect(content()
                        .string("{\"code\":\"403\",\"message\":\"Full authentication is required to access this resource\"}"));
    }

    @Test
    public void postUserContactsPage_NotAuthentication_ReturnForbidden_Test() throws Exception {
        this.mockMvc.perform(post("/user"))
                .andExpect(status()
                        .isForbidden())
                .andExpect(content()
                        .string("{\"code\":\"403\",\"message\":\"Full authentication is required to access this resource\"}"));
    }

    @Test
    public void postUserContactsPage_AuthenticationMock_ReturnOk_Test() throws Exception {
        User user = Util.getUserValidOk("00000000000000000000000000000001");
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(user.getLogin());
        when(this.userRepositoryMock.findByLogin(user.getLogin())).thenReturn(java.util.Optional.of(user));
        this.mockMvcWithMockClass.perform(post("/user")
                .flashAttr("userForm", user))
                .andExpect(status()
                        .isOk())
                .andExpect(view().name("/view/userContactsPage"))
                .andExpect(content()
                        .string(""));
    }

    @Test
    public void getTestUsers_Return_AllUsers_Test () throws Exception {
        String responseJson = mockMvc
                .perform(get("/test/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        User[] users = mapFromJson(responseJson, User[].class);
        String json = mapToJson(users);
        assertTrue(users.length > 0);
    }

    @Test
    public void getTestUserByLOgin_Return_UserWithLogin_Test () throws Exception {
        String login = "nickA";
        String responseJson = mockMvc
                .perform(get("/test/user/" + login))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        User user = mapFromJson(responseJson, User.class);
        assertNotNull(user);
        assertEquals(login, user.getLogin());

    }
}
