package com.chocobo.customshop.util.impl;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.model.service.impl.UserServiceImpl;
import com.chocobo.customshop.model.validator.Validator;
import com.chocobo.customshop.model.validator.impl.EmailValidator;
import com.chocobo.customshop.model.validator.impl.LoginValidator;
import com.chocobo.customshop.model.validator.impl.PasswordValidator;
import com.chocobo.customshop.util.ValidationUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import static com.chocobo.customshop.web.command.PagePath.AMPERSAND;
import static com.chocobo.customshop.web.command.PagePath.EQUALS_SIGN;
import static com.chocobo.customshop.web.command.RequestAttribute.DUPLICATE_EMAIL_ERROR;
import static com.chocobo.customshop.web.command.RequestAttribute.DUPLICATE_LOGIN_ERROR;

public class ValidationUtilImpl implements ValidationUtil {

    private static ValidationUtil instance;
    private static final UserService userService = UserServiceImpl.getInstance();

    public static ValidationUtil getInstance() {
        if (instance == null) {
            instance = new ValidationUtilImpl();
        }
        return instance;
    }

    @Override
    public Pair<Boolean, String> isUpdatedUserDuplicate(String email, String login, String redirectUrl,
                                                        boolean emailsMatch, boolean loginsMatch) throws ServiceException {
        boolean duplicate = false;
        if (!emailsMatch && !userService.isEmailUnique(email)) {
            duplicate = true;
            redirectUrl += AMPERSAND + DUPLICATE_EMAIL_ERROR + EQUALS_SIGN + true;
        }
        if (!loginsMatch && !userService.isLoginUnique(login)) {
            duplicate = true;
            redirectUrl += AMPERSAND + DUPLICATE_LOGIN_ERROR + EQUALS_SIGN + true;
        }
        return Pair.of(duplicate, redirectUrl);
    }

    @Override
    public Pair<Boolean, String> isUserDuplicate(String email, String login, String redirectUrl) throws ServiceException {
        boolean duplicate = false;
        if (!userService.isEmailUnique(email)) {
            duplicate = true;
            redirectUrl += AMPERSAND + DUPLICATE_EMAIL_ERROR + EQUALS_SIGN + true;
        }
        if (!userService.isLoginUnique(login)) {
            duplicate = true;
            redirectUrl += AMPERSAND + DUPLICATE_LOGIN_ERROR + EQUALS_SIGN + true;
        }
        return Pair.of(duplicate, redirectUrl);
    }
}
