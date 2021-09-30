package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.Guitar;
import com.chocobo.customshop.model.entity.Neck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.chocobo.customshop.model.dao.TableColumn.*;
import static com.chocobo.customshop.model.entity.Guitar.*;

public interface GuitarDao extends BaseDao<Guitar> {

    List<Guitar> selectByName(int offset, int length, String keyword) throws DaoException;

    long selectCountByName(String keyword) throws DaoException;

    List<Guitar> selectByBodyId(int offset, int length, String keyword) throws DaoException;

    long selectCountByBodyId(String keyword) throws DaoException;

    List<Guitar> selectByNeckId(int offset, int length, String keyword) throws DaoException;

    long selectCountByNeckId(String keyword) throws DaoException;

    List<Guitar> selectByPickupId(int offset, int length, String keyword) throws DaoException;

    long selectCountByPickupId(String keyword) throws DaoException;

    List<Guitar> selectByUserId(int offset, int length, String keyword) throws DaoException;

    long selectCountByUserId(String keyword) throws DaoException;

    List<Guitar> selectByColor(int offset, int length, String keyword) throws DaoException;

    long selectCountByColor(String keyword) throws DaoException;

    List<Guitar> selectByNeckJoint(int offset, int length, String keyword) throws DaoException;

    long selectCountByNeckJoint(String keyword) throws DaoException;

    List<Guitar> selectByOrderStatus(int offset, int length, String keyword) throws DaoException;

    long selectCountByOrderStatus(String keyword) throws DaoException;

    List<Guitar> selectByNameForUser(int offset, int length, String keyword, String userId) throws DaoException;

    long selectCountByNameForUser(String keyword, String userId) throws DaoException;

    List<Guitar> selectAllForActiveOrder(int offset, int length) throws DaoException;

    long selectCountAllForActiveOrder() throws DaoException;

    List<Guitar> selectByNameForActiveOrder(int offset, int length, String keyword) throws DaoException;

    long selectCountByNameForActiveOrder(String keyword) throws DaoException;

    List<Guitar> selectByBodyIdForActiveOrder(int offset, int length, String keyword) throws DaoException;

    long selectCountByBodyIdForActiveOrder(String keyword) throws DaoException;

    List<Guitar> selectByNeckIdForActiveOrder(int offset, int length, String keyword) throws DaoException;

    long selectCountByNeckIdForActiveOrder(String keyword) throws DaoException;

    List<Guitar> selectByPickupIdForActiveOrder(int offset, int length, String keyword) throws DaoException;

    long selectCountByPickupIdForActiveOrder(String keyword) throws DaoException;

    List<Guitar> selectByUserIdForActiveOrder(int offset, int length, String keyword) throws DaoException;

    long selectCountByUserIdForActiveOrder(String keyword) throws DaoException;

    List<Guitar> selectByColorForActiveOrder(int offset, int length, String keyword) throws DaoException;

    long selectCountByColorForActiveOrder(String keyword) throws DaoException;

    List<Guitar> selectByNeckJointForActiveOrder(int offset, int length, String keyword) throws DaoException;

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
