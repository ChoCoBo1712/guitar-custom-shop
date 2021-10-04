package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.Neck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.chocobo.customshop.model.dao.TableColumn.*;

/**
 * {@code NeckDao} interface extends {@link BaseDao}.
 * It provides means of manipulating stored {@link Neck} entities.
 * @author Evgeniy Sokolchik
 */
public interface NeckDao extends BaseDao<Neck> {

    /**
     * Select multiple {@link Neck} entities with wood id specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Neck> selectByWoodId(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Neck} entities with wood id specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByWoodId(String keyword) throws DaoException;

    /**
     * Select multiple {@link Neck} entities with fretboard wood id specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Neck> selectByFretboardWoodId(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Neck} entities with fretboard wood id specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByFretboardWoodId(String keyword) throws DaoException;

    /**
     * Select multiple {@link Neck} entities with name specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Neck> selectByName(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Neck} entities with name specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByName(String keyword) throws DaoException;

    /**
     * Select multiple {@link Neck} entities with concatenated name and wood specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Neck> selectByNameAndWood(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Neck} entities with concatenated name and wood specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByNameAndWood(String keyword) throws DaoException;

    @Override
    default Neck buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return (Neck) Neck.builder()
                .setName(resultSet.getString(NECK_NAME))
                .setWoodId(resultSet.getLong(ID_NECK_WOOD))
                .setFretboardId(resultSet.getLong(ID_FRETBOARD_WOOD))
                .setEntityId(resultSet.getLong(NECK_ID))
                .build();
    }
}
