package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.Guitar;
import com.chocobo.customshop.model.entity.Neck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.chocobo.customshop.model.dao.TableColumn.*;
import static com.chocobo.customshop.model.entity.Guitar.*;

/**
 * {@code GuitarDao} interface extends {@link BaseDao}.
 * It provides means of manipulating stored {@link Guitar} entities.
 * @author Evgeniy Sokolchik
 */
public interface GuitarDao extends BaseDao<Guitar> {

    /**
     * Select multiple entities {@link Guitar} with name specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByName(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities with name specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByName(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with body id specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByBodyId(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities with body id specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByBodyId(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with neck id specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByNeckId(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities with neck id specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByNeckId(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with pickup id specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByPickupId(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities with pickup id specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByPickupId(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with user id specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByUserId(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities with user id specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByUserId(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with color specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByColor(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities with color specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByColor(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with neck joint specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByNeckJoint(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities with neck joint specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByNeckJoint(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with order status specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByOrderStatus(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities with order status specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByOrderStatus(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with user id specified by parameter and name specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @param userId specifies user id.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByNameForUser(int offset, int length, String keyword, String userId) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities
     * with user id specified by parameter and name specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByNameForUser(String keyword, String userId) throws DaoException;

    /**
     * Select all {@link Guitar} entities with active {@link OrderStatus}.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectAllForActiveOrder(int offset, int length) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities with active {@link OrderStatus}.
     *
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountAllForActiveOrder() throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with name specified by keyword with active {@link OrderStatus}.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByNameForActiveOrder(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities
     * with name specified by keyword with active {@link OrderStatus}.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByNameForActiveOrder(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with body id specified by keyword with active {@link OrderStatus}.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByBodyIdForActiveOrder(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities
     * with body id specified by keyword with active {@link OrderStatus}.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByBodyIdForActiveOrder(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with neck id specified by keyword with active {@link OrderStatus}.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByNeckIdForActiveOrder(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities
     * with neck id specified by keyword with active {@link OrderStatus}.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByNeckIdForActiveOrder(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with pickup id specified by keyword with active {@link OrderStatus}.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByPickupIdForActiveOrder(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities
     * with pickup id specified by keyword with active {@link OrderStatus}.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByPickupIdForActiveOrder(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with user id specified by keyword with active {@link OrderStatus}.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByUserIdForActiveOrder(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities
     * with user id specified by keyword with active {@link OrderStatus}.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByUserIdForActiveOrder(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with color specified by keyword with active {@link OrderStatus}.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByColorForActiveOrder(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar}
     * entities with color specified by keyword with active {@link OrderStatus}.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByColorForActiveOrder(String keyword) throws DaoException;

    /**
     * Select multiple {@link Guitar} entities with neck joint specified by keyword with active {@link OrderStatus}.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Guitar> selectByNeckJointForActiveOrder(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Guitar} entities
     * with neck joint specified by keyword with active {@link OrderStatus}.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByNeckJointForActiveOrder(String keyword) throws DaoException;

    @Override
    default Guitar buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return (Guitar) builder()
                .setName(resultSet.getString(GUITAR_NAME))
                .setBodyId(resultSet.getLong(ID_BODY))
                .setNeckId(resultSet.getLong(ID_NECK))
                .setPickupId(resultSet.getLong(ID_PICKUP))
                .setUserId(resultSet.getLong(ID_USER))
                .setColor(resultSet.getString(COLOR))
                .setNeckJoint(NeckJoint.valueOf(resultSet.getString(NECK_JOINT)))
                .setOrderStatus(OrderStatus.valueOf(resultSet.getString(ORDER_STATUS)))
                .setPicturePath(resultSet.getString(PICTURE_PATH))
                .setEntityId(resultSet.getLong(GUITAR_ID))
                .build();
    }
}
