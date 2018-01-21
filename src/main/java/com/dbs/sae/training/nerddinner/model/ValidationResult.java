package com.dbs.sae.training.nerddinner.model;

import lombok.Data;

import java.util.List;

@Data
public class ValidationResult<T> {

    private Boolean isValid;
    private List<ValidationError> validationErrors;
    private T model;
    private String successUrl;
}
