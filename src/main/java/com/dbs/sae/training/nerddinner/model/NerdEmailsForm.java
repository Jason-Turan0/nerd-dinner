package com.dbs.sae.training.nerddinner.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class NerdEmailsForm {
    private Map<String, NerdEmailForm> nerdEmails;
    private Map<String, NerdPhoneForm> nerdPhones;
    private Map<String, NerdAddressForm> nerdAddresses;

    private List<SelectOption> nerdContactTypes;
    private List<SelectOption> states;
    private List<SelectOption> timezones;

    private NerdEmailForm emailEntryTemplate;
    private String emailEntryTemplateValidationRules;

    private NerdPhoneForm nerdPhoneTemplate;
    private String phoneEntryTemplateValidationRules;

    private NerdAddressForm nerdAddressTemplate;
    private String nerdAddressTemplateValidationRules;

    private String validationRules;
}
