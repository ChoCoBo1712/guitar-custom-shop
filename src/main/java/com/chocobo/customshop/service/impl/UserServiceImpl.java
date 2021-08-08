package com.chocobo.customshop.service.impl;

import com.chocobo.customshop.controller.command.PagePath;
import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.UserDao;
import com.chocobo.customshop.model.dao.impl.UserDaoImpl;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.service.HashingService;
import com.chocobo.customshop.service.UserService;

import java.util.Arrays;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static UserService instance;

    private final UserDao userDao = UserDaoImpl.getInstance();
    private final HashingService hashingService = HashingServiceImpl.getInstance();

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean isEmailUnique(String email) throws ServiceException {
        Optional<User> user;
        try {
            user = userDao.selectByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user.isEmpty();
    }

    @Override
    public boolean isLoginUnique(String login) throws ServiceException {
        Optional<User> user;
        try {
            user = userDao.selectByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user.isEmpty();
    }

    @Override
    public long register(String email, String login, String password) throws ServiceException {
        byte[] salt = hashingService.generateSalt();
        byte[] passwordHash = hashingService.generateHash(password, salt);

        User user = new User(email, login, passwordHash, salt, User.UserRole.CLIENT, User.UserStatus.NOT_CONFIRMED);

        try {
            return userDao.insert(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> login(String login, String password) throws ServiceException {
        Optional<User> optional;
        try {
            optional = userDao.selectByLogin(login);
            if (optional.isPresent()) {
                User user = optional.get();
                byte[] passwordHash = hashingService.generateHash(password, user.getSalt());

                if (Arrays.equals(passwordHash, user.getPasswordHash())) {
                    return Optional.of(user);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return Optional.empty();
    }

}
