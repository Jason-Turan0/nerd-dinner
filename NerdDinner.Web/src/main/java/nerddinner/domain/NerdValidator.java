package nerddinner.domain;

import nerddinner.model.ValidationError;
import nerddinner.model.ValidationResult;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NerdValidator<T> {
    private final Validator validator;

    public NerdValidator(Validator validator) {
        this.validator = validator;
    }

    public ValidationResult<T> performValidation(T model) {
        Set<ConstraintViolation<T>> result = validator.validate(model);
        List<ValidationError> errors = result.stream().map(r -> new ValidationError(r.getPropertyPath().toString(), r.getMessage())).collect(Collectors.toList());
        ValidationResult<T> vr = new ValidationResult<>();
        vr.setModel(model);
        vr.setIsValid(result.size() == 0);
        vr.setValidationErrors(errors);
        return vr;
    }

}
