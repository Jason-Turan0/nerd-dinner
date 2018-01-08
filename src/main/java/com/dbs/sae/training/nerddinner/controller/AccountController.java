package com.dbs.sae.training.nerddinner.controller;

import com.dbs.sae.training.nerddinner.data.models.Nerd;
import com.dbs.sae.training.nerddinner.data.repositories.NerdRepository;
import com.dbs.sae.training.nerddinner.domain.StringExtensions;
import com.dbs.sae.training.nerddinner.model.GeneralNerdSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Controller
public class AccountController {
    private final NerdRepository nerdRepository;
    private final ApplicationContext applicationContext;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(
            NerdRepository nerdRepository,
            ApplicationContext applicationContext,
            PasswordEncoder passwordEncoder) {
        this.nerdRepository = nerdRepository;
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
    public String general(GeneralNerdSettings generalNerdSettings, Principal principal) {
        Nerd n = this.nerdRepository.findOneByPropertyValue(Nerd.class, principal.getName(), (nerd, un) -> nerd.setUserName(un));

        generalNerdSettings.setFirstName(n.getFirstName());
        generalNerdSettings.setLastName(n.getLastName());
        generalNerdSettings.setUsername(n.getUserName());
        generalNerdSettings.setTitle(n.getTitle());
        generalNerdSettings.setCompany(n.getCompany());
        generalNerdSettings.setBiography(n.getBiography());
        generalNerdSettings.setAvatar(n.getAvatar());
        return "account/general";
    }


    @PostMapping(Paths.Account.general)
    public String postGeneral(
            @Valid GeneralNerdSettings generalNerdSettings,
            BindingResult bindingResult,
            RedirectAttributes ra,
            Principal principal,
            Locale locale) {
        if (bindingResult.hasErrors()) {
            return "account/general";
        }
        Nerd n = this.nerdRepository.findOneByPropertyValue(Nerd.class, principal.getName(), (nerd, un) -> nerd.setUserName(un));
        n.setUserName(generalNerdSettings.getUsername());
        n.setFirstName(generalNerdSettings.getFirstName());
        n.setLastName(generalNerdSettings.getLastName());
        if (!StringExtensions.isNullOrWhiteSpace(generalNerdSettings.getPassword())) {
            n.setPassword(this.passwordEncoder.encode(generalNerdSettings.getPassword()));
        }
        n.setTitle(generalNerdSettings.getTitle());
        n.setCompany(generalNerdSettings.getCompany());
        n.setBiography(generalNerdSettings.getBiography());
        n.setAvatar(generalNerdSettings.getAvatar());
        nerdRepository.save(n);
        boolean credentialsUpdated =
                !n.getUserName().equalsIgnoreCase(principal.getName()) ||
                        !StringExtensions.isNullOrWhiteSpace(generalNerdSettings.getPassword());

        if (credentialsUpdated) {
            ResourceBundle messageBundle = ResourceBundle.getBundle("Messages", locale);
            String pleaseLoginMessage = messageBundle.getString("account.general.pleaseLogin");
            return "redirect:" + Paths.Login.logout + "?logoutMessage=" + StringExtensions.UrlEncode(pleaseLoginMessage);
        } else {
            return "redirect:" + Paths.Account.general + "?saveSuccessful";
        }


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
