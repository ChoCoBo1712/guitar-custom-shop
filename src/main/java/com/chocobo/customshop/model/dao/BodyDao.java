package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.Body;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.chocobo.customshop.model.dao.TableColumn.*;

/**
 * {@code BodyDao} interface extends {@link BaseDao}.
 * It provides means of manipulating stored {@link Body} entities.
 * @author Evgeniy Sokolchik
 */
public interface BodyDao extends BaseDao<Body> {

    /**
     * Select multiple {@link Body} entities with wood id specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Body> selectByWoodId(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Body} entities with wood id specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByWoodId(String keyword) throws DaoException;

    /**
     * Select multiple {@link Body} entities with name specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Body> selectByName(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Body} entities with name specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByName(String keyword) throws DaoException;

    /**
     * Select multiple {@link Body} entities with concatenated name and wood name specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Body> selectByNameAndWood(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Body} entities with concatenated name and wood name specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByNameAndWood(String keyword) throws DaoException;

    @Override
    default Body buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return (Body) Body.builder()
                .setName(resultSet.getString(BODY_NAME))
                .setWoodId(resultSet.getLong(ID_WOOD))
                .setEntityId(resultSet.getLong(BODY_ID))
                .build();
    }
}
