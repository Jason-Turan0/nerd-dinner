package com.dbs.sae.training.nerddinner.controller;

import com.dbs.sae.training.nerddinner.configuration.MessageSourceFactory;
import com.dbs.sae.training.nerddinner.data.models.*;
import com.dbs.sae.training.nerddinner.data.repositories.LanguageRepository;
import com.dbs.sae.training.nerddinner.data.repositories.NerdContactTypeRepository;
import com.dbs.sae.training.nerddinner.data.repositories.NerdEmailRepository;
import com.dbs.sae.training.nerddinner.data.repositories.NerdRepository;
import com.dbs.sae.training.nerddinner.domain.NerdValidator;
import com.dbs.sae.training.nerddinner.domain.StreamExtensions;
import com.dbs.sae.training.nerddinner.domain.StringExtensions;
import com.dbs.sae.training.nerddinner.domain.ValidationGenerator;
import com.dbs.sae.training.nerddinner.model.*;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class AccountController {
    private final NerdRepository nerdRepository;
    private final LanguageRepository languageRepository;
    private final ApplicationContext applicationContext;
    private final PasswordEncoder passwordEncoder;
    private final MessageSourceFactory messageSourceFactory;
    private final NerdValidator<GeneralNerdSettingsForm> generalSettingValidator;
    private final NerdValidator<NerdEmailsForm> emailFormValidator;
    private final NerdEmailRepository emailRepository;
    private final NerdContactTypeRepository contactTypeRepository;

    @Autowired
    public AccountController(
            NerdRepository nerdRepository,
            LanguageRepository languageRepository,
            ApplicationContext applicationContext,
            PasswordEncoder passwordEncoder,
            MessageSourceFactory messageSourceFactory,
            NerdValidator<GeneralNerdSettingsForm> validator,
            NerdValidator<NerdEmailsForm> emailFormValidator,
            NerdEmailRepository emailRepository,
            NerdContactTypeRepository contactTypeRepository) {
        this.nerdRepository = nerdRepository;
        this.languageRepository = languageRepository;
        this.applicationContext = applicationContext;
        this.passwordEncoder = passwordEncoder;
        this.messageSourceFactory = messageSourceFactory;
        this.generalSettingValidator = validator;
        this.emailFormValidator = emailFormValidator;
        this.emailRepository = emailRepository;
        this.contactTypeRepository = contactTypeRepository;
    }

    @GetMapping(Paths.Account.index)
    public String index() {
        return "account/index";
    }

    @GetMapping(Paths.Account.address)
    public String address() {
        return "account/address";
    }

    @GetMapping(Paths.Account.email)
    public String email(NerdEmailsForm model, Principal principal, Locale l) {
        Nerd n = this.nerdRepository.findOneByPropertyValue(Nerd.class, principal.getName(), (nerd, un) -> nerd.setUserName(un));
        Map<String, NerdEmailForm> emailForms = n.getEmails()
                .stream()
                .filter(ne -> ne.getExpiredDate() == null)
                .map(ne -> {
                    NerdEmailForm nef = new NerdEmailForm();
                    nef.setEmail(ne.getEmail());
                    nef.setNerdFk(n.getNerdPk());
                    nef.setNerdEmailPk(ne.getNerdEmailPk());
                    nef.setNerdContactTypeFk(ne.getNerdContactType().getNerdContactTypePk());
                    return nef;
                })
                .collect(Collectors.toMap(
                        ne -> UUID.randomUUID().toString(),
                        ne -> ne));

        NerdEmailForm emailFormTemplate = new NerdEmailForm();
        emailFormTemplate.setNerdFk(n.getNerdPk());
        model.setEmailEntryTemplate(emailFormTemplate);
        model.setEmailEntryTemplateValidationRules(getValidationRules(emailFormTemplate, l));

        model.setNerdEmails(emailForms);
        model.setNerdContactTypes(ControllerExtensions.getContactTypesAsSelectOptions(contactTypeRepository, l));
        model.setValidationRules(getValidationRules(model, l));
        return "account/email";
    }

    @PostMapping(Paths.Account.email)
    public @ResponseBody
    ValidationResult<NerdEmailsForm> postEmails(@RequestBody NerdEmailsForm formModel, Principal principal, Locale locale) {
        ValidationResult<NerdEmailsForm> vr = this.emailFormValidator.performValidation(formModel);
        if (!vr.getIsValid()) {
            return vr;
        }
        Nerd n = this.nerdRepository.findOneByPropertyValue(Nerd.class, principal.getName(), (nerd, un) -> nerd.setUserName(un));
        List<NerdContactType> contactTypes = contactTypeRepository.findAll();
        List<NerdEmail> nerdEmails = n.getEmails().stream().filter(ne -> ne.getExpiredDate() == null).collect(Collectors.toList());
        List<Pair<Optional<NerdEmail>, Optional<NerdEmailForm>>> joined = StreamExtensions.OuterJoin(
                () -> nerdEmails.stream(),
                () -> formModel.getNerdEmails().values().stream(),
                (l, r) -> {
                    return l.getNerdEmailPk() == r.getNerdEmailPk();
                }
        );
        List<Pair<Optional<NerdEmail>, Optional<NerdEmailForm>>> toUpdate = joined.stream().filter(t -> t.getValue0().isPresent() && t.getValue1().isPresent()).collect(Collectors.toList());
        List<Pair<Optional<NerdEmail>, Optional<NerdEmailForm>>> toAdd = joined.stream().filter(t -> !t.getValue0().isPresent() && t.getValue1().isPresent()).collect(Collectors.toList());
        List<Pair<Optional<NerdEmail>, Optional<NerdEmailForm>>> toExpire = joined.stream().filter(t -> t.getValue0().isPresent() && !t.getValue1().isPresent()).collect(Collectors.toList());
        for (Pair<Optional<NerdEmail>, Optional<NerdEmailForm>> p : toUpdate) {
            NerdEmail ne = p.getValue0().get();
            NerdEmailForm form = p.getValue1().get();
            MapToNerdEmail(form, ne, contactTypes);
        }
        for (Pair<Optional<NerdEmail>, Optional<NerdEmailForm>> p : toAdd) {
            NerdEmail ne = new NerdEmail();
            ne.setNerd(n);
            n.getEmails().add(ne);
            NerdEmailForm form = p.getValue1().get();
            MapToNerdEmail(form, ne, contactTypes);
        }
        for (Pair<Optional<NerdEmail>, Optional<NerdEmailForm>> p : toExpire) {
            NerdEmail ne = p.getValue0().get();
            ne.setExpiredDate(Timestamp.from(Instant.now()));
            ne.setLastUpdateDate(Timestamp.from(Instant.now()));
        }
        nerdRepository.save(n);
        return vr;
    }

    public void MapToNerdEmail(NerdEmailForm form, NerdEmail ne, List<NerdContactType> contactTypes) {
        NerdContactType nct = contactTypes.stream().filter(ct -> ct.getNerdContactTypePk() == form.getNerdContactTypeFk()).findFirst().get();
        ne.setEmail(form.getEmail());
        ne.setNerdContactType(nct);
        ne.setLastUpdateDate(Timestamp.from(Instant.now()));
    }


    @GetMapping(Paths.Account.general)
    public String general(GeneralNerdSettingsForm model, Principal principal, Locale locale) {
        Nerd n = this.nerdRepository.findOneByPropertyValue(Nerd.class, principal.getName(), (nerd, un) -> nerd.setUserName(un));
        model.setFirstName(n.getFirstName());
        model.setLastName(n.getLastName());
        model.setUsername(n.getUserName());
        List<Language> languages = languageRepository.findAll();
        List<GeneralNerdDescriptionForm> nerdDescriptions = StreamExtensions.MapStreams(
                () -> languages.stream(),
                () -> n.getNerdDescriptions().stream(),
                (l, nd) -> l.getLanguagePk() == nd.getLanguage().getLanguagePk(),
                (l, nd) -> {
                    GeneralNerdDescriptionForm gd = new GeneralNerdDescriptionForm();
                    if (nd.isPresent()) {
                        gd.setCompany(nd.get().getCompany());
                        gd.setTitle(nd.get().getTitle());
                        gd.setBiography(nd.get().getBiography());
                    }
                    gd.setLanguageName(l.getLanguageName());
                    gd.setLanguageFk(l.getLanguagePk());
                    return gd;
                }
        );
        model.setNerdDescriptions(nerdDescriptions);
        model.setAvatar(n.getAvatar());
        model.setValidationRules(getValidationRules(model, locale));
        return "account/general";
    }

    public String getValidationRules(Object withValidation, Locale locale) {
        ResourceBundle vrb = this.messageSourceFactory.getValidationMessageSource(locale);
        return new ValidationGenerator(vrb).getValidationRules(withValidation).toString();
    }

    @PostMapping(Paths.Account.general)
    public @ResponseBody
    ValidationResult<GeneralNerdSettingsForm> postGeneral(@RequestBody GeneralNerdSettingsForm formModel, Principal principal, Locale locale) {
        ValidationResult<GeneralNerdSettingsForm> vr = this.generalSettingValidator.performValidation(formModel);
        if (!vr.getIsValid()) {
            return vr;
        }
        List<Language> languages = this.languageRepository.findAll();
        Nerd n = this.nerdRepository.findOneByPropertyValue(Nerd.class, principal.getName(), (nerd, un) -> nerd.setUserName(un));
        n.setUserName(formModel.getUsername());
        n.setFirstName(formModel.getFirstName());
        n.setLastName(formModel.getLastName());
        if (!StringExtensions.isNullOrWhiteSpace(formModel.getPassword())) {
            n.setPassword(this.passwordEncoder.encode(formModel.getPassword()));
        }
        Set<NerdDescription> d = n.getNerdDescriptions();
        for (GeneralNerdDescriptionForm ndf : formModel.getNerdDescriptions()) {
            Optional<NerdDescription> matching = d.stream().filter(nd -> nd.getLanguage().getLanguagePk() == ndf.getLanguageFk()).findFirst();
            NerdDescription toUpdate = matching.isPresent() ? matching.get() : new NerdDescription();
            if (!matching.isPresent()) {
                Language ld = languages.stream().filter(l -> l.getLanguagePk() == ndf.getLanguageFk()).findFirst().get();
                d.add(toUpdate);
                toUpdate.setNerd(n);
                toUpdate.setLanguage(ld);
            }
            toUpdate.setBiography(ndf.getBiography());
            toUpdate.setCompany(ndf.getCompany());
            toUpdate.setTitle(ndf.getTitle());
            toUpdate.setNerd(n);
        }

        n.setAvatar(formModel.getAvatar());
        nerdRepository.save(n);
        boolean credentialsUpdated =
                !n.getUserName().equalsIgnoreCase(principal.getName()) ||
                        !StringExtensions.isNullOrWhiteSpace(formModel.getPassword());
        if (credentialsUpdated) {
            ResourceBundle messageBundle = ResourceBundle.getBundle("Messages", locale);
            String pleaseLoginMessage = messageBundle.getString("account.general.pleaseLogin");
            vr.setSuccessUrl(Paths.Login.logout + "?logoutMessage=" + StringExtensions.urlEncode(pleaseLoginMessage));
        } else {
            vr.setSuccessUrl(Paths.Account.general + "?saveSuccessful");
        }
        return vr;

    }

    @ModelAttribute("avatars")
    public List<String> avatars() {
        return ControllerExtensions.getAvatarUrls(applicationContext);
    }


    @GetMapping(Paths.Account.phone)
    public String phone() {
        return "account/phone";
    }

}
