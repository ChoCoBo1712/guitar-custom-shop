package com.chocobo.customshop.model.validator.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.service.WoodService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.service.impl.WoodServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

import static com.chocobo.customshop.controller.command.SessionAttribute.*;
import static com.chocobo.customshop.util.ValidationUtil.SERVICE_EXCEPTION;

public class WoodNameValidator implements Validator<String> {

    private static final Logger logger = LogManager.getLogger();
    private static Validator<String> instance;

    private static final String VALID_NAME_REGEX = "^\\p{Alpha}{8,20}$";

    public static Validator<String> getInstance() {
        if (instance == null) {
            instance = new WoodNameValidator();
        }
        return instance;
    }

    @Override
    public Pair<Boolean, String> validate(String name) {
        boolean valid = true;
        String error = "";
        try {
            if (!Pattern.matches(VALID_NAME_REGEX, name)) {
                valid = false;
                error = INVALID_NAME_PATTERN_ERROR;
            } else if (!WoodServiceImpl.getInstance().isNameUnique(name)) {
                valid = false;
                error = DUPLICATE_NAME_ERROR;
            }
        } catch (ServiceException e) {
            logger.error("An error occurred during wood name validation", e);
            error = SERVICE_EXCEPTION;
        }
        return Pair.of(valid, error);
    }
}
