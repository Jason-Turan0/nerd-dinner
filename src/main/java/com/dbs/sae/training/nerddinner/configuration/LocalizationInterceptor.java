package com.dbs.sae.training.nerddinner.configuration;

import com.dbs.sae.training.nerddinner.data.models.Country;
import com.dbs.sae.training.nerddinner.data.models.CountryDescription;
import com.dbs.sae.training.nerddinner.data.models.Language;
import com.dbs.sae.training.nerddinner.data.models.LocaleViewModel;
import com.dbs.sae.training.nerddinner.data.repositories.LocaleRepository;
import org.javatuples.Pair;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalizationInterceptor extends HandlerInterceptorAdapter {

    private final LocaleRepository localeRepository;

    public LocalizationInterceptor(LocaleRepository localeRepository) {
        this.localeRepository = localeRepository;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {
        if (request == null) return;

        Optional<Cookie> localeCookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equalsIgnoreCase("nerd-locale")).findFirst();
        String localCookieValue = localeCookie.isPresent() ? localeCookie.get().getValue() : "";
        String language = request.getHeader("Accept-Language");
        List<Pair<String, Float>> languages = language == null || language.trim().isEmpty() ?
                new ArrayList<>() :
                Arrays.stream(language.split(","))
                        .map(acceptParam -> {
                            String[] accept = acceptParam.split(";");
                            if (accept.length == 1) {
                                return Pair.with(accept[0], 1.0f);
                            } else {
                                Float quality = Float.parseFloat(accept[1].replace("q=", ""));
                                return Pair.with(accept[0], quality);
                            }
                        })
                        .sorted((p0, p1) -> Float.compare(p1.getValue1(), p0.getValue1()))
                        .collect(Collectors.toList());

        List<LocaleViewModel> supportedLocales = localeRepository.findAll().stream().map(l -> {
            LocaleViewModel lvm = new LocaleViewModel();
            Country c = l.getCountry();
            Language la = l.getLanguage();
            Optional<CountryDescription> countryDescription = c.getDescriptions().stream().filter(cd -> cd.getLanguage().getLanguageCode() == la.getLanguageCode()).findFirst();
            lvm.setCountryCode(c.getCountryCode());
            lvm.setCountryName(countryDescription.isPresent() ? countryDescription.get().getCountryName() : "");
            lvm.setLanguageCode(la.getLanguageCode());
            lvm.setLanguageName(la.getLanguageName());
            lvm.setLocaleId(l.getLocaleId());
            return lvm;
        }).collect(Collectors.toList());

        Stream<Optional<LocaleViewModel>> cookieMatches = Stream.of(
                supportedLocales.stream().filter(l -> l.getLocaleId().equals(localCookieValue)).findFirst(),
                supportedLocales.stream().filter(l -> l.getLanguageCode().equals(localCookieValue)).findFirst(),
                supportedLocales.stream().filter(l -> l.getCountryCode().equals(localCookieValue)).findFirst()
        );
        Stream<Optional<LocaleViewModel>> languageMatches =
                languages.stream()
                        .flatMap(p ->
                                Stream.of(
                                        supportedLocales.stream().filter(l -> l.getLocaleId().equals(p.getValue0())).findFirst(),
                                        supportedLocales.stream().filter(l -> l.getLanguageCode().equals(p.getValue0())).findFirst(),
                                        supportedLocales.stream().filter(l -> l.getCountryCode().equals(p.getValue0())).findFirst()));

        Stream<Optional<LocaleViewModel>> defaultLocale = Stream.of(
                supportedLocales.stream().filter(l -> l.getLocaleId().equals("en_US")).findFirst());
        Stream<Optional<LocaleViewModel>> matches = Stream.concat(
                Stream.concat(cookieMatches, languageMatches),
                defaultLocale);
        LocaleViewModel matched = matches.filter(o -> o.isPresent()).findFirst().get().get();
        modelAndView.addObject("locales", supportedLocales);
        modelAndView.addObject("selectedLocale", matched);

    }
}
