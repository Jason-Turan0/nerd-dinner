package com.dbs.sae.training.nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "NERD_ADDRESSES")
public class NerdAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NERD_ADDRESS_PK")
    private int nerdAddressPk;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESS_FK")
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NERD_FK")
    private Nerd nerd;

    @OneToOne
    @JoinColumn(name = "NERD_CONTACT_TYPE_FK")
    private NerdContactType nerd_Contact_Type;
    private Timestamp last_Update_Date;
    private Timestamp expired_Date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NerdAddress that = (NerdAddress) o;
        return nerdAddressPk == that.nerdAddressPk;
    }

    @Override
    public int hashCode() {
        return nerdAddressPk;
    }
}
