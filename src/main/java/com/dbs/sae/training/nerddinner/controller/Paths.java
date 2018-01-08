package com.dbs.sae.training.nerddinner.controller;

public final class Paths {


    private Paths() {
    }


    public static class Login {
        public static final String forgotPassword = "/forgotPassword";
        public static final String registerAccount = "/registerAccount";
        public static final String login = "/login";
        public static final String resetPassword = "/resetPassword";
        public static final String logout = "/logout";
    }

    public class Home {
        public static final String defaultPage = "/";
        public static final String index = "/home";
    }

    public class Account {
        public static final String index = "/account";
        public static final String general = index + "/general";
        public static final String email = index + "/email";
        public static final String phone = index + "/phone";
        public static final String address = index + "/address";
    }

    public class Dinners {
        public static final String index = "/dinners";
        public static final String create = index + "/create";
        public static final String find = index + "/find";
        public static final String invite = index + "/invite";
    }


}
