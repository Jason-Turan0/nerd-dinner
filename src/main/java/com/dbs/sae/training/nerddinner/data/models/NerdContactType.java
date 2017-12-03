package com.dbs.sae.training.nerddinner.data.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity(name = "NERD_CONTACT_TYPE")
public class NerdContactType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="NERD_CONTACT_TYPE_PK")
    private Integer nerdContactTypePk;

    @Column(name="CONTACT_TYPE_CODE")
    private String contactTypeCode;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        NerdContactType that = (NerdContactType) o;

        return nerdContactTypePk.equals(that.nerdContactTypePk);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + nerdContactTypePk.hashCode();
        return result;
    }
}
