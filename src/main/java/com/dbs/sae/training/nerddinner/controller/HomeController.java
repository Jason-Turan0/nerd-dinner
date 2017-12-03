package com.dbs.sae.training.nerddinner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class HomeController {
    @GetMapping("/")
    String index() { return "homeIndex";    }

}
