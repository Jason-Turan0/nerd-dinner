package com.dbs.sae.training.nerddinner.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@Matches(field = "password", verifyField = "confirmPassword")
public class GeneralNerdSettingsForm {
    @NotEmpty()
    @Size(min = 1, max = 255)
    private String firstName;

    @NotEmpty()
    @Size(min = 1, max = 255)
    private String lastName;

    @NotEmpty()
    @Size(min = 1, max = 255)
    private String username;

    private String password;
    private String confirmPassword;

    @NotEmpty()
    private String avatar;

    private List<GeneralNerdDescriptionForm> nerdDescriptions;

    private String validationRules;


}
