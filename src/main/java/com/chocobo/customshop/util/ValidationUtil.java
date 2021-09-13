package com.chocobo.customshop.util;

import com.chocobo.customshop.exception.ServiceException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface ValidationUtil {

    String SERVICE_EXCEPTION = "serviceException";

    Pair<Boolean, List<String>> validateUserCreation(String email, String login) throws ServiceException;

    Pair<Boolean, List<String>> validateUserUpdate(String email, String login, String previousEmail, String previousLogin)
            throws ServiceException;
}
