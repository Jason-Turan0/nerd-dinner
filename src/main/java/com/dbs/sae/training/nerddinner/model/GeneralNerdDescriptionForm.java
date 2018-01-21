package com.dbs.sae.training.nerddinner.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@Getter
@Setter
public class GeneralNerdDescriptionForm {
    @NotEmpty
    private Integer languageFk;

    @NotEmpty
    @Size(max = 255)
    private String title;

    @NotEmpty
    @Size(max = 255)
    private String company;

    @NotEmpty
    private String biography;

    @NotEmpty
    private String languageName;
}
