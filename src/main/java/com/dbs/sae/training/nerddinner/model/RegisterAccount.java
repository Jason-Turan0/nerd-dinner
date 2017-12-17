package com.dbs.sae.training.nerddinner.model;

import lombok.Data;

@Data
public class RegisterAccount {
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private String confirmPassword;
}
