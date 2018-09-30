package nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "NERD_DESCRIPTIONS")
public class NerdDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NERD_DESCRIPTION_PK")
    private Integer nerdDescriptionPk;

    @ManyToOne()
    @JoinColumn(name = "LANGUAGE_FK")
    private Language language;

    @ManyToOne()
    @JoinColumn(name = "NERD_FK")
    private Nerd nerd;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "BIOGRAPHY")
    private String biography;

}
