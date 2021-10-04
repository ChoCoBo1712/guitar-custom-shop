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

/**
 * {@code BaseDao} is a top interface in the DAO hierarchy.
 * Contains base data manipulation methods applicable to all subclasses.
 *
 * @param <T> the type parameter extends {@link AbstractEntity} and stands for entity related to DAO.
 * @author Evgeniy Sokolchik
 */
public interface BaseDao<T extends AbstractEntity> {

    /**
     * Insert new entity.
     *
     * @param entity is a new entity to insert.
     * @return {{@code long} generated primary key.
     * @throws DaoException if an error occurred while processing the query.
     */
    long insert(T entity) throws DaoException;

    /**
     * Update existing entity.
     *
     * @param entity object with new data.
     * @throws DaoException if an error occurred while processing the query.
     */
    void update(T entity) throws DaoException;

    /**
     * Delete entity by id.
     *
     * @param id unique id of the entity.
     * @throws DaoException if an error occurred while processing the query.
     */
    void delete(long id) throws DaoException;

    /**
     * Select a single entity with specified id.
     *
     * @param id unique id of the entity.
     * @return entity wrapped with {@link Optional}.
     * @throws DaoException if an error occurred while processing the query.
     */
    Optional<T> selectById(long id) throws DaoException;

    /**
     * Select multiple entities with id specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<T> selectById(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored entities with id specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountById(String keyword) throws DaoException;

    /**
     * Select all stored entities.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<T> selectAll(int offset, int length) throws DaoException;

    /**
     * Select total count of all stored entities.
     *
     * @return {@code long} value that represents number of found entities
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountAll() throws DaoException;

    /**
     * Describes the way to build entity from given {@link ResultSet}.
     *
     * @param resultSet to build entity from.
     * @return entity.
     * @throws SQLException if an error occurred while getting params.
     */
    T buildEntityFromResultSet(ResultSet resultSet) throws SQLException;
}