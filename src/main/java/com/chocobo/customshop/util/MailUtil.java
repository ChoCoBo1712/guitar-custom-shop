package com.chocobo.customshop.util;


import com.chocobo.customshop.exception.ServiceException;

public interface MailUtil {

    void sendMail(String recipient, String subject, String body) throws ServiceException;
    String getMailProperty(String propertyName);
}