package com.lardi.phonebook.service;

import com.lardi.phonebook.repository.IUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserDetailsServiceImplTest {

    UserDetailsServiceImpl userDetailsServiceMock;

//    @Autowired
//    private MockMvc mockMvc;

    @Autowired
    private IUserRepository userRepository;

    @Before
    public void setup() {
        userDetailsServiceMock = new UserDetailsServiceImpl(this.userRepository);
    }

//    loadUserByUsername(String login)
    @Test
    public void loadUserByUsername_UserRegistration_ReturnUserDetails_Test() throws Exception {
        String name = "nickA";
        UserDetails userDetails =  userDetailsServiceMock.loadUserByUsername(name);
        assertEquals(name, userDetails.getUsername());
    }

    @Test
    public void existsUserByLogin_UserNotRegistration_ReturnExceptionWithUserName_Test() throws UsernameNotFoundException {
        String name = "nickF";
        Throwable exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsServiceMock.loadUserByUsername(name));
        assertEquals(name, exception.getMessage());
    }

}
