package com.dbs.sae.training.nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "NERD_PHONES")
public class NerdPhone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NERD_PHONE_PK")
    private Integer nerdPhonePk;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NERD_FK")
    private Nerd nerd;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NERD_CONTACT_TYPE_FK")
    private NerdContactType nerdContactType;

    @Column(name="LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    @Column(name="EXPIRED_DATE")
    private Timestamp expiredDate;

    @Column(name="AREA_CODE")
    private String areaCode;

    @Column(name="CENTRAL_OFFICE_PREFIX")
    private String centralOfficePrefix;

    @Column(name="LINE_NUMBER")
    private String LineNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NerdPhone nerdPhone = (NerdPhone) o;

        return nerdPhonePk.equals(nerdPhone.nerdPhonePk);
    }

    @Override
    public int hashCode() {
        return nerdPhonePk.hashCode();
    }

}
