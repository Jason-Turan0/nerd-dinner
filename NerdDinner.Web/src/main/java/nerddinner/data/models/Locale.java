package nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "LOCALES")
public class Locale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCALE_PK")
    private Integer localePK;

    @Column(name = "LOCALE_ID")
    private String localeId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "LANGUAGE_FK")
    private Language language;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "COUNTRY_FK")
    private Country country;

}
