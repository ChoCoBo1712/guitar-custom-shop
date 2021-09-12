package com.chocobo.customshop.util;

import com.chocobo.customshop.exception.ServiceException;

import java.util.Map;

public interface TokenService {

    String generateToken(long userId, String email);

    Map<String, Object> parseToken(String token) throws ServiceException;
}
