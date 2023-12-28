package cloud.jillion.component.common.utils.mail;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

/**
 * Send Mail by Java
 *
 * @author leanderlee
 * @since 1.0.0
 */
public class MailSender {
    private volatile static MailSender mailSender;

    private final MailServerConfig serverConfig;

    public static MailSender newSender(MailServerConfig config) {
        if (null == mailSender) {
            synchronized (MailSender.class) {
                if (null == mailSender) {
                    mailSender = new MailSender(config);
                }
            }
        }
        return mailSender;
    }

    private MailSender(MailServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public void sendMessage(String sender,
                            String receiver,
                            String subject,
                            String content,
                            boolean html,
                            Date sentDate) {
        try {
            Session session = createSession();

            //create a MimeMessage object
            MimeMessage message = new MimeMessage(session);
            //set From email field
            message.setFrom(new InternetAddress(sender));
            //set To email field
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            //set email subject field
            message.setSubject(subject);
            //set the content of the email message
            if (html) {
                message.setContent(content, "text/html;charset=utf-8");
            } else {
                message.setText(content, StandardCharsets.UTF_8.name());
            }
            message.setSentDate(sentDate);
            //send the email message
            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.serverConfig.getServerHost());
        props.put("mail.smtp.socketFactory.port", this.serverConfig.getServerPort());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        //create the Session object
        String username = this.serverConfig.getServerUsername();
        String password = this.serverConfig.getServerPassword();
        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }
}
