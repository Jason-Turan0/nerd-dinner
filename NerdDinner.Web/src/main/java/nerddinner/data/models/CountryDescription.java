package nerddinner.data.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "COUNTRY_DESCRIPTION")
public class CountryDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COUNTRY_DESCRIPTION_PK")
    private Integer countryDescriptionPk;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "LANGUAGE_FK")
    private Language language;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COUNTRY_FK")
    private Country country;

    @Column(name = "COUNTRY_NAME")
    private String countryName;

}
