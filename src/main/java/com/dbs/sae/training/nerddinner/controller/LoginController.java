package com.dbs.sae.training.nerddinner.controller;

import com.dbs.sae.training.nerddinner.data.models.NerdContactType;
import com.dbs.sae.training.nerddinner.data.models.NerdContactTypeDescription;
import com.dbs.sae.training.nerddinner.data.repositories.NerdContactTypeRepository;
import com.dbs.sae.training.nerddinner.domain.RegisterAccountEventHandler;
import com.dbs.sae.training.nerddinner.domain.RegisterAccountValidator;
import com.dbs.sae.training.nerddinner.model.RegisterAccount;
import com.dbs.sae.training.nerddinner.model.SelectOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    private final RegisterAccountValidator validator;
    private final NerdContactTypeRepository contactTypeRepository;
    private final RegisterAccountEventHandler registerAccountEventHandler;


    @Autowired
    public LoginController(
            RegisterAccountValidator validator,
            NerdContactTypeRepository contactTypeRepository,
            RegisterAccountEventHandler registerAccountEventHandler) {
        this.validator = validator;
        this.contactTypeRepository = contactTypeRepository;
        this.registerAccountEventHandler = registerAccountEventHandler;
    }

    @InitBinder("registerAccount")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping(Paths.Login.forgotPasswordPath)
    public String forgotPassword() {
        return "login/forgotPassword";
    }

    @PostMapping(Paths.Login.forgotPasswordPath)
    public String postForgotPassword() {
        return "redirect:/";
    }

    @GetMapping(Paths.Login.setLocalePath)
    public String setLocale(Model model) {
        return "login/setLocale";
    }

    @ModelAttribute("contactTypes")
    public List<SelectOption> contactTypes(Locale l) {
        List<NerdContactType> contactTypeList = contactTypeRepository.findAll();
        String requestedLanguage = l.getLanguage();
        String requestedCountry = l.getCountry();
        List<SelectOption> contactTypes = contactTypeList.stream().map(ct -> {
            Optional<NerdContactTypeDescription> description = ct
                    .getDescriptions()
                    .stream()
                    .filter(d -> d.getLocale().getLanguageCode().equalsIgnoreCase(requestedLanguage) && (
                            d.getLocale().getCountryCode().equalsIgnoreCase(requestedCountry) || requestedCountry.isEmpty()))
                    .findFirst();
            SelectOption o = new SelectOption();
            o.setValue(ct.getNerdContactTypePk());
            o.setText(description.isPresent() ? description.get().getDescription() : "");
            return o;
        }).collect(Collectors.toList());
        return contactTypes;
    }

    @GetMapping(Paths.Login.registerAccountPath)
    public String registerAccount(final RegisterAccount registerAccount, Locale l) {

        return "login/registerAccount";
    }

    @PostMapping(Paths.Login.registerAccountPath)
    public String postRegisterAccount(
            @Valid RegisterAccount registerAccount,
            BindingResult bindingResult,
            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/registerAccount";
        }
        registerAccountEventHandler.onRegisterAccount(registerAccount);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return "redirect:" + Paths.Login.loginPath;
    }

    @GetMapping(Paths.Login.loginPath)
    public String login() {
        return "login/login";
    }

}
