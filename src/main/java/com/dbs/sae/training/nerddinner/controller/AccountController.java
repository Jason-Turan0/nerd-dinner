package com.dbs.sae.training.nerddinner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping(Paths.Account.index)
    public String index() {
        return "account/index";
    }

    @GetMapping(Paths.Account.address)
    public String address() {
        return "account/address";
    }

    @GetMapping(Paths.Account.email)
    public String email() {
        return "account/email";
    }

    @GetMapping(Paths.Account.general)
    public String general() {
        return "account/general";
    }

    @GetMapping(Paths.Account.phone)
    public String phone() {
        return "account/phone";
    }

}
