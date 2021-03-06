package nerddinner.controller;

import nerddinner.data.repositories.NerdContactTypeRepository;
import nerddinner.*;
import nerddinner.domain.*;
import nerddinner.model.RegisterAccount;
import nerddinner.model.ResetPassword;
import nerddinner.model.SelectOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    private final RegisterAccountValidator registerAccountValidator;
    private final ResetPasswordValidator resetPasswordValidator;
    private final NerdContactTypeRepository contactTypeRepository;
    private final RegisterAccountEventHandler registerAccountEventHandler;
    private final ForgotPasswordEventHandler forgotPasswordEventHandler;
    private final ResetPasswordService resetPasswordService;
    private final ApplicationContext applicationContext;

    @Autowired
    public LoginController(
            RegisterAccountValidator registerAccountValidator,
            NerdContactTypeRepository contactTypeRepository,
            RegisterAccountEventHandler registerAccountEventHandler,
            ForgotPasswordEventHandler forgotPasswordEventHandler,
            ResetPasswordValidator resetPasswordValidator,
            ResetPasswordService resetPasswordService,
            ApplicationContext applicationContext) {
        this.registerAccountValidator = registerAccountValidator;
        this.contactTypeRepository = contactTypeRepository;
        this.registerAccountEventHandler = registerAccountEventHandler;
        this.forgotPasswordEventHandler = forgotPasswordEventHandler;
        this.resetPasswordValidator = resetPasswordValidator;
        this.resetPasswordService = resetPasswordService;
        this.applicationContext = applicationContext;
    }

    @InitBinder("registerAccount")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(registerAccountValidator);
    }

    @InitBinder("resetPassword")
    private void initResetBinder(WebDataBinder binder) {
        binder.addValidators(resetPasswordValidator);
    }

    @GetMapping(Paths.Login.forgotPassword)
    public String forgotPassword() {
        return "login/forgotPassword";
    }

    @PostMapping(Paths.Login.forgotPassword)
    public String postForgotPassword(@RequestParam("email") String email, HttpServletRequest request, Locale locale) {
        String baseUrl = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
        HashMap<String, String> response = forgotPasswordEventHandler.onForgotPasswordRequest(
                baseUrl,
                email == null ? null : email.toString(),
                locale);
        String urlParams = response
                .keySet()
                .stream()
                .map(k -> String.format("%s=%s", k, PerformEncode(response.get(k))))
                .collect(Collectors.joining("&"));
        String url = Paths.Login.forgotPassword + "?" + urlParams;
        return "redirect:" + url;
    }

    @ModelAttribute("contactTypes")
    public List<SelectOption> contactTypes(Locale l) {
        return ControllerExtensions.getContactTypesAsSelectOptions(contactTypeRepository, l);
    }

    @ModelAttribute("avatars")
    public List<String> avatars() {
        return ControllerExtensions.getAvatarUrls(applicationContext);
    }


    @GetMapping(Paths.Login.registerAccount)
    public String registerAccount(final RegisterAccount registerAccount, Locale l) {

        return "login/registerAccount";
    }

    @PostMapping(Paths.Login.registerAccount)
    public String postRegisterAccount(
            @Valid RegisterAccount registerAccount,
            BindingResult bindingResult,
            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/registerAccount";
        }
        registerAccountEventHandler.onRegisterAccount(registerAccount);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return "redirect:" + Paths.Login.login;
    }

    @GetMapping(Paths.Login.login)
    public String login() {
        return "login/login";
    }

    @GetMapping(Paths.Login.resetPassword)
    public String resetPassword(final ResetPassword resetPassword, @RequestParam(value = "resetKey", required = false) String resetKey) {
        resetPasswordService.setupResetPassword(resetKey, resetPassword);
        return "login/resetPassword";
    }

    @PostMapping(Paths.Login.resetPassword)
    public String postResetPassword(
            @Valid ResetPassword resetPassword,
            BindingResult bindingResult,
            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/resetPassword";
        }
        resetPasswordService.onResetPassword(resetPassword);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return "redirect:" + Paths.Login.login;
    }

    private String PerformEncode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
