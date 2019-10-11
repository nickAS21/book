package com.lardi.phonebook.controller;

import com.lardi.phonebook.model.User;
import com.lardi.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Request to go to the user’s contact page, after authorisation
     * @param model format User entity
     * @param // list (Contact) userContactsPage.html  -> contacts.js ->  myTable = $('#contactstbl').DataTable
     * -> jQery:  "ajax": { url: "/contacts/user", type: "GET"}
     * @return thymeleaf userContactsPage.html
     */
    @RequestMapping(value = "/user",
            method = {GET, POST})
    public String userPost(Model model) {
        User user = userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("userForm", user);
        return "/view/userContactsPage";
    }

    /**
     * For the testing
     * @return
     */
    @RequestMapping (value = "/test/users",
            method = GET,
            headers = "Accept=application/json; charset=UTF-8",
            produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * For the testing
     * @return
     */
    @RequestMapping (value = "/test/user/{login}",
            method = GET,
            headers = "Accept=application/json; charset=UTF-8",
            produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> getUserByLogin(@PathVariable(value = "login") String login) {
        return ResponseEntity.ok(userService.findUserByLogin(login));
    }

}
