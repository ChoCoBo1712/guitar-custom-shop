package com.chocobo.customshop.model.validator.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

import static com.chocobo.customshop.controller.command.SessionAttribute.DUPLICATE_EMAIL_ERROR;
import static com.chocobo.customshop.controller.command.SessionAttribute.INVALID_EMAIL_PATTERN_ERROR;
import static com.chocobo.customshop.util.impl.ValidationUtilImpl.SERVICE_EXCEPTION;

public class UserEmailValidator implements Validator<String> {

    private static final Logger logger = LogManager.getLogger();
    private static Validator<String> instance;

    private static final String VALID_EMAIL_REGEX = "^[\\w.]+@[\\w.]+$";

    public static Validator<String> getInstance() {
        if (instance == null) {
            instance = new UserEmailValidator();
        }
        return instance;
    }

    @Override
    public Pair<Boolean, String> validate(String email) {
        boolean valid = true;
        String error = "";
        try {
            if (!Pattern.matches(VALID_EMAIL_REGEX, email)) {
                valid = false;
                error = INVALID_EMAIL_PATTERN_ERROR;
            } else if (!UserServiceImpl.getInstance().isEmailUnique(email)) {
                valid = false;
                error = DUPLICATE_EMAIL_ERROR;
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during user email validation", e);
            error = SERVICE_EXCEPTION;
        }
        return Pair.of(valid, error);
    }
}
