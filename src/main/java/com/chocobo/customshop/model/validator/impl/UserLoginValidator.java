package com.chocobo.customshop.model.validator.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

import static com.chocobo.customshop.controller.command.SessionAttribute.*;
import static com.chocobo.customshop.util.impl.ValidationUtilImpl.SERVICE_EXCEPTION;

public class UserLoginValidator implements Validator<String> {

    private static final Logger logger = LogManager.getLogger();
    private static Validator<String> instance;

    private static final String VALID_LOGIN_REGEX = "^\\p{Alnum}{8,20}$";

    public static Validator<String> getInstance() {
        if (instance == null) {
            instance = new UserLoginValidator();
        }
        return instance;
    }

    @Override
    public Pair<Boolean, String> validate(String login) {
        boolean valid = true;
        String error = "";
        try {
            if (!Pattern.matches(VALID_LOGIN_REGEX, login)) {
                valid = false;
                error = INVALID_LOGIN_PATTERN_ERROR;
            } else if (!UserServiceImpl.getInstance().isLoginUnique(login)) {
                valid = false;
                error = DUPLICATE_LOGIN_ERROR;
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during user login validation", e);
            error = SERVICE_EXCEPTION;
        }
        return Pair.of(valid, error);
    }
}
