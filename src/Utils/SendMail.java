package Utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author HP
 */
public class SendMail {

    private static final String FROM = "service.provider.time@gmail.com";
    private static final String PASSWORD = "coffee2021";


    public static void sendMail(String toMail, String subject, String message) throws AddressException, MessagingException {

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");

        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session mailSession = Session.getDefaultInstance(props, null);

        //enable the comment below to activate console debug
        mailSession.setDebug(true);

        Message mailMessage = new MimeMessage(mailSession);

        mailMessage.setFrom(new InternetAddress(FROM));
        mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
        mailMessage.setContent(message, "text/html; charset=utf-8");
        mailMessage.setSubject(subject);
        Transport transport = mailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", FROM, PASSWORD);
        transport.sendMessage(mailMessage, mailMessage.getAllRecipients());

    }

}