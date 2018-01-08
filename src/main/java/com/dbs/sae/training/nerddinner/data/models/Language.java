package com.dbs.sae.training.nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "LANGUAGES")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LANGUAGE_PK")
    private Integer languagePk;

    @Column(name = "LANGUAGE_CODE")
    private String languageCode;

    @Column(name = "LANGUAGE_NAME")
    private String languageName;

}
