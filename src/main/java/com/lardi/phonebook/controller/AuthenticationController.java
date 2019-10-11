package com.lardi.phonebook.controller;

import com.lardi.phonebook.model.User;
import com.lardi.phonebook.service.UserService;
import com.lardi.phonebook.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

//@Slf4j
@Controller
public class AuthenticationController {

    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public AuthenticationController(final UserService userService,
                                    final UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    /**
     * Start for user authorization
     * @return thymeleaf loginPage.html
     */
    @GetMapping("/login")
    public String login() {
        return "/loginPage";
    }

    /**
     * Start to enter information about the user
     * @param model (entity User)
     * @return thymeleaf registerPage.html
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userForm", new User());
        return "/registerPage";
    }

    /**
     * User registration
     * @param user (model: User)
     * @param result (Aanalysis result after validation).
     * @return thymeleaf registerPage/html (If the error.size > 0)
     * @return redirect:/login (If the error.size == 0)
     */
    @PostMapping("/register")
    public String registerSave(@ModelAttribute("userForm") User user, BindingResult result, Model modelNew) {
        userValidator.validate(user, result);
        String page = userService.saveUser(user, result);
        modelNew.addAttribute("userForm", user);
        return  page;
    }
}
