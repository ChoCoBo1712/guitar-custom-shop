package com.chocobo.customshop.service;

import com.chocobo.customshop.exception.ServiceException;

public interface HashingService {

    byte[] generateSalt();

    byte[] generateHash(String password, byte[] salt) throws ServiceException;
}
