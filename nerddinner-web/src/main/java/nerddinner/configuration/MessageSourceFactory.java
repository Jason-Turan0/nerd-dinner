package nerddinner.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class MessageSourceFactory {

    public ResourceBundle getEmailMessageSource(Locale locale) {
        ResourceBundle messageBundle = ResourceBundle.getBundle("mail/MailMessages", locale);
        return messageBundle;
    }

    public ResourceBundle getMessageSource(Locale locale) {
        ResourceBundle messageBundle = ResourceBundle.getBundle("Messages", locale);
        return messageBundle;
    }

    public ResourceBundle getValidationMessageSource(Locale locale) {
        ResourceBundle messageBundle = ResourceBundle.getBundle("ValidationMessages", locale);
        return messageBundle;
    }

    //Please do not remove. Thymeleaf messages are not getting resolved without this bean declaration in release packages (jar files).
    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/Messages", "classpath:/ValidationMessages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(5);
        return messageSource;
    }
}
