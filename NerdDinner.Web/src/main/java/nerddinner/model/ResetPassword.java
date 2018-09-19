package nerddinner.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Matches(field = "password", verifyField = "confirmPassword")
public class ResetPassword {
    @NotEmpty
    private String resetKey;

    private Boolean resetKeyIsValid;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty()
    private String confirmPassword;


}
