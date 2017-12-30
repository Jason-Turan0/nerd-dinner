package com.dbs.sae.training.nerddinner.domain;

import com.dbs.sae.training.nerddinner.configuration.NerdDinnerConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EmailServiceImpl implements EmailService {

    private NerdDinnerConfigurationProperties configuration;
    private JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(NerdDinnerConfigurationProperties configuration, JavaMailSender mailSender) {
        this.configuration = configuration;
        this.mailSender = mailSender;
    }

    @Override
    public void sendHtmlEmail(String recipientEmail, String subject, String htmlContent) throws MessagingException {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(subject);
        message.setFrom("admin@nerddinner.com");
        message.setTo(recipientEmail);
        message.setText(htmlContent, true);

        if (configuration.getSendEmails()) {
            this.mailSender.send(mimeMessage);
        }

        String fileName = String.format("%s_%s_%s.eml",
                recipientEmail,
                subject,
                DateTimeFormatter.ofPattern("MM-dd-yyyy HH-mm-ss-SSSZ").format(ZonedDateTime.now()));

        try {
            File emailDirectory = FileSystems.getDefault().getPath(configuration.getEmailSavePath()).toFile();
            emailDirectory.mkdir();
            File emailFile = FileSystems.getDefault().getPath(emailDirectory.getAbsolutePath(), fileName).toFile();
            emailFile.createNewFile();
            mimeMessage.writeTo(new FileOutputStream(emailFile));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }
}
