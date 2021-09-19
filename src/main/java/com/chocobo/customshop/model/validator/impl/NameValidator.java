package com.chocobo.customshop.model.validator.impl;

import com.chocobo.customshop.model.validator.Validator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.regex.Pattern;

import static com.chocobo.customshop.controller.command.SessionAttribute.INVALID_NAME_PATTERN_ERROR;

public class NameValidator implements Validator<String> {

    private static Validator<String> instance;

    private static final String VALID_NAME_REGEX = "[a-zA-Z0-9\s\\-]{1,30}";

    public static Validator<String> getInstance() {
        if (instance == null) {
            instance = new NameValidator();
        }
        return instance;
    }

    @Override
    public Pair<Boolean, String> validate(String name) {
        boolean valid = true;
        String error = "";
        if (!Pattern.matches(VALID_NAME_REGEX, name)) {
            valid = false;
            error = INVALID_NAME_PATTERN_ERROR;
        }
        return Pair.of(valid, error);
    }
}
