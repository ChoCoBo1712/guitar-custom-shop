package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.DatabaseConnectionException;
import com.chocobo.customshop.model.dao.UserDao;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.pool.DatabaseConnectionPool;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static UserDao instance;

    private static final String SELECT_BY_ID =
            "SELECT user_id, email, login, password_hash, salt, role, status " +
            "FROM users " +
            "WHERE user_id = ?;";

    private static final String SELECT_BY_EMAIL =
            "SELECT user_id, email, login, password_hash, salt, role, status " +
            "FROM users " +
            "WHERE email = ?;";

    private static final String SELECT_BY_LOGIN =
            "SELECT user_id, email, login, password_hash, salt, role, status " +
            "FROM users " +
            "WHERE login = ?;";

    private static final String INSERT =
            "INSERT INTO users(email, login, password_hash, salt, role, status)" +
            "VALUES(?, ?, ?, ?, ?, ?);";

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public long insert(User entity) throws DaoException {
        Connection connection = null;
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getLogin());
            statement.setBytes(3, entity.getPasswordHash());
            statement.setBytes(4, entity.getSalt());
            statement.setString(5, entity.getRole().toString());
            statement.setString(6, entity.getStatus().toString());
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            long generatedKey = generatedKeys.next() ? generatedKeys.getLong(1) : 0;
            statement.close();
            return generatedKey;
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void update(User entity) throws DaoException {

    }

    @Override
    public void delete(long id) throws DaoException {

    }

    @Override
    public Optional<User> selectById(long id) throws DaoException {
        Connection connection = null;
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        User user = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getBytes(4),
                        resultSet.getBytes(5),
                        User.UserRole.valueOf(resultSet.getString(6)),
                        User.UserStatus.valueOf(resultSet.getString(7))
                );
            }
            statement.close();
            return Optional.ofNullable(user);
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<User> selectAll(int offset) throws DaoException {
        return null;
    }

    @Override
    public Optional<User> selectByEmail(String email) throws DaoException {
        Connection connection = null;
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        User user = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getBytes(4),
                        resultSet.getBytes(5),
                        User.UserRole.valueOf(resultSet.getString(6)),
                        User.UserStatus.valueOf(resultSet.getString(7))
                );
            }
            statement.close();
            return Optional.ofNullable(user);
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public Optional<User> selectByLogin(String login) throws DaoException {
        Connection connection = null;
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        User user = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getBytes(4),
                        resultSet.getBytes(5),
                        User.UserRole.valueOf(resultSet.getString(6)),
                        User.UserStatus.valueOf(resultSet.getString(7))
                );
            }
            statement.close();
            return Optional.ofNullable(user);
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
