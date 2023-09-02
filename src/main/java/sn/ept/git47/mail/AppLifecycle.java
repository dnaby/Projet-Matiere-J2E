package sn.ept.git47.mail;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;

@Singleton
/*@Startup*/
public class AppLifecycle {
    @Inject
    private EmailSender emailSender;

    //@PostConstruct
    public void appStartup() {
        try {
            emailSender.sendEmail("nabylmoustaphadia@gmail.com", "Application Started", "Application has been started !");
        } catch (MessagingException e) {
            System.out.println("Error on sending application start mail: " + e.getMessage());
        }
    }

    //@PreDestroy
    public void appShutdown() {
        try {
            emailSender.sendEmail("nabylmoustaphadia@gmail.com", "Application Stopped", "Application has been stopped !");
        } catch (MessagingException e) {
            System.out.println("Error on sending application stop mail: " + e.getMessage());
        }
    }
}
