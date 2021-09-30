package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.DatabaseConnectionException;
import com.chocobo.customshop.model.dao.BaseDao;
import com.chocobo.customshop.model.entity.AbstractEntity;
import com.chocobo.customshop.model.pool.DatabaseConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class QueryExecutor {

    Logger logger = LogManager.getLogger();

    private final Connection connection;
    private final boolean transaction;
    private boolean destroyed;

    private QueryExecutor(boolean transaction) throws DatabaseConnectionException, SQLException {
        this.transaction = transaction;
        connection = DatabaseConnectionPool.getInstance().getConnection();
        if (transaction) {
            connection.setAutoCommit(false);
        }
    }

    static QueryExecutor createExecutor(boolean transaction) throws DaoException {
        try {
            return new QueryExecutor(transaction);
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        }
    }

    static QueryExecutor createExecutor() throws DaoException {
        return createExecutor(false);
    }

    <T extends AbstractEntity> Optional<T> executeSelect(String sql, BaseDao<T> dao, Object... params) throws DaoException {
        T entity = null;
        try (PreparedStatement statement = prepareStatement(sql, Statement.NO_GENERATED_KEYS, params)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity = dao.buildEntityFromResultSet(resultSet);
            }
            return Optional.ofNullable(entity);
        } catch (SQLException e) {
            if (transaction) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DaoException(ex);
                }
            }
            destroyExecutor();
            throw new DaoException(e);
        } finally {
            if (!transaction && !destroyed) {
                destroyExecutor();
            }
        }
    }

    <T extends AbstractEntity> List<T> executeSelectMultiple(String sql, BaseDao<T> dao, Object... params)
            throws DaoException {
        try (PreparedStatement statement = prepareStatement(sql, Statement.NO_GENERATED_KEYS, params)) {
            ResultSet resultSet = statement.executeQuery();
            List<T> entityList = new ArrayList<>();
            while (resultSet.next()) {
                T entity = dao.buildEntityFromResultSet(resultSet);
                entityList.add(entity);
            }
            return entityList;
        } catch (SQLException e) {
            if (transaction) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DaoException(ex);
                }
            }
            destroyExecutor();
            throw new DaoException(e);
        } finally {
            if (!transaction && !destroyed) {
                destroyExecutor();
            }
        }
    }

    long executeSelectCount(String sql, Object... params) throws DaoException {
        try (PreparedStatement statement = prepareStatement(sql, Statement.NO_GENERATED_KEYS, params)) {
            ResultSet resultSet = statement.executeQuery();
            long count = 0;
            if (resultSet.next()) {
                count = resultSet.getLong(1);
            }
            return count;
        } catch (SQLException e) {
            if (transaction) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DaoException(ex);
                }
            }
            destroyExecutor();
            throw new DaoException(e);
        } finally {
            if (!transaction && !destroyed) {
                destroyExecutor();
            }
        }
    }

    void executeUpdateOrDelete(String sql, Object... params) throws DaoException {
        if (destroyed) {
            throw new DaoException("Context is already terminated");
        }

        try (PreparedStatement statement = prepareStatement(sql, Statement.NO_GENERATED_KEYS, params)) {
            statement.execute();
        } catch (SQLException e) {
            if (transaction) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DaoException(ex);
                }
            }
            destroyExecutor();
            throw new DaoException(e);
        } finally {
            if (!transaction && !destroyed) {
                destroyExecutor();
            }
        }
    }

    long executeInsert(String sql, Object... params) throws DaoException {
        try (PreparedStatement statement = prepareStatement(sql, Statement.RETURN_GENERATED_KEYS, params);) {
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            return generatedKeys.next() ? generatedKeys.getLong(1) : 0;
        } catch (SQLException e) {
            if (transaction) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DaoException(ex);
                }
            }
            destroyExecutor();
            throw new DaoException(e);
        } finally {
            if (!transaction && !destroyed) {
                destroyExecutor();
            }
        }
    }

    void commit() throws DaoException {
        if (destroyed) {
            throw new DaoException("Context is already terminated");
        }

        if (transaction) {
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        destroyExecutor();
    }

    private PreparedStatement prepareStatement(String sql, int generatedKeys, Object... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql, generatedKeys);
        int i = 1;
        for (Object param : params) {
            statement.setObject(i++, param);
        }
        return statement;
    }

    private void destroyExecutor() {
        destroyed = true;
        try {
            if (transaction) {
                connection.setAutoCommit(true);
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("An error occurred during closing connection: ", e);
        }
    }
}
