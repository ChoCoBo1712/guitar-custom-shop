package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.DatabaseConnectionException;
import com.chocobo.customshop.model.entity.AbstractEntity;
import com.chocobo.customshop.model.pool.DatabaseConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.chocobo.customshop.model.entity.AbstractEntity.*;

public interface BaseDao<T extends AbstractEntity> {

    long insert(T entity) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(long id) throws DaoException;

    Optional<T> selectById(long id) throws DaoException;

    List<T> selectById(int offset, int length, String keyword) throws DaoException;

    List<T> selectAll(int offset, int length) throws DaoException;

    T buildEntityFromResultSet(ResultSet resultSet) throws SQLException;

    default void executeUpdateOrDelete(String sql, Object... params) throws DaoException {
        Connection connection = null;
        try {
            connection = DatabaseConnectionPool.getInstance().getConnection();
            PreparedStatement statement = prepareStatement(sql, connection, Statement.NO_GENERATED_KEYS, params);
            statement.execute();
            statement.close();
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    default long executeInsert(String sql, Object... params) throws DaoException {
        Connection connection = null;
        try {
            connection = DatabaseConnectionPool.getInstance().getConnection();
            PreparedStatement statement = prepareStatement(sql, connection, Statement.RETURN_GENERATED_KEYS, params);
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            long generatedKey = generatedKeys.next() ? generatedKeys.getLong(1) : 0;
            statement.close();
            return generatedKey;
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    default Optional<T> executeSelect(String sql, Object... params) throws DaoException {
        Connection connection = null;
        T entity = null;
        try {
            connection = DatabaseConnectionPool.getInstance().getConnection();
            PreparedStatement statement = prepareStatement(sql, connection, Statement.RETURN_GENERATED_KEYS, params);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity = buildEntityFromResultSet(resultSet);
            }
            statement.close();
            return Optional.ofNullable(entity);
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    default List<T> executeSelectMultiple(String sql, Object... params) throws DaoException {
        Connection connection = null;
        List<T> entityList = new ArrayList<>();
        try {
            connection = DatabaseConnectionPool.getInstance().getConnection();
            PreparedStatement statement = prepareStatement(sql, connection, Statement.RETURN_GENERATED_KEYS, params);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T entity = buildEntityFromResultSet(resultSet);
                entityList.add(entity);
            }
            statement.close();
            return entityList;
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private PreparedStatement prepareStatement(String sql, Connection connection, int generatedKeys, Object... params) throws DatabaseConnectionException, SQLException {
        PreparedStatement statement = connection.prepareStatement(sql, generatedKeys);
        int i = 1;
        for (Object param : params) {
            statement.setObject(i++, param);
        }
        return statement;
    }
}