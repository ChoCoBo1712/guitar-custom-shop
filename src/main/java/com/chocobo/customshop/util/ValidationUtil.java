package com.chocobo.customshop.util;

import com.chocobo.customshop.exception.ServiceException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface ValidationUtil {

    String SERVICE_EXCEPTION = "serviceException";

    Pair<Boolean, List<String>> validateUserCreation(String email, String login, String password) throws ServiceException;

    Pair<Boolean, List<String>> validateUserUpdate(String email, String login, String previousEmail, String previousLogin)
            throws ServiceException;

    Pair<Boolean, List<String>> validateName(String name) throws ServiceException;

    Pair<Boolean, List<String>> validateNameUpdate(String email, String previousname) throws ServiceException;
}
