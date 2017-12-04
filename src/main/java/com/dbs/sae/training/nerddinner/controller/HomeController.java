package com.dbs.sae.training.nerddinner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "home/index";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "account/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String postForgotPassword() {
        return "redirect:/";
    }

    @GetMapping("/registerAccount")
    public String registerAccount() {
        return "account/registerAccount";
    }

    @PostMapping("/registerAccount")
    public String postRegisterAccount() {
        return "redirect:/";
    }


    @GetMapping("/login")
    public String login() {
        return "account/login";
    }




}
