package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.UserDao;
import com.chocobo.customshop.model.dao.impl.UserDaoImpl;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.User.UserRole;
import com.chocobo.customshop.model.entity.User.UserStatus;
import com.chocobo.customshop.model.service.criteria.UserFilterCriteria;
import com.chocobo.customshop.util.HashingUtil;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.util.impl.HashingUtilImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static UserService instance;

    private final UserDao userDao = UserDaoImpl.getInstance();
    private final HashingUtil hashingUtil = HashingUtilImpl.getInstance();

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
    public long register(String email, String login, String password, UserRole role, UserStatus status) throws ServiceException {
        byte[] salt = hashingUtil.generateSalt();
        byte[] passwordHash = hashingUtil.generateHash(password, salt);

        User user = User.builder()
                .setEmail(email)
                .setLogin(login)
                .setPasswordHash(passwordHash)
                .setSalt(salt)
                .setRole(role)
                .setStatus(status)
                .build();
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
                byte[] passwordHash = hashingUtil.generateHash(password, user.getSalt());

                if (Arrays.equals(passwordHash, user.getPasswordHash())) {
                    return Optional.of(user);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(long id) throws ServiceException {
        try {
            return userDao.selectById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(User user) throws ServiceException {
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> filter(int start, int length, UserFilterCriteria criteria, String keyword) throws ServiceException {
        List<User> result;
        try {
            switch (criteria) {
                case NONE -> result = userDao.selectAll(start, length);
                case ID -> result = userDao.selectById(start, length, keyword);
                case EMAIL -> result = userDao.selectByEmail(start, length, keyword);
                case LOGIN -> result = userDao.selectByLogin(start, length, keyword);
                case ROLE -> result = userDao.selectByRole(start, length, keyword);
                case STATUS -> result = userDao.selectByStatus(start, length, keyword);
                default -> throw new ServiceException("Invalid criteria: " + criteria);
            }
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
