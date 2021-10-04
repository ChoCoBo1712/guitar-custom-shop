package com.chocobo.customshop.util;

import com.chocobo.customshop.exception.ServiceException;

import java.util.Map;

/**
 * {@code TokenUtil} interface provides functionality for generating and parsing JWT.
 * @author Evgeniy Sokolchik
 */
public interface TokenUtil {

    /**
     * Method for generating JWT with claims from given map.
     * Sets expiration time specified in resources.
     *
     * @param claimsMap containing strings and objects
     * @return JWT
     */
    String generateToken(Map<String, Object> claimsMap);

    /**
     * Method for parsing JWT and acquiring claims to map.
     *
     * @param token to parse
     * @return map with claims from JWT
     * @throws ServiceException if application is unable to parse token
     */
    Map<String, Object> parseToken(String token) throws ServiceException;
}
