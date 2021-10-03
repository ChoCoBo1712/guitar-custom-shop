package com.chocobo.customshop.util;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;
import org.apache.commons.lang3.tuple.Pair;

public interface ValidationUtil {

    Pair<Boolean, String> isUserDuplicate(String email, String login, String redirectUrl) throws ServiceException;

    boolean validateUserUpdate(User user, String email, String login, String password, boolean passwordEmpty);
}
