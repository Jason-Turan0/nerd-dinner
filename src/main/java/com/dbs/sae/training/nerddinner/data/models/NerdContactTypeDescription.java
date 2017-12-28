package com.dbs.sae.training.nerddinner.data.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity(name = "NERD_CONTACT_TYPE_DESCRIPTIONS")
public class NerdContactTypeDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NERD_CONTACT_TYPE_DESCRIPTION_PK")
    private Integer nerdContactTypeDescriptionPk;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOCALE_FK")
    private Locale locale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NERD_CONTACT_TYPE_FK")
    private NerdContactType nerdContactType;

    private String description;

}
