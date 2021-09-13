package com.chocobo.customshop.util;

import com.chocobo.customshop.exception.ServiceException;

public interface HashingUtil {

    byte[] generateSalt();

    byte[] generateHash(String password, byte[] salt) throws ServiceException;
}
