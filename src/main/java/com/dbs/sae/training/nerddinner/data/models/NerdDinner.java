package com.dbs.sae.training.nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "NERD_DINNERS")
public class NerdDinner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NERD_DINNER_PK")
    private Integer nerdDinnerPk;

    @Column(name = "DINNER_DATE")
    private Timestamp dinnerDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESS_FK")
    private Address address;

    @Column(name = "MAXIMUM_ATTENDANCE")
    private Integer maximumAttendance;

    @Column(name = "TYPE")
    private String type;

    @ManyToOne()
    @JoinColumn(name = "CREATOR_NERD_FK")
    private Nerd creatorNerd;

    @OneToMany(mappedBy = "nerdDinner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<NerdDinnerReservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "nerdDinner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<NerdDinnerDescription> descriptions = new HashSet<>();



}
