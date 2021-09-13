package com.chocobo.customshop.util;

import com.chocobo.customshop.exception.ServiceException;

import java.util.Map;

public interface TokenUtil {

    String generateToken(long userId, String email);

    Map<String, Object> parseToken(String token) throws ServiceException;
}
