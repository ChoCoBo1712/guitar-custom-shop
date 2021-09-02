package com.chocobo.customshop.service.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.service.MailService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailServiceImpl implements MailService {

    private static MailService instance;

    private static final String MAIL_PROPERTIES_NAME = "properties/mail.properties";
    private static final String USERNAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String HTML_BODY_TYPE = "text/html; charset=UTF-8";

    private static final Properties mailProperties;
    private static final Session mailSession;
    private static final String sender;

    static {
        ClassLoader classLoader = MailServiceImpl.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(MAIL_PROPERTIES_NAME)) {
            mailProperties = new Properties();
            mailProperties.load(inputStream);
            sender = mailProperties.getProperty(USERNAME_PROPERTY);
            String password = mailProperties.getProperty(PASSWORD_PROPERTY);
            mailSession = Session.getInstance(mailProperties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, password);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read mail properties file", e);
        }
    }

    public static MailService getInstance() {
        if (instance == null) {
            instance = new MailServiceImpl();
        }
        return instance;
    }

    @Override
    public void sendMail(String recipient, String subject, String body) throws ServiceException {
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
            Transport.send(message);
        } catch (MessagingException e) {
            throw new ServiceException("Error sending an email", e);
        }
    }

    @Override
    public String getMailProperty(String propertyName) {
        return mailProperties.getProperty(propertyName);
    }
}
