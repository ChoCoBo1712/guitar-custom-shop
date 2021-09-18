package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.model.entity.Guitar;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface GuitarDao extends BaseDao<Guitar> {

    @Override
    default Guitar buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }
}
