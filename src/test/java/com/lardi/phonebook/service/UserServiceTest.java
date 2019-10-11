package com.lardi.phonebook.service;

import com.lardi.phonebook.model.User;
import com.lardi.phonebook.repository.IUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static com.lardi.phonebook.controller.Util.getUserValidOk;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 //    saveUser -> AuthenticationControllerTest

 //    findUserByLogin -> UserControllerTest

//      getAllUsers -> UserControllerTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    UserService userServiceMock;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setup() {
        userServiceMock = new UserService(this.userRepository, this.bCryptPasswordEncoder);
    }


//    existsUserByLogin
    @Test
    public void existsUserByLogin_UserRegistration_ReturnTrue_Test() throws Exception {
        User user = getUserValidOk(null);
        user.setLogin("nickA");
        boolean result =  userServiceMock.existsUserByLogin(user.getLogin());
        assertTrue(result);
    }

    @Test
    public void existsUserByLogin_UserNoRegistration_ReturnFalse_Test() throws Exception {
        User user = getUserValidOk(null);
        assertFalse(userServiceMock.existsUserByLogin(user.getLogin()));
    }
}
