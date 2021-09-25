package com.chocobo.customshop.util;

import com.chocobo.customshop.exception.ServiceException;

import java.util.Map;

public interface TokenUtil {

    String generateToken(Map<String, Object> claimsMap);

    Map<String, Object> parseToken(String token) throws ServiceException;
}
