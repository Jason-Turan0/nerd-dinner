package nerddinner.domain;

import javax.mail.MessagingException;

public interface EmailService {

    void sendHtmlEmail(String recipientEmail, String subject, String htmlContent) throws MessagingException;

}
