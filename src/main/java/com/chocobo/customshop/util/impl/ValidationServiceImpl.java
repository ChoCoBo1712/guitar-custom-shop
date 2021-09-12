package com.chocobo.customshop.util.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.model.validator.impl.UserEmailValidator;
import com.chocobo.customshop.model.validator.impl.UserLoginValidator;
import com.chocobo.customshop.util.ValidationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ValidationServiceImpl implements ValidationService {

    private static ValidationService instance;

    public static ValidationService getInstance() {
        if (instance == null) {
            instance = new ValidationServiceImpl();
        }
        return instance;
    }

    @Override
    public Pair<Boolean, List<String>> validateUserCreation(String email, String login) throws ServiceException {
        List<String> errorList = new ArrayList<>();
        boolean validEmail = validate(email, errorList, UserEmailValidator.getInstance());
        boolean validLogin = validate(login, errorList, UserLoginValidator.getInstance());
        if (errorList.contains(SERVICE_EXCEPTION)) {
            throw new ServiceException("An error occurred during user email validation");
        }
        return Pair.of(validEmail && validLogin, errorList);
    }

    @Override
    public Pair<Boolean, List<String>> validateUserUpdate(String email, String login, String previousEmail, String previousLogin)
            throws ServiceException {
        List<String> errorList = new ArrayList<>();
        boolean validEmail = validateIfNecessary(email, previousEmail, errorList, UserEmailValidator.getInstance());
        boolean validLogin = validateIfNecessary(login, previousLogin, errorList, UserLoginValidator.getInstance());
        if (errorList.contains(SERVICE_EXCEPTION)) {
            throw new ServiceException("An error occurred during user email validation");
        }
        return Pair.of(validEmail && validLogin, errorList);
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
