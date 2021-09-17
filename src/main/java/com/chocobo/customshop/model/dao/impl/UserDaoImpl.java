package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.DatabaseConnectionException;
import com.chocobo.customshop.model.dao.TableColumn;
import com.chocobo.customshop.model.dao.UserDao;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.pool.DatabaseConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.chocobo.customshop.model.dao.TableColumn.*;

public class UserDaoImpl implements UserDao {

    private static UserDao instance;

    private static final String SELECT_ALL =
            "SELECT user_id, email, login, password_hash, salt, role, status " +
            "FROM users " +
            "WHERE status <> 'DELETED' " +
            "ORDER BY user_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_BY_ID =
            "SELECT user_id, email, login, password_hash, salt, role, status " +
            "FROM users " +
            "WHERE user_id = ?;";

    private static final String SELECT_MULTIPLE_BY_ID =
            "SELECT user_id, email, login, password_hash, salt, role, status " +
            "FROM users " +
            "WHERE user_id LIKE CONCAT('%', ?, '%') AND status <> 'DELETED' " +
            "ORDER BY user_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_BY_EMAIL =
            "SELECT user_id, email, login, password_hash, salt, role, status " +
            "FROM users " +
            "WHERE email = ?;";

    private static final String SELECT_MULTIPLE_BY_EMAIL =
            "SELECT user_id, email, login, password_hash, salt, role, status " +
            "FROM users " +
            "WHERE email LIKE CONCAT('%', ?, '%') AND status <> 'DELETED' " +
            "ORDER BY user_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_BY_LOGIN =
            "SELECT user_id, email, login, password_hash, salt, role, status " +
            "FROM users " +
            "WHERE login = ?;";

    private static final String SELECT_MULTIPLE_BY_ROLE =
            "SELECT user_id, email, login, password_hash, salt, role, status " +
            "FROM users " +
            "WHERE role LIKE CONCAT('%', ?, '%') AND status <> 'DELETED' " +
            "ORDER BY user_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_MULTIPLE_BY_STATUS =
            "SELECT user_id, email, login, password_hash, salt, role, status " +
            "FROM users " +
            "WHERE status LIKE CONCAT('%', ?, '%') AND status <> 'DELETED' " +
            "ORDER BY user_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_MULTIPLE_BY_LOGIN =
            "SELECT user_id, email, login, password_hash, salt, role, status " +
            "FROM users " +
            "WHERE login LIKE CONCAT('%', ?, '%') AND status <> 'DELETED' " +
            "ORDER BY user_id " +
            "LIMIT ?, ?;";

    private static final String INSERT =
            "INSERT INTO users(email, login, password_hash, salt, role, status)" +
            "VALUES(?, ?, ?, ?, ?, ?);";

    private static final String UPDATE =
            "UPDATE users " +
            "SET email = ?, login = ?, password_hash = ?, salt = ?, role = ?, status = ?" +
            "WHERE user_id = ?;";

    private static final String DELETE =
            "UPDATE users " +
            "SET status = 'DELETED'" +
            "WHERE user_id = ?;";

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
        Connection connection = null;
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getLogin());
            statement.setBytes(3, entity.getPasswordHash());
            statement.setBytes(4, entity.getSalt());
            statement.setString(5, entity.getRole().toString());
            statement.setString(6, entity.getStatus().toString());
            statement.setString(7, String.valueOf(entity.getEntityId()));
            statement.execute();

            statement.close();
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void delete(long id) throws DaoException {
        Connection connection = null;
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.execute();

            statement.close();
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
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
                user = (User) User.builder()
                        .setEmail(resultSet.getString(USER_EMAIL))
                        .setLogin(resultSet.getString(USER_LOGIN))
                        .setPasswordHash(resultSet.getBytes(USER_PASSWORD_HASH))
                        .setSalt(resultSet.getBytes(USER_SALT))
                        .setRole(User.UserRole.valueOf(resultSet.getString(USER_ROLE)))
                        .setStatus(User.UserStatus.valueOf(resultSet.getString(USER_STATUS)))
                        .setEntityId(resultSet.getLong(USER_ID))
                        .build();
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
    public List<User> selectById(int offset, int length, String keyword) throws DaoException {
        Connection connection = null;
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        List<User> userList = new ArrayList<>();
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_MULTIPLE_BY_ID);
            statement.setString(1, keyword);
            statement.setInt(2, offset);
            statement.setInt(3, length);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = (User) User.builder()
                        .setEmail(resultSet.getString(USER_EMAIL))
                        .setLogin(resultSet.getString(USER_LOGIN))
                        .setPasswordHash(resultSet.getBytes(USER_PASSWORD_HASH))
                        .setSalt(resultSet.getBytes(USER_SALT))
                        .setRole(User.UserRole.valueOf(resultSet.getString(USER_ROLE)))
                        .setStatus(User.UserStatus.valueOf(resultSet.getString(USER_STATUS)))
                        .setEntityId(resultSet.getLong(USER_ID))
                        .build();
                userList.add(user);
            }
            statement.close();
            return userList;
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<User> selectAll(int offset, int length) throws DaoException {
        Connection connection = null;
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        List<User> userList = new ArrayList<>();
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            statement.setInt(1, offset);
            statement.setInt(2, length);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = (User) User.builder()
                        .setEmail(resultSet.getString(USER_EMAIL))
                        .setLogin(resultSet.getString(USER_LOGIN))
                        .setPasswordHash(resultSet.getBytes(USER_PASSWORD_HASH))
                        .setSalt(resultSet.getBytes(USER_SALT))
                        .setRole(User.UserRole.valueOf(resultSet.getString(USER_ROLE)))
                        .setStatus(User.UserStatus.valueOf(resultSet.getString(USER_STATUS)))
                        .setEntityId(resultSet.getLong(USER_ID))
                        .build();
                userList.add(user);
            }
            statement.close();
            return userList;
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
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
                user = (User) User.builder()
                        .setEmail(resultSet.getString(USER_EMAIL))
                        .setLogin(resultSet.getString(USER_LOGIN))
                        .setPasswordHash(resultSet.getBytes(USER_PASSWORD_HASH))
                        .setSalt(resultSet.getBytes(USER_SALT))
                        .setRole(User.UserRole.valueOf(resultSet.getString(USER_ROLE)))
                        .setStatus(User.UserStatus.valueOf(resultSet.getString(USER_STATUS)))
                        .setEntityId(resultSet.getLong(USER_ID))
                        .build();
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
    public List<User> selectByEmail(int offset, int length, String keyword) throws DaoException {
        Connection connection = null;
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        List<User> userList = new ArrayList<>();
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_MULTIPLE_BY_EMAIL);
            statement.setString(1, keyword);
            statement.setInt(2, offset);
            statement.setInt(3, length);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = (User) User.builder()
                        .setEmail(resultSet.getString(USER_EMAIL))
                        .setLogin(resultSet.getString(USER_LOGIN))
                        .setPasswordHash(resultSet.getBytes(USER_PASSWORD_HASH))
                        .setSalt(resultSet.getBytes(USER_SALT))
                        .setRole(User.UserRole.valueOf(resultSet.getString(USER_ROLE)))
                        .setStatus(User.UserStatus.valueOf(resultSet.getString(USER_STATUS)))
                        .setEntityId(resultSet.getLong(USER_ID))
                        .build();
                userList.add(user);
            }
            statement.close();
            return userList;
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
                user = (User) User.builder()
                        .setEmail(resultSet.getString(USER_EMAIL))
                        .setLogin(resultSet.getString(USER_LOGIN))
                        .setPasswordHash(resultSet.getBytes(USER_PASSWORD_HASH))
                        .setSalt(resultSet.getBytes(USER_SALT))
                        .setRole(User.UserRole.valueOf(resultSet.getString(USER_ROLE)))
                        .setStatus(User.UserStatus.valueOf(resultSet.getString(USER_STATUS)))
                        .setEntityId(resultSet.getLong(USER_ID))
                        .build();
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
    public List<User> selectByLogin(int offset, int length, String keyword) throws DaoException {
        Connection connection = null;
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        List<User> userList = new ArrayList<>();
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_MULTIPLE_BY_LOGIN);
            statement.setString(1, keyword);
            statement.setInt(2, offset);
            statement.setInt(3, length);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = (User) User.builder()
                        .setEmail(resultSet.getString(USER_EMAIL))
                        .setLogin(resultSet.getString(USER_LOGIN))
                        .setPasswordHash(resultSet.getBytes(USER_PASSWORD_HASH))
                        .setSalt(resultSet.getBytes(USER_SALT))
                        .setRole(User.UserRole.valueOf(resultSet.getString(USER_ROLE)))
                        .setStatus(User.UserStatus.valueOf(resultSet.getString(USER_STATUS)))
                        .setEntityId(resultSet.getLong(USER_ID))
                        .build();
                userList.add(user);
            }
            statement.close();
            return userList;
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<User> selectByRole(int offset, int length, String keyword) throws DaoException {
        Connection connection = null;
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        List<User> userList = new ArrayList<>();
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_MULTIPLE_BY_ROLE);
            statement.setString(1, keyword);
            statement.setInt(2, offset);
            statement.setInt(3, length);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = (User) User.builder()
                        .setEmail(resultSet.getString(USER_EMAIL))
                        .setLogin(resultSet.getString(USER_LOGIN))
                        .setPasswordHash(resultSet.getBytes(USER_PASSWORD_HASH))
                        .setSalt(resultSet.getBytes(USER_SALT))
                        .setRole(User.UserRole.valueOf(resultSet.getString(USER_ROLE)))
                        .setStatus(User.UserStatus.valueOf(resultSet.getString(USER_STATUS)))
                        .setEntityId(resultSet.getLong(USER_ID))
                        .build();
                userList.add(user);
            }
            statement.close();
            return userList;
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<User> selectByStatus(int offset, int length, String keyword) throws DaoException {
        Connection connection = null;
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        List<User> userList = new ArrayList<>();
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_MULTIPLE_BY_STATUS);
            statement.setString(1, keyword);
            statement.setInt(2, offset);
            statement.setInt(3, length);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = (User) User.builder()
                        .setEmail(resultSet.getString(USER_EMAIL))
                        .setLogin(resultSet.getString(USER_LOGIN))
                        .setPasswordHash(resultSet.getBytes(USER_PASSWORD_HASH))
                        .setSalt(resultSet.getBytes(USER_SALT))
                        .setRole(User.UserRole.valueOf(resultSet.getString(USER_ROLE)))
                        .setStatus(User.UserStatus.valueOf(resultSet.getString(USER_STATUS)))
                        .setEntityId(resultSet.getLong(USER_ID))
                        .build();
                userList.add(user);
            }
            statement.close();
            return userList;
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
