package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User>{

    Optional<User> selectByEmail(String email) throws DaoException;

    Optional<User> selectByLogin(String login) throws DaoException;
}