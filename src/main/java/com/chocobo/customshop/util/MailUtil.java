package com.chocobo.customshop.util;


import com.chocobo.customshop.exception.ServiceException;

public interface MailUtil {

    void sendConfirmationMail(long userId, String email, String scheme, String serverName) throws ServiceException;

    void sendPasswordChangeMail(String email, String scheme, String serverName) throws ServiceException;
}
