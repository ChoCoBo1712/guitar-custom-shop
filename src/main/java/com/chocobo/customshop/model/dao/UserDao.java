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

/**
 * {@code UserDao} interface extends {@link BaseDao}.
 * It provides means of manipulating stored {@link User} entities.
 * @author Evgeniy Sokolchik
 */
public interface UserDao extends BaseDao<User> {

    /**
     * Select a single {@link User} entity with specified email.
     *
     * @param email is a field of the entity.
     * @return entity wrapped with {@link Optional}.
     * @throws DaoException if an error occurred while processing the query.
     */
    Optional<User> selectByEmail(String email) throws DaoException;

    /**
     * Select multiple {@link User} entities with email specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<User> selectByEmail(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link User} entities with email specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByEmail(String keyword) throws DaoException;

    /**
     * Select a single {@link User} entity with specified login.
     *
     * @param login is a field of the entity.
     * @return entity wrapped with {@link Optional}.
     * @throws DaoException if an error occurred while processing the query.
     */
    Optional<User> selectByLogin(String login) throws DaoException;

    /**
     * Select multiple {@link User} entities with login specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<User> selectByLogin(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link User} entities with login specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByLogin(String keyword) throws DaoException;

    /**
     * Select multiple {@link User} entities with role specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<User> selectByRole(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link User} entities with role specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByRole(String keyword) throws DaoException;

    /**
     * Select multiple {@link User} entities with status specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<User> selectByStatus(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link User} entities with status specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByStatus(String keyword) throws DaoException;

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