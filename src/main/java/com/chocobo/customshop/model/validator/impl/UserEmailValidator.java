package com.chocobo.customshop.model.validator.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.controller.command.SessionAttribute.INVALID_EMAIL_ERROR;
import static com.chocobo.customshop.util.impl.ValidationUtilImpl.SERVICE_EXCEPTION;

public class UserEmailValidator implements Validator {
    // TODO: 06.09.2021 implement client and server side validation
    private static final Logger logger = LogManager.getLogger();
    private static Validator instance;

    public static Validator getInstance() {
        if (instance == null) {
            instance = new UserEmailValidator();
        }
        return instance;
    }

    @Override
    public Pair<Boolean, String> validate(Object email) {
        boolean valid = true;
        String error = "";
        try {
            if (!UserServiceImpl.getInstance().isEmailUnique((String) email)) {
                valid = false;
                error = INVALID_EMAIL_ERROR;
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during user email validation", e);
            error = SERVICE_EXCEPTION;
        }
        return Pair.of(valid, error);
    }
}
