package com.chocobo.customshop.util.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.model.validator.impl.UserEmailValidator;
import com.chocobo.customshop.model.validator.impl.UserLoginValidator;
import com.chocobo.customshop.model.validator.impl.UserPasswordValidator;
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
    public Pair<Boolean, List<String>> validateUserCreation(String email, String login, String password)
            throws ServiceException {
        List<String> errorList = new ArrayList<>();
        boolean valid = validateInsert(email, errorList, UserEmailValidator.getInstance());
        valid &= validateInsert(login, errorList, UserLoginValidator.getInstance());
        valid &= validateInsert(password, errorList, UserPasswordValidator.getInstance());
        if (errorList.contains(SERVICE_EXCEPTION)) {
            throw new ServiceException("An error occurred during user email validation");
        }
        return Pair.of(valid, errorList);
    }

    @Override
    public Pair<Boolean, List<String>> validateUserUpdate(String email, String login, String previousEmail,
                                                          String previousLogin) throws ServiceException {
        List<String> errorList = new ArrayList<>();
        boolean valid = validateUpdate(email, previousEmail, errorList, UserEmailValidator.getInstance());
        valid &= validateUpdate(login, previousLogin, errorList, UserLoginValidator.getInstance());
        if (errorList.contains(SERVICE_EXCEPTION)) {
            throw new ServiceException("An error occurred during user email validation");
        }
        return Pair.of(valid, errorList);
    }

    @Override
    public Pair<Boolean, List<String>> validateWoodCreation(String name) throws ServiceException {
        List<String> errorList = new ArrayList<>();
        boolean valid = validateInsert(name, errorList, UserEmailValidator.getInstance());
        if (errorList.contains(SERVICE_EXCEPTION)) {
            throw new ServiceException("An error occurred during wood name validation");
        }
        return Pair.of(valid, errorList);
    }

    @Override
    public Pair<Boolean, List<String>> validateWoodUpdate(String name, String previousName) throws ServiceException {
        List<String> errorList = new ArrayList<>();
        boolean valid = validateUpdate(name, previousName, errorList, UserEmailValidator.getInstance());
        if (errorList.contains(SERVICE_EXCEPTION)) {
            throw new ServiceException("An error occurred during wood name validation");
        }
        return Pair.of(valid, errorList);
    }

    private boolean validateInsert(String attribute, List<String> errorList, Validator<String> validator) {
        Pair<Boolean, String> validationResult = validator.validate(attribute);
        String error = validationResult.getRight();
        if (!error.isEmpty()) {
            errorList.add(error);
        }
        return validationResult.getLeft();
    }

    private boolean validateUpdate(String attribute, String previousAttribute, List<String> errorList,
                                   Validator<String> validator) {
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
