package nerddinner.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class InnerTestObject {
    @NotEmpty
    private String innerPropertyOne;
    private Integer innerPropertyTwo;
}
