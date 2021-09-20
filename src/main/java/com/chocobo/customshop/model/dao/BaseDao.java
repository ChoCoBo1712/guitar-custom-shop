package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.DatabaseConnectionException;
import com.chocobo.customshop.model.entity.AbstractEntity;
import com.chocobo.customshop.model.pool.DatabaseConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends AbstractEntity> {

    Logger logger = LogManager.getLogger();

    long insert(T entity) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(long id) throws DaoException;

    Optional<T> selectById(long id) throws DaoException;

    List<T> selectById(int offset, int length, String keyword) throws DaoException;

    long selectCountById(String keyword) throws DaoException;

    List<T> selectAll(int offset, int length) throws DaoException;

    long selectCountAll() throws DaoException;

    T buildEntityFromResultSet(ResultSet resultSet) throws SQLException;

    default void executeUpdateOrDelete(String sql, Object... params) throws DaoException {
        try (
            Connection connection = DatabaseConnectionPool.getInstance().getConnection();
            PreparedStatement statement = prepareStatement(sql, connection, Statement.NO_GENERATED_KEYS, params);
        ) {
            statement.execute();
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        }
    }

    default long executeInsert(String sql, Object... params) throws DaoException {
        try (
            Connection connection = DatabaseConnectionPool.getInstance().getConnection();
            PreparedStatement statement = prepareStatement(sql, connection, Statement.RETURN_GENERATED_KEYS, params);
        ) {
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            return generatedKeys.next() ? generatedKeys.getLong(1) : 0;
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        }
    }

    default Optional<T> executeSelect(String sql, Object... params) throws DaoException {
        T entity = null;
        try (
            Connection connection = DatabaseConnectionPool.getInstance().getConnection();
            PreparedStatement statement = prepareStatement(sql, connection, Statement.NO_GENERATED_KEYS, params)
        ) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity = buildEntityFromResultSet(resultSet);
            }
            return Optional.ofNullable(entity);
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        }
    }

    default List<T> executeSelectMultiple(String sql, Object... params) throws DaoException {
        List<T> entityList = new ArrayList<>();
        try (
            Connection connection = DatabaseConnectionPool.getInstance().getConnection();
            PreparedStatement statement = prepareStatement(sql, connection, Statement.NO_GENERATED_KEYS, params)
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T entity = buildEntityFromResultSet(resultSet);
                entityList.add(entity);
            }
            return entityList;
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        }
    }

    default long executeSelectCount(String sql, Object... params) throws DaoException {
        long count = 0;
        try (
            Connection connection = DatabaseConnectionPool.getInstance().getConnection();
            PreparedStatement statement = prepareStatement(sql, connection, Statement.NO_GENERATED_KEYS, params)
        ) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getLong(1);
            }
            return count;
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
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