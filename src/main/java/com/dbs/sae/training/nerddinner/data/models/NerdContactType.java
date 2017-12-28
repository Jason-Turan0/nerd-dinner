package com.dbs.sae.training.nerddinner.data.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
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

    @OneToMany(mappedBy = "nerdContactType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<NerdContactTypeDescription> descriptions = new HashSet<NerdContactTypeDescription>();

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
