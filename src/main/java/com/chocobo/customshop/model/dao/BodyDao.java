package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.Body;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.chocobo.customshop.model.dao.TableColumn.*;

public interface BodyDao extends BaseDao<Body> {

    List<Body> selectByWoodId(int offset, int length, String keyword) throws DaoException;

    long selectCountByWoodId(String keyword) throws DaoException;

    List<Body> selectByName(int offset, int length, String keyword) throws DaoException;

    long selectCountByName(String keyword) throws DaoException;

    @Override
    default Body buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return (Body) Body.builder()
                .setName(resultSet.getString(BODY_NAME))
                .setWoodId(resultSet.getLong(ID_WOOD))
                .setEntityId(resultSet.getLong(BODY_ID))
                .build();
    }
}
