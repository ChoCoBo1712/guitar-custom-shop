package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.User.UserRole;
import com.chocobo.customshop.model.entity.User.UserStatus;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.criteria.UserFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean isEmailUnique(String email) throws ServiceException;

    boolean isLoginUnique(String login) throws ServiceException;

    long register(String email, String login, String password, UserRole role, UserStatus status)
            throws ServiceException;

    Optional<User> login(String login, String password) throws ServiceException;

    Optional<User> findById(long id) throws ServiceException;

    void update(User user) throws ServiceException;

    void delete(long id) throws ServiceException;

    Pair<Long, List<User>> filter(int start, int length, UserFilterCriteria criteria, String keyword)
            throws ServiceException;
}
