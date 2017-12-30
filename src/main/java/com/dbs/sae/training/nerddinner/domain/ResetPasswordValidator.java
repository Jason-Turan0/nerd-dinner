package com.dbs.sae.training.nerddinner.domain;

import com.dbs.sae.training.nerddinner.data.models.Nerd;
import com.dbs.sae.training.nerddinner.data.repositories.NerdRepository;
import com.dbs.sae.training.nerddinner.model.ResetPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ResetPasswordValidator implements Validator {

    private final NerdRepository nerdRepository;

    @Autowired
    public ResetPasswordValidator(NerdRepository nerdRepository) {
        this.nerdRepository = nerdRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ResetPassword.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ResetPassword resetPassword = (ResetPassword) o;
        Nerd forgetfulNerdRequestingReset = nerdRepository.findOneByPropertyValue(Nerd.class, resetPassword.getResetKey(), (n, k) -> n.setPasswordReset(resetPassword.getResetKey()));
        if (forgetfulNerdRequestingReset == null) {
            errors.rejectValue("resetKey", "resetKeyInvalid", new Object[]{"'resetKey'"}, "{login.resetPassword.resetKeyInvalid}");
        }
    }
}
