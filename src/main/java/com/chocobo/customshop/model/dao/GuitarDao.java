package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.Guitar;
import com.chocobo.customshop.model.entity.Neck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.chocobo.customshop.model.dao.TableColumn.*;

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

    List<Guitar> selectByNameForUser(int offset, int length, String keyword, String userId) throws DaoException;

    long selectCountByNameForUser(String keyword, String userId) throws DaoException;

    @Override
    default Guitar buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return (Guitar) Guitar.builder()
                .setName(resultSet.getString(GUITAR_NAME))
                .setBodyId(resultSet.getLong(ID_BODY))
                .setNeckId(resultSet.getLong(ID_NECK))
                .setPickupId(resultSet.getLong(ID_PICKUP))
                .setUserId(resultSet.getLong(ID_USER))
                .setColor(resultSet.getString(COLOR))
                .setNeckJoint(Guitar.NeckJoint.valueOf(resultSet.getString(NECK_JOINT)))
                .setPicturePath(resultSet.getString(PICTURE_PATH))
                .setEntityId(resultSet.getLong(GUITAR_ID))
                .build();
    }
}
