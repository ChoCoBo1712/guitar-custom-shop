package com.chocobo.customshop.util.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.util.MailUtil;
import com.chocobo.customshop.util.TokenUtil;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.chocobo.customshop.util.impl.TokenUtilImpl.EMAIL_CLAIM;
import static com.chocobo.customshop.util.impl.TokenUtilImpl.ID_CLAIM;

public class MailUtilImpl implements MailUtil {

    private static final Logger logger = LogManager.getLogger();
    private static MailUtil instance;

    public static final String PROTOCOL_DELIMITER = "://";

    private static final String MAIL_PROPERTIES_NAME = "properties/mail.properties";
    private static final String USERNAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String THREAD_POOL_SIZE_PROPERTY = "thread_pool_size";
    private static final String HTML_BODY_TYPE = "text/html; charset=UTF-8";

    private static final String CONFIRMATION_MAIL_SUBJECT_PROPERTY = "confirmationMail.subject";
    private static final String CONFIRMATION_MAIL_BODY_PROPERTY = "confirmationMail.body";
    private static final String CONFIRMATION_MAIL_URL_BLANK = "/controller?command=confirm_email&token=";

    private static final String PASSWORD_CHANGE_MAIL_SUBJECT_PROPERTY = "passwordChangeMail.subject";
    private static final String PASSWORD_CHANGE_MAIL_BODY_PROPERTY = "passwordChangeMail.body";
    private static final String PASSWORD_CHANGE_MAIL_URL_BLANK = "/controller?command=go_to_password_change_page&token=";

    private static final Properties mailProperties;
    private static final Session mailSession;
    private static final String sender;

    private static final ExecutorService emailExecutor;
    private static final TokenUtil tokenUtil = TokenUtilImpl.getInstance();

    static {
        ClassLoader classLoader = MailUtilImpl.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(MAIL_PROPERTIES_NAME)) {
            mailProperties = new Properties();
            mailProperties.load(inputStream);
            sender = mailProperties.getProperty(USERNAME_PROPERTY);
            String password = mailProperties.getProperty(PASSWORD_PROPERTY);
            int threadPoolSize = Integer.parseInt(mailProperties.getProperty(THREAD_POOL_SIZE_PROPERTY));

            mailSession = Session.getInstance(mailProperties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, password);
                }
            });
            emailExecutor = Executors.newFixedThreadPool(threadPoolSize);
        } catch (IOException e) {
            logger.fatal("Couldn't read mail properties file", e);
            throw new RuntimeException(e);
        }
    }

    public static MailUtil getInstance() {
        if (instance == null) {
            instance = new MailUtilImpl();
        }
        return instance;
    }

    @Override
    public void sendConfirmationMail(long userId, String email, String scheme, String serverName) throws ServiceException {
        String mailSubject = mailProperties.getProperty(CONFIRMATION_MAIL_SUBJECT_PROPERTY);
        String bodyTemplate = mailProperties.getProperty(CONFIRMATION_MAIL_BODY_PROPERTY);
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(ID_CLAIM, userId);
        claimsMap.put(EMAIL_CLAIM, email);
        String confirmationUrl = CONFIRMATION_MAIL_URL_BLANK + tokenUtil.generateToken(claimsMap);
        String confirmationLink = scheme + PROTOCOL_DELIMITER + serverName + confirmationUrl;

        String mailBody = String.format(bodyTemplate, confirmationLink);
        sendMail(email, mailSubject, mailBody);
    }

    @Override
    public void sendPasswordChangeMail(String email, String scheme, String serverName) throws ServiceException {
        String mailSubject = mailProperties.getProperty(PASSWORD_CHANGE_MAIL_SUBJECT_PROPERTY);
        String bodyTemplate = mailProperties.getProperty(PASSWORD_CHANGE_MAIL_BODY_PROPERTY);
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(EMAIL_CLAIM, email);
        String confirmationUrl = PASSWORD_CHANGE_MAIL_URL_BLANK + tokenUtil.generateToken(claimsMap);
        String confirmationLink = scheme + PROTOCOL_DELIMITER + serverName + confirmationUrl;

        String mailBody = String.format(bodyTemplate, confirmationLink);
        sendMail(email, mailSubject, mailBody);
    }

    private void sendMail(String recipient, String subject, String body) throws ServiceException {
        Message message = new MimeMessage(mailSession);

        try {
            message.setFrom(new InternetAddress(sender));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(body, HTML_BODY_TYPE);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);
            emailExecutor.execute(() -> {
                try {
                    Transport.send(message);
                } catch (MessagingException e) {
                    logger.error("Error sending an email", e);
                }
            });
        } catch (MessagingException e) {
            throw new ServiceException("Error sending an email", e);
        }
    }
}
