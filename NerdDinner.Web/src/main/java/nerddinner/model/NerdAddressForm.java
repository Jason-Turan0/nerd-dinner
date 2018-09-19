package nerddinner.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class NerdAddressForm {

    @NotNull
    private Integer nerdFk;

    @NotNull
    private Integer nerdAddressPk;

    @NotNull
    private Integer nerdContactTypeFk;

    @NotNull
    private Integer addressFk;

    @NotEmpty
    private String streetLine1;

    private String streetLine2;

    @NotEmpty
    private String city;

    @NotEmpty
    private String state;

    @NotEmpty
    private String zip;

    @NotNull
    private Integer timeZoneOffsetMinutes;
}
