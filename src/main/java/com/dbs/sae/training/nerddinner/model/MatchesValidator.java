package com.dbs.sae.training.nerddinner.model;

import lombok.NonNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MatchesValidator implements ConstraintValidator<Matches, Object> {

    private String field;
    private String verifyField;
    private Matches constraintAnnotation;


    public void initialize(@NonNull Matches constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.verifyField = constraintAnnotation.verifyField();
        this.constraintAnnotation = constraintAnnotation;
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldObj = GetFieldValue(field, value);
        Object verifyFieldObj = GetFieldValue(verifyField, value);

        boolean neitherSet = (fieldObj == null) && (verifyFieldObj == null);

        if (neitherSet) {
            return true;
        }

        boolean matches = (fieldObj != null) && fieldObj.equals(verifyFieldObj);

        if (!matches) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(constraintAnnotation.message())
                    .addPropertyNode(verifyField)
                    .addConstraintViolation();
        }

        return matches;
    }

    private Object GetFieldValue(String fieldName, Object value) {
        if (value == null) throw new RuntimeException("");
        try {
            String accessor = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method m = value.getClass().getMethod(accessor);
            return m.invoke(value);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}