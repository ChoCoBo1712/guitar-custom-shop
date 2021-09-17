package com.chocobo.customshop.model.validator.impl;

import com.chocobo.customshop.model.validator.Validator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.regex.Pattern;

import static com.chocobo.customshop.controller.command.SessionAttribute.INVALID_PASSWORD_PATTERN_ERROR;

public class UserPasswordValidator implements Validator<String> {

    private static Validator<String> instance;

    private static final String VALID_PASSWORD_REGEX = "^(?=.*\\p{Alpha})(?=.*\\d)[\\p{Alnum}]{8,32}$";

    public static Validator<String> getInstance() {
        if (instance == null) {
            instance = new UserPasswordValidator();
        }
        return instance;
    }

    @Override
    public Pair<Boolean, String> validate(String password) {
        boolean valid = true;
        String error = "";
        if (!Pattern.matches(VALID_PASSWORD_REGEX, password)) {
            valid = false;
            error = INVALID_PASSWORD_PATTERN_ERROR;
        }
        return Pair.of(valid, error);
    }
}
