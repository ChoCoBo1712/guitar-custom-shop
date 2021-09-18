package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.User.UserRole;
import com.chocobo.customshop.model.entity.User.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<User> {

    Optional<User> selectByEmail(String email) throws DaoException;

    List<User> selectByEmail(int offset, int length, String keyword) throws DaoException;

    Optional<User> selectByLogin(String login) throws DaoException;

    List<User> selectByLogin(int offset, int length, String keyword) throws DaoException;

    List<User> selectByRole(int offset, int length, String keyword) throws DaoException;

    List<User> selectByStatus(int offset, int length, String keyword) throws DaoException;
}