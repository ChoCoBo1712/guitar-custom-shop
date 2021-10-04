package com.chocobo.customshop.util;

import com.chocobo.customshop.exception.ServiceException;

/**
 * {@code HashingUtil} interface provides functionality for hashing user passwords.
 * @author Evgeniy Sokolchik
 */
public interface HashingUtil {

    /**
     * Method used to generate random salt.
     *
     * @return random salt
     */
    byte[] generateSalt();

    /**
     * Method generates a hash of given password using provided salt with addition of constant salt specified in code.
     *
     * @param password to hash
     * @param salt to use in hashing
     * @return byte array representing password hash
     * @throws ServiceException if application is unable to generate hash
     */
    byte[] generateHash(String password, byte[] salt) throws ServiceException;
}
