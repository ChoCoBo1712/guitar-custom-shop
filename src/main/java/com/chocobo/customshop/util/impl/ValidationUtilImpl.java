package com.chocobo.customshop.util.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.model.validator.impl.UserEmailValidator;
import com.chocobo.customshop.model.validator.impl.UserLoginValidator;
import com.chocobo.customshop.util.ValidationUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtilImpl implements ValidationUtil {

    private static ValidationUtil instance;

    public static ValidationUtil getInstance() {
        if (instance == null) {
            instance = new ValidationUtilImpl();
        }
        return instance;
    }

    @Override
    public Pair<Boolean, List<String>> validateUserCreation(String email, String login) throws ServiceException {
        List<String> errorList = new ArrayList<>();
        boolean valid = validate(email, errorList, UserEmailValidator.getInstance());
        valid &= validate(login, errorList, UserLoginValidator.getInstance());
        if (errorList.contains(SERVICE_EXCEPTION)) {
            throw new ServiceException("An error occurred during user email validation");
        }
        return Pair.of(valid, errorList);
    }

    @Override
    public Pair<Boolean, List<String>> validateUserUpdate(String email, String login, String previousEmail, String previousLogin)
            throws ServiceException {
        List<String> errorList = new ArrayList<>();
        boolean valid = validateIfNecessary(email, previousEmail, errorList, UserEmailValidator.getInstance());
        valid &= validateIfNecessary(login, previousLogin, errorList, UserLoginValidator.getInstance());
        if (errorList.contains(SERVICE_EXCEPTION)) {
            throw new ServiceException("An error occurred during user email validation");
        }
        return Pair.of(valid, errorList);
    }

    private boolean validate(String attribute, List<String> errorList, Validator validator) {
        Pair<Boolean, String> validationResult = validator.validate(attribute);
        String error = validationResult.getRight();
        if (!error.isEmpty()) {
            errorList.add(error);
        }
        return validationResult.getLeft();
    }

    private boolean validateIfNecessary(String attribute, String previousAttribute, List<String> errorList, Validator validator) {
        if (!StringUtils.equals(attribute, previousAttribute)) {
            Pair<Boolean, String> validationResult = validator.validate(attribute);
            String error = validationResult.getRight();
            if (!error.isEmpty()) {
                errorList.add(error);
            }
            return validationResult.getLeft();
        }
        return true;
    }
}
