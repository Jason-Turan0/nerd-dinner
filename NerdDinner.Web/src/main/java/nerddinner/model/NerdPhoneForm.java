package nerddinner.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class NerdPhoneForm {

    private Integer nerdPhonePk;

    @NotNull
    private Integer nerdFk;

    @NotNull
    private Integer nerdContactTypeFk;

    @NotEmpty
    @Pattern(message = "{common.constraints.areaCode}", regexp = "\\d{3}")
    private String areaCode;

    @NotEmpty
    @Pattern(message = "{common.constraints.lineNumber}", regexp = "\\d{7}")
    private String lineNumber;

}
