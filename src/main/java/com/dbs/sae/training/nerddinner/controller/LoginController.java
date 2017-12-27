package com.dbs.sae.training.nerddinner.controller;

import com.dbs.sae.training.nerddinner.domain.RegisterAccountValidator;
import com.dbs.sae.training.nerddinner.model.RegisterAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final RegisterAccountValidator validator;

    @Autowired
    public LoginController(RegisterAccountValidator validator) {
        this.validator = validator;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
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
    public String registerAccount(final RegisterAccount registerAccount) {
        return "login/registerAccount";
    }

    @PostMapping(Paths.Login.registerAccountPath)
    public String postRegisterAccount(@Valid RegisterAccount registerAccount, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/registerAccount";
        }
        return "redirect:/";
    }

    @GetMapping(Paths.Login.loginPath)
    public String login() {
        return "login/login";
    }

}
