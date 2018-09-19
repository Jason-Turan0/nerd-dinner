package nerddinner.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class TestObject {
    @NotEmpty
    @Size(max = 10)
    private String propertyOne;

    @Valid
    private List<InnerTestObject> innerObjects;
}
