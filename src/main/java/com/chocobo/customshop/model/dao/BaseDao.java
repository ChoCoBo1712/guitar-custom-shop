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

    long insert(T entity) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(long id) throws DaoException;

    Optional<T> selectById(long id) throws DaoException;

    List<T> selectById(int offset, int length, String keyword) throws DaoException;

    long selectCountById(String keyword) throws DaoException;

    List<T> selectAll(int offset, int length) throws DaoException;

    long selectCountAll() throws DaoException;

    T buildEntityFromResultSet(ResultSet resultSet) throws SQLException;
}