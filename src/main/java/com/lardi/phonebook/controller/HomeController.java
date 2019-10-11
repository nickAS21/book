package com.lardi.phonebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * The request for transfer to the main page
     * @return thymeleaf homePage.html
     */
    @GetMapping("/")
    public String home1() {
        return "/homePage";
    }

    /**
     * The request for transfer to the main page
     * @return thymeleaf homePage.html
     */
    @GetMapping("/home")
    public String home() {
        return "/homePage";
    }


    /**
     * The request for transfer to the about page
     * @return thymeleaf about.html
     */
    @GetMapping("/about")
    public String about() {
        return "/aboutPage";
    }
}
