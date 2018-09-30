package nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "ADDRESSES")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "ADDRESS_PK")
    private Integer addressPk;

    @Column(name = "STREET_LINE1")
    private String streetLine1;

    @Column(name = "STREET_LINE2")
    private String streetLine2;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "ZIP")
    private String zip;

    @Column(name = "TIME_ZONE_OFFSET_MINUTES")
    private Integer timeZoneOffsetMinutes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return addressPk.equals(address.addressPk);
    }

    @Override
    public int hashCode() {
        return addressPk.hashCode();
    }
}
