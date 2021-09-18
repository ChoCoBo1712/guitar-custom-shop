package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.User.UserRole;
import com.chocobo.customshop.model.entity.User.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.chocobo.customshop.model.dao.TableColumn.*;

public interface UserDao extends BaseDao<User> {

    Optional<User> selectByEmail(String email) throws DaoException;

    List<User> selectByEmail(int offset, int length, String keyword) throws DaoException;

    Optional<User> selectByLogin(String login) throws DaoException;

    List<User> selectByLogin(int offset, int length, String keyword) throws DaoException;

    List<User> selectByRole(int offset, int length, String keyword) throws DaoException;

    List<User> selectByStatus(int offset, int length, String keyword) throws DaoException;

    @Override
    default User buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return (User) User.builder()
                .setEmail(resultSet.getString(USER_EMAIL))
                .setLogin(resultSet.getString(USER_LOGIN))
                .setPasswordHash(resultSet.getBytes(USER_PASSWORD_HASH))
                .setSalt(resultSet.getBytes(USER_SALT))
                .setRole(UserRole.valueOf(resultSet.getString(USER_ROLE)))
                .setStatus(UserStatus.valueOf(resultSet.getString(USER_STATUS)))
                .setEntityId(resultSet.getLong(USER_ID))
                .build();
    }
}