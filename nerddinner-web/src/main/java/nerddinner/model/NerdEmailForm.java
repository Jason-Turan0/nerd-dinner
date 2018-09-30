package nerddinner.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class NerdEmailForm {

    private Integer nerdEmailPk;

    @NotNull
    private Integer nerdFk;

    @NotNull
    private Integer nerdContactTypeFk;

    @NotEmpty
    @Email
    private String email;
}
