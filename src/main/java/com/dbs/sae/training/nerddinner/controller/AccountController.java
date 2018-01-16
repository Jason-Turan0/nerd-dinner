package com.dbs.sae.training.nerddinner.controller;

import com.dbs.sae.training.nerddinner.data.models.Language;
import com.dbs.sae.training.nerddinner.data.models.Nerd;
import com.dbs.sae.training.nerddinner.data.repositories.LanguageRepository;
import com.dbs.sae.training.nerddinner.data.repositories.NerdRepository;
import com.dbs.sae.training.nerddinner.domain.StreamExtensions;
import com.dbs.sae.training.nerddinner.model.GeneralNerdDescription;
import com.dbs.sae.training.nerddinner.model.GeneralNerdDescriptionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Controller
public class AccountController {
    private final NerdRepository nerdRepository;
    private final LanguageRepository languageRepository;
    private final ApplicationContext applicationContext;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(
            NerdRepository nerdRepository,
            LanguageRepository languageRepository,
            ApplicationContext applicationContext,
            PasswordEncoder passwordEncoder) {
        this.nerdRepository = nerdRepository;
        this.languageRepository = languageRepository;
        this.applicationContext = applicationContext;
        this.passwordEncoder = passwordEncoder;
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
    public String email() {
        return "account/email";
    }

    @GetMapping(Paths.Account.general)
    public String general(Model model, Principal principal) {
        Nerd n = this.nerdRepository.findOneByPropertyValue(Nerd.class, principal.getName(), (nerd, un) -> nerd.setUserName(un));
        model.addAttribute("firstName", n.getFirstName());


        model.addAttribute("lastName", n.getLastName());
        model.addAttribute("userName", n.getUserName());
        List<Language> languages = languageRepository.findAll();
        List<GeneralNerdDescription> nerdDescriptions = StreamExtensions.MapStreams(
                () -> languages.stream(),
                () -> n.getNerdDescriptions().stream(),
                (l, nd) -> l.getLanguagePk() == nd.getLanguage().getLanguagePk(),
                (l, nd) -> {
                    GeneralNerdDescription gd = new GeneralNerdDescription();
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
        model.addAttribute("avatar", n.getAvatar());
        GeneralNerdDescriptionWrapper wrapper = new GeneralNerdDescriptionWrapper();
        wrapper.setDescriptions(nerdDescriptions);
        model.addAttribute("descriptions", wrapper);
        return "account/general";
    }


    @PostMapping(Paths.Account.general)
    public String postGeneral(
            @ModelAttribute GeneralNerdDescriptionWrapper descriptions,
            @Valid Model model,
            BindingResult bindingResult,
            RedirectAttributes ra,
            Principal principal,
            Locale locale) {
        if (bindingResult.hasErrors()) {
        }
        model.addAttribute("descriptions", descriptions);
        return "account/general";

       /* Nerd n = this.nerdRepository.findOneByPropertyValue(Nerd.class, principal.getName(), (nerd, un) -> nerd.setUserName(un));
        n.setUserName(generalNerdSettings.getUsername());
        n.setFirstName(generalNerdSettings.getFirstName());
        n.setLastName(generalNerdSettings.getLastName());
        if (!StringExtensions.isNullOrWhiteSpace(generalNerdSettings.getPassword())) {
            n.setPassword(this.passwordEncoder.encode(generalNerdSettings.getPassword()));
        }

        n.setAvatar(generalNerdSettings.getAvatar());
        nerdRepository.save(n);
        boolean credentialsUpdated =
                !n.getUserName().equalsIgnoreCase(principal.getName()) ||
                        !StringExtensions.isNullOrWhiteSpace(generalNerdSettings.getPassword());

        if (credentialsUpdated) {
            ResourceBundle messageBundle = ResourceBundle.getBundle("Messages", locale);
            String pleaseLoginMessage = messageBundle.getString("account.general.pleaseLogin");
            return "redirect:" + Paths.Login.logout + "?logoutMessage=" + StringExtensions.urlEncode(pleaseLoginMessage);
        } else {
            return "redirect:" + Paths.Account.general + "?saveSuccessful";
        }*/

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
