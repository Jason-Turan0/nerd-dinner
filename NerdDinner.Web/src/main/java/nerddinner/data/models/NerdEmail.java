package nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "NERD_EMAILS")
public class NerdEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NERD_EMAIL_PK")
    private Integer nerdEmailPk;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NERD_FK")
    private Nerd nerd;

    @Column(name = "NERD_FK", insertable = false, updatable = false)
    private Integer nerdFk;

    @OneToOne
    @JoinColumn(name = "NERD_CONTACT_TYPE_FK")
    private NerdContactType nerdContactType;

    @Column(name="LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    @Column(name = "EMAIL")
    private String email;

    @Column(name="EXPIRED_DATE")
    private Timestamp expiredDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NerdEmail that = (NerdEmail) o;
        return nerdEmailPk == that.nerdEmailPk;
    }

    @Override
    public int hashCode() {
        return nerdEmailPk == null ? 0 : nerdEmailPk.hashCode();
    }

}
