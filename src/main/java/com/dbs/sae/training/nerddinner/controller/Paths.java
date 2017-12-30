package com.dbs.sae.training.nerddinner.controller;

public final class Paths {

    private Paths() {
    }


    public static class Login {
        public static final String forgotPassword = "/forgotPassword";
        public static final String registerAccount = "/registerAccount";
        public static final String login = "/login";
        public static final String resetPassword = "/resetPassword";
    }

    public class Home {
        public static final String index = "/home";
    }
}
