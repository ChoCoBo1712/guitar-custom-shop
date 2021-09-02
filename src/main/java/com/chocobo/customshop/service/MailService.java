package com.chocobo.customshop.service;


import com.chocobo.customshop.exception.ServiceException;

public interface MailService {

    void sendMail(String recipient, String subject, String body) throws ServiceException;
    String getMailProperty(String propertyName);
}
