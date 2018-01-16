package com.dbs.sae.training.nerddinner.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Matches(field = "password", verifyField = "confirmPassword")
public class GeneralNerdSettings {
    @NotEmpty()
    private String firstName;
    @NotEmpty()
    private String lastName;
    @NotEmpty()
    private String username;

    private String password;
    private String confirmPassword;
    private String title;
    private String company;
    private String biography;

    @NotEmpty()
    private String avatar;

}
