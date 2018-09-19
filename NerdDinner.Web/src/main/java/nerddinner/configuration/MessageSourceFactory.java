package nerddinner.configuration;

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


}
