package com.chocobo.customshop.model.validator.impl;

import com.chocobo.customshop.model.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class EmailValidator implements Validator<String> {

    private static final Logger logger = LogManager.getLogger();
    private static Validator<String> instance;

    private static final String VALID_EMAIL_REGEX = "^[\\w.]+@[\\w.]+${5,50}";

    public static Validator<String> getInstance() {
        if (instance == null) {
            instance = new EmailValidator();
        }
        return instance;
    }

    @Override
    public boolean validate(String email) {
        return Pattern.matches(VALID_EMAIL_REGEX, email);
    }
}
