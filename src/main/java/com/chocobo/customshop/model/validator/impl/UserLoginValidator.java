package com.chocobo.customshop.model.validator.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.chocobo.customshop.controller.command.SessionAttribute.INVALID_LOGIN_ERROR;
import static com.chocobo.customshop.util.impl.ValidationServiceImpl.SERVICE_EXCEPTION;

public class UserLoginValidator implements Validator {

    private static final Logger logger = LogManager.getLogger();
    private static Validator instance;

    public static Validator getInstance() {
        if (instance == null) {
            instance = new UserLoginValidator();
        }
        return instance;
    }

    @Override
    public Pair<Boolean, String> validate(Object login) {
        boolean valid = true;
        String error = "";
        try {
            if (!UserServiceImpl.getInstance().isLoginUnique((String) login)) {
                valid = false;
                error = INVALID_LOGIN_ERROR;
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during user login validation", e);
            error = SERVICE_EXCEPTION;
        }
        return Pair.of(valid, error);
    }
}
