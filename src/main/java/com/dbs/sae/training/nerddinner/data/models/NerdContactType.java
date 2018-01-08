package com.dbs.sae.training.nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "NERD_CONTACT_TYPE")
public class NerdContactType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="NERD_CONTACT_TYPE_PK")
    private Integer nerdContactTypePk;

    @Column(name="CONTACT_TYPE_CODE")
    private String contactTypeCode;

    @OneToMany(mappedBy = "nerdContactType", fetch = FetchType.EAGER)
    private Set<NerdContactTypeDescription> descriptions = new HashSet<NerdContactTypeDescription>();

}
