package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.model.entity.Body;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BodyDao extends BaseDao<Body> {

    @Override
    default Body buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }
}
