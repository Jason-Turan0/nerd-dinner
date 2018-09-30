package nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "NERD_DINNER_DESCRIPTIONS")
public class NerdDinnerDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NERD_DINNER_DESCRIPTION_PK")
    private Integer nerdDinnerPk;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NERD_DINNER_FK")
    private NerdDinner nerdDinner;

    @Column(name = "LANGUAGE_CODE")
    private String languageCode;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;


}
