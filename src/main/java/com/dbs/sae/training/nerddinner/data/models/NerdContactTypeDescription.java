package com.dbs.sae.training.nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "NERD_CONTACT_TYPE_DESCRIPTIONS")
public class NerdContactTypeDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NERD_CONTACT_TYPE_DESCRIPTION_PK")
    private Integer nerdContactTypeDescriptionPk;

    @ManyToOne()
    @JoinColumn(name = "LANGUAGE_FK")
    private Language language;

    @ManyToOne()
    @JoinColumn(name = "NERD_CONTACT_TYPE_FK")
    private NerdContactType nerdContactType;

    @Column(name = "DESCRIPTION")
    private String description;

}
