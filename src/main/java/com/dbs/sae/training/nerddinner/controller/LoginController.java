package com.dbs.sae.training.nerddinner.controller;

import com.dbs.sae.training.nerddinner.model.RegisterAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Locale;

@Controller
public class LoginController {

    private final MessageSource messageSource;

    @Autowired
    public LoginController(MessageSource ms) {
        this.messageSource = ms;
    }


    @GetMapping(Paths.Login.forgotPasswordPath)
    public String forgotPassword() {
        return "login/forgotPassword";
    }

    @PostMapping(Paths.Login.forgotPasswordPath)
    public String postForgotPassword() {
        return "redirect:/";
    }

    @GetMapping(Paths.Login.registerAccountPath)
    public String registerAccount(final RegisterAccount registerAccount, Locale locale) {

        return "login/registerAccount";
    }

    @PostMapping(Paths.Login.registerAccountPath)
    public String postRegisterAccount(RegisterAccount registerAccount) {


        return "redirect:/";
    }

    @GetMapping(Paths.Login.loginPath)
    public String login() {
        return "login/login";
    }

}
