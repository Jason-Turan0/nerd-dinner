package com.dbs.sae.training.nerddinner.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class NerdEmailsForm {
    private Map<String, NerdEmailForm> nerdEmails;
    private List<SelectOption> nerdContactTypes;
    private NerdEmailForm emailEntryTemplate;
    private String emailEntryTemplateValidationRules;

    private String validationRules;
}
