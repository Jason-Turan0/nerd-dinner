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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private NerdContactType nerdContactType;

    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    @Column(name = "EXPIRED_DATE")
    private Timestamp expiredDate;

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
