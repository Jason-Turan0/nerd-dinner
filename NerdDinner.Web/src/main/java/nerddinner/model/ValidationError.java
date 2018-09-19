package nerddinner.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationError {
    public String modelPath;
    public String errorMessage;
}
