package nerddinner.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@Matches(field = "password", verifyField = "confirmPassword")
public class RegisterAccount {

    @NotEmpty()
    private String firstName;

    @NotEmpty()
    private String lastName;

    @NotEmpty()
    private String email;

    @NotEmpty()
    private String username;

    @NotEmpty()
    private String password;

    @NotEmpty()
    private String confirmPassword;

    @NotEmpty()
    private String avatar;

    @NotNull
    private Integer selectedContactType;

}
