package sn.ept.git47.mail;

import jakarta.ejb.Stateless;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

@Stateless
public class EmailSender {
    public void sendEmail(String recipient, String subject, String content) throws MessagingException {
        String smtpHost = "mail.galgit.com";
        int smtpPort = 587;
        String username = "dic2-2023@galgit.com";
        String password = "sn-ept@GIT2024";

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        //props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setContent(content, "text/html");

        Transport.send(message);
    }
}
