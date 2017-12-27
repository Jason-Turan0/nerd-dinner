package com.dbs.sae.training.nerddinner.domain;

import com.dbs.sae.training.nerddinner.data.models.Nerd;
import com.dbs.sae.training.nerddinner.data.models.NerdEmail;
import com.dbs.sae.training.nerddinner.data.repositories.NerdEmailRepository;
import com.dbs.sae.training.nerddinner.data.repositories.NerdRepository;
import com.dbs.sae.training.nerddinner.model.RegisterAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegisterAccountValidator implements Validator {

    private final NerdRepository nerdRepository;
    private final NerdEmailRepository nerdEmailRepository;

    @Autowired
    public RegisterAccountValidator(
            NerdRepository nerdRepository,
            NerdEmailRepository nerdEmailRepository) {
        this.nerdRepository = nerdRepository;
        this.nerdEmailRepository = nerdEmailRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RegisterAccount.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegisterAccount ra = (RegisterAccount) o;
        String email = ra.getEmail();
        String userName = ra.getUserName();
        if (email != null) {
            NerdEmail matching = nerdEmailRepository.findOneByPropertyValue(NerdEmail.class, email, NerdEmail::setEmail);
            if (matching != null) {
                errors.rejectValue("email", "login.registerAccount.emailAlreadyExists");
            }
        }
        if (userName != null) {
            Nerd matching = nerdRepository.findOneByPropertyValue(Nerd.class, userName, Nerd::setUserName);
            if (matching != null) {
                errors.rejectValue("userName", "userNameExists", new Object[]{"'userName'"}, "{login.registerAccount.usernameAlreadyExists}");
            }
        }
    }

}