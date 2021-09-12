package com.chocobo.customshop.util.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.util.MailService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MailServiceImpl implements MailService {

    private static final Logger logger = LogManager.getLogger();
    private static MailService instance;

    private static final String MAIL_PROPERTIES_NAME = "properties/mail.properties";
    private static final String USERNAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String THREAD_POOL_SIZE_PROPERTY = "thread_pool_size";
    private static final String HTML_BODY_TYPE = "text/html; charset=UTF-8";

    private static final Properties mailProperties;
    private static final Session mailSession;
    private static final String sender;

    private static final ExecutorService emailExecutor;

    static {
        ClassLoader classLoader = MailServiceImpl.class.getClassLoader();
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
            emailExecutor.execute(() -> {
                try {
                    Transport.send(message);
                } catch (MessagingException e) {
                    // TODO: 05.09.2021 ask about exception catch 
                    logger.error("Error sending an email", e);
                }
            });
        } catch (MessagingException e) {
            throw new ServiceException("Error sending an email", e);
        }
    }

    @Override
    public String getMailProperty(String propertyName) {
        return mailProperties.getProperty(propertyName);
    }
}