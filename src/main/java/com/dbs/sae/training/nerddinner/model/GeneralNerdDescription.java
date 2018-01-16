package com.dbs.sae.training.nerddinner.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
public class GeneralNerdDescription {
    @NotEmpty
    private Integer languageFk;
    @NotEmpty
    private String title;
    @NotEmpty
    private String company;
    @NotEmpty
    private String biography;
    @NotEmpty
    private String languageName;
}
