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

    private static final String SELECT_ALL = """
            SELECT user_id, email, login, password_hash, salt, role, status
            FROM users
            WHERE status <> 'DELETED'
            ORDER BY user_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_ALL = """
            SELECT COUNT(user_id)
            FROM users
            WHERE status <> 'DELETED';
            """;

    private static final String SELECT_BY_ID = """
            SELECT user_id, email, login, password_hash, salt, role, status
            FROM users
            WHERE user_id = ?;
            """;

    private static final String SELECT_MULTIPLE_BY_ID = """
            SELECT user_id, email, login, password_hash, salt, role, status
            FROM users
            WHERE user_id LIKE CONCAT('%', ?, '%') AND status <> 'DELETED'
            ORDER BY user_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_ID = """
            SELECT COUNT(user_id)
            FROM users
            WHERE user_id LIKE CONCAT('%', ?, '%') AND status <> 'DELETED';
            """;

    private static final String SELECT_BY_EMAIL = """
            SELECT user_id, email, login, password_hash, salt, role, status
            FROM users
            WHERE email = ?;
            """;

    private static final String SELECT_MULTIPLE_BY_EMAIL = """
            SELECT user_id, email, login, password_hash, salt, role, status
            FROM users
            WHERE email LIKE CONCAT('%', ?, '%') AND status <> 'DELETED'
            ORDER BY user_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_EMAIL = """
            SELECT COUNT(user_id)
            FROM users
            WHERE email LIKE CONCAT('%', ?, '%') AND status <> 'DELETED';
            """;

    private static final String SELECT_BY_LOGIN = """
            SELECT user_id, email, login, password_hash, salt, role, status
            FROM users
            WHERE login = ?;
            """;

    private static final String SELECT_MULTIPLE_BY_ROLE = """
            SELECT user_id, email, login, password_hash, salt, role, status
            FROM users
            WHERE role LIKE ? AND status <> 'DELETED'
            ORDER BY user_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_ROLE = """
            SELECT COUNT(user_id)
            FROM users
            WHERE role LIKE ? AND status <> 'DELETED';
            """;

    private static final String SELECT_MULTIPLE_BY_STATUS = """
            SELECT user_id, email, login, password_hash, salt, role, status
            FROM users
            WHERE status LIKE ? AND status <> 'DELETED'
            ORDER BY user_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_STATUS = """
            SELECT COUNT(user_id)
            FROM users
            WHERE status LIKE ? AND status <> 'DELETED';
            """;

    private static final String SELECT_MULTIPLE_BY_LOGIN = """
            SELECT user_id, email, login, password_hash, salt, role, status
            FROM users
            WHERE login LIKE CONCAT('%', ?, '%') AND status <> 'DELETED'
            ORDER BY user_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_LOGIN = """
            SELECT COUNT(user_id)
            FROM users
            WHERE login LIKE CONCAT('%', ?, '%') AND status <> 'DELETED';
            """;

    private static final String INSERT = """
            INSERT INTO users(email, login, password_hash, salt, role, status)
            VALUES(?, ?, ?, ?, ?, ?);
            """;

    private static final String UPDATE = """
            UPDATE users
            SET email = ?, login = ?, password_hash = ?, salt = ?, role = ?, status = ?
            WHERE user_id = ?;
            """;

    private static final String DELETE = """
            UPDATE users
            SET status = 'DELETED'
            WHERE user_id = ?;
            """;

    private static final String DELETE_GUITARS_BY_USER = """
            UPDATE guitars
            SET deleted = 1
            WHERE id_user = ?;
            """;

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public long insert(User entity) throws DaoException {
        return QueryExecutor.createExecutor().executeInsert(
                INSERT,
                entity.getEmail(),
                entity.getLogin(),
                entity.getPasswordHash(),
                entity.getSalt(),
                entity.getRole().toString(),
                entity.getStatus().toString()
        );
    }

    @Override
    public void update(User entity) throws DaoException {
        QueryExecutor.createExecutor().executeUpdateOrDelete(
                UPDATE,
                entity.getEmail(),
                entity.getLogin(),
                entity.getPasswordHash(),
                entity.getSalt(),
                entity.getRole().toString(),
                entity.getStatus().toString(),
                entity.getEntityId()
        );
    }

    @Override
    public void delete(long id) throws DaoException {
        QueryExecutor executor = QueryExecutor.createExecutor(true);
        executor.executeUpdateOrDelete(DELETE, id);
        executor.executeUpdateOrDelete(DELETE_GUITARS_BY_USER, id);
        executor.commit();
    }

    @Override
    public Optional<User> selectById(long id) throws DaoException {
        return QueryExecutor.createExecutor().executeSelect(SELECT_BY_ID, this, id);
    }

    @Override
    public List<User> selectById(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_ID, this, keyword, offset, length);
    }

    @Override
    public long selectCountById(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_ID, keyword);
    }

    @Override
    public List<User> selectAll(int offset, int length) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectMultiple(SELECT_ALL, this, offset, length);
    }

    @Override
    public long selectCountAll() throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_ALL);
    }

    @Override
    public Optional<User> selectByEmail(String email) throws DaoException {
        return QueryExecutor.createExecutor().executeSelect(SELECT_BY_EMAIL, this, email);
    }

    @Override
    public List<User> selectByEmail(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_EMAIL, this, keyword, offset, length);
    }

    @Override
    public long selectCountByEmail(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_EMAIL, keyword);
    }

    @Override
    public Optional<User> selectByLogin(String login) throws DaoException {
        return QueryExecutor.createExecutor().executeSelect(SELECT_BY_LOGIN, this, login);
    }

    @Override
    public List<User> selectByLogin(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_LOGIN, this, keyword, offset, length);
    }

    @Override
    public long selectCountByLogin(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_LOGIN, keyword);
    }

    @Override
    public List<User> selectByRole(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_ROLE, this, keyword, offset, length);
    }

    @Override
    public long selectCountByRole(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_ROLE, keyword);
    }

    @Override
    public List<User> selectByStatus(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_STATUS, this, keyword, offset, length);
    }

    @Override
    public long selectCountByStatus(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_STATUS, keyword);
    }
}
