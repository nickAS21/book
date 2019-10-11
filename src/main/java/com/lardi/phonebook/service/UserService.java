package com.lardi.phonebook.service;

import com.lardi.phonebook.model.User;
import com.lardi.phonebook.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Slf4j
@Service
@Scope(value="prototype", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class UserService {

    private IUserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(final IUserRepository userRepository,
                       final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Insert new user    @Autowired
     * @param user
     * @param result
     * @return String for the registerPage/html (If the error.size > 0)
     * @return String redirect:/login (If the error.size == 0)
     */
    public String saveUser(User user, BindingResult result) {
        if (result.hasErrors()) {
            return "/registerPage";
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return  "redirect:/login";
    }

    /**
     * Find user by login
     * @param login
     * @return User find by login
     */
    public User findUserByLogin(String login) {
        log.info("User findByLogin: " + login);
        return userRepository.findByLogin(login).get();
    }

    /**
     /**
     * Find of user with value==login
     * @param login
     * @return false (not Present)
     * @return true (isPresent)
     */
    public Boolean existsUserByLogin(final String login) {
        return userRepository.existsUserByLogin(login);
    }

    /**
     * All users
     * For the testing
     * @return List<User> all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
