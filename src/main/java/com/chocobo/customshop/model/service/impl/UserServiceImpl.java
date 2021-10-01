package com.chocobo.customshop.model.service.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.dao.UserDao;
import com.chocobo.customshop.model.dao.impl.UserDaoImpl;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.User.UserRole;
import com.chocobo.customshop.model.entity.User.UserStatus;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.criteria.UserFilterCriteria;
import com.chocobo.customshop.util.HashingUtil;
import com.chocobo.customshop.model.service.UserService;
import com.chocobo.customshop.util.impl.HashingUtilImpl;
import org.apache.commons.lang3.tuple.Pair;

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
            // TODO: 30.09.2021 check deleted user login 
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
    public Optional<User> findByEmail(String email) throws ServiceException {
        try {
            return userDao.selectByEmail(email);
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
    public void updateWithPassword(User user, String password) throws ServiceException {
        byte[] salt = hashingUtil.generateSalt();
        byte[] passwordHash = hashingUtil.generateHash(password, salt);

        User updatedUser = User.builder().of(user)
                .setSalt(salt)
                .setPasswordHash(passwordHash)
                .build();
        try {
            userDao.update(updatedUser);
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
    public Pair<Long, List<User>> filter(int start, int length, UserFilterCriteria criteria, String keyword)
            throws ServiceException {
        long count;
        List<User> resultList;
        try {
            switch (criteria) {
                case NONE -> {
                    resultList = userDao.selectAll(start, length);
                    count = userDao.selectCountAll();
                }
                case ID -> {
                    resultList = userDao.selectById(start, length, keyword);
                    count = userDao.selectCountById(keyword);
                }
                case EMAIL -> {
                    resultList = userDao.selectByEmail(start, length, keyword);
                    count = userDao.selectCountByEmail(keyword);
                }
                case LOGIN -> {
                    resultList = userDao.selectByLogin(start, length, keyword);
                    count = userDao.selectCountByLogin(keyword);
                }
                case ROLE -> {
                    resultList = userDao.selectByRole(start, length, keyword);
                    count = userDao.selectCountByRole(keyword);
                }
                case STATUS -> {
                    resultList = userDao.selectByStatus(start, length, keyword);
                    count = userDao.selectCountByStatus(keyword);
                }
                default -> throw new ServiceException("Invalid criteria: " + criteria);
            }
            return Pair.of(count, resultList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
