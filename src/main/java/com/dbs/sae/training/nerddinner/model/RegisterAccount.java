package com.dbs.sae.training.nerddinner.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Matches(field = "password", verifyField = "confirmPassword")
public class RegisterAccount {

    @NotEmpty()
    private String firstName;

    @NotEmpty()
    private String lastName;

    @NotEmpty()
    private String email;

    @NotEmpty()
    private String userName;

    @NotEmpty()
    private String password;

    @NotEmpty()
    private String confirmPassword;
}
