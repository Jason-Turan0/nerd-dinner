package nerddinner.domain;

import nerddinner.controller.Paths;
import nerddinner.data.models.Nerd;
import nerddinner.data.models.NerdEmail;
import nerddinner.data.repositories.NerdEmailRepository;
import nerddinner.data.repositories.NerdRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

@Component
public class ForgotPasswordEventHandler {
    private final NerdRepository nerdRepository;
    private final NerdEmailRepository emailRepository;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;

    public ForgotPasswordEventHandler(
            NerdRepository nerdRepository,
            NerdEmailRepository emailRepository,
            EmailService emailService,
            @Qualifier("email") TemplateEngine templateEngine) {
        this.nerdRepository = nerdRepository;
        this.emailRepository = emailRepository;
        this.emailService = emailService;
        this.templateEngine = templateEngine;
    }

    public HashMap<String, String> onForgotPasswordRequest(String baseUrl, String requestedEmail, Locale locale) {
        HashMap<String, String> values = new HashMap<>();
        ResourceBundle messageBundle = ResourceBundle.getBundle("Messages", locale);
        if (requestedEmail == null || requestedEmail.toString().trim().isEmpty()) {
            values.put("error", messageBundle.getString("login.forgotPassword.emailRequired"));
            return values;
        }
        NerdEmail matchingEmail = emailRepository.findOneByPropertyValue(NerdEmail.class, requestedEmail, (ne, e) -> ne.setEmail(e));
        if (matchingEmail != null) {
            //Send email with reset key
            String resetKey = UUID.randomUUID().toString();
            Nerd n = matchingEmail.getNerd();
            n.setPasswordReset(resetKey);
            nerdRepository.save(n);
            ResourceBundle emailMessageBundle = ResourceBundle.getBundle("mail/MailMessages", locale);
            String subjectLine = emailMessageBundle.getString("forgotPassword.resetPassword.subjectLine");
            String resetLink = String.format("%s%s?resetKey=%s", baseUrl, Paths.Login.resetPassword, resetKey);
            String recipientName = String.format("%s %s", n.getFirstName(), n.getLastName());
            final Context ctx = new Context(locale);
            ctx.setVariable("recipientName", recipientName);
            ctx.setVariable("resetLink", resetLink);
            final String htmlContent = templateEngine.process("forgotPassword/resetPassword", ctx);
            try {
                emailService.sendHtmlEmail(requestedEmail, subjectLine, htmlContent);
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        } else {
            //Send email with missing email
            throw new NotImplementedException();
        }


//
//            if(matchingEmail == null){
//                String errorMessage =
//                    String.format(
//                        messageBundle.getString("login.forgotPassword.emailNotFound"),
//                            requestedEmail);
//                values.put("error", errorMessage);
//                return values;
//            }


        String infoMessage = String.format(
                messageBundle.getString("login.forgotPassword.emailSent"),
                requestedEmail);
        values.put("info", infoMessage);
        return values;
    }


}
