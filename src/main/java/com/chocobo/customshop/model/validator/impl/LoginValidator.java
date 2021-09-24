package com.chocobo.customshop.model.validator.impl;

import com.chocobo.customshop.model.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class LoginValidator implements Validator<String> {

    private static final Logger logger = LogManager.getLogger();
    private static Validator<String> instance;

    private static final String VALID_LOGIN_REGEX = "^\\p{Alnum}{8,20}$";

    public static Validator<String> getInstance() {
        if (instance == null) {
            instance = new LoginValidator();
        }
        return instance;
    }

    @Override
    public boolean validate(String login) {
        return !Pattern.matches(VALID_LOGIN_REGEX, login);
    }
}
