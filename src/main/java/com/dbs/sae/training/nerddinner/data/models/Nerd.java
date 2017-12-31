package com.dbs.sae.training.nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "NERDS")
@Getter
@Setter
public class Nerd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NERD_PK")
    private Integer nerdPk;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD_RESET")
    private String passwordReset;

    @Column(name = "AVATAR")
    private String avatar;

    @OneToMany(mappedBy = "nerd", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<NerdAddress> addresses = new HashSet<NerdAddress>();

    @OneToMany(mappedBy = "nerd", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
        Boolean keysAreEqual = Integer.compare(nerdPk, nerd.nerdPk) == 0;
        return keysAreEqual;
    }

    @Override
    public int hashCode() {
        return nerdPk.hashCode();
    }
}
