package com.dbs.sae.training.nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "LOCALES")
public class Locale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCALE_PK")
    private Integer localePK;

    @Column(name = "LOCALE_ID")
    private String localeId;

    @Column(name = "LANGUAGE_CODE")
    private String languageCode;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "LANGUAGE_NAME")
    private String languageName;

    @Column(name = "COUNTRY_NAME")
    private String countryName;
}
