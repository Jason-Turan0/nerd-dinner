package com.dbs.sae.training.nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "NERDS")
public class Nerd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NERD_PK")
    @Getter
    @Setter
    private Integer nerdPk;

    @Column(name = "FIRST_NAME")
    @Getter
    @Setter
    private String firstName;

    @Column(name = "LAST_NAME")
    @Getter
    @Setter
    private String lastName;

    @Column(name = "PASSWORD")
    @Getter
    @Setter
    private String password;

    @Column(name = "USER_NAME")
    @Getter
    @Setter
    private String userName;

    @OneToMany(mappedBy = "nerd", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<NerdAddress> addresses = new HashSet<NerdAddress>();

    @OneToMany(mappedBy = "nerd", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<NerdEmail> emails = new HashSet<NerdEmail>();

    @OneToMany(mappedBy = "creatorNerd", cascade = CascadeType.ALL)
    private Set<NerdDinner> createdDinners = new HashSet<NerdDinner>();

    @OneToMany(mappedBy = "nerd", cascade = CascadeType.ALL)
    private Set<NerdDinnerReservation> dinnersReservations = new HashSet<NerdDinnerReservation>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nerd nerd = (Nerd) o;
        if (!nerdPk.equals(nerd.nerdPk)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nerdPk.hashCode();
    }
}
