package com.chocobo.customshop.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.User;

import java.util.Optional;

public interface UserService {

    boolean isEmailUnique(String email) throws ServiceException;

    boolean isLoginUnique(String login) throws ServiceException;

    long register(String email, String login, String password) throws ServiceException;

    Optional<User> login(String login, String password) throws ServiceException;
}
