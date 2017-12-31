package com.dbs.sae.training.nerddinner.configuration;

import com.dbs.sae.training.nerddinner.data.repositories.LocaleRepository;
import com.dbs.sae.training.nerddinner.data.repositories.NerdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

@Configuration
//@EnableWebMvc
//@Import(ThymeLeafConfig.class)
public class WebConfig extends WebMvcConfigurationSupport {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };

    @Autowired
    LocaleRepository repository;

    @Autowired
    NerdRepository nerdRepository;

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieThenAcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        localeResolver.setCookieName("nerd-locale");
        localeResolver.setCookieMaxAge(3600);
        return localeResolver;
    }

    @Bean
    public LocalizationInterceptor getLocalizationInterceptor() {
        LocalizationInterceptor interceptor = new LocalizationInterceptor(repository);
        return interceptor;
    }

    @Bean
    public UserProfileInterceptor getUserProfileInterceptor() {
        UserProfileInterceptor interceptor = new UserProfileInterceptor(nerdRepository);
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLocalizationInterceptor());
        registry.addInterceptor(getUserProfileInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);

        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
}
