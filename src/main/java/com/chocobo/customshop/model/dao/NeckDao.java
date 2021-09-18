package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.model.entity.Neck;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface NeckDao extends BaseDao<Neck> {

    @Override
    default Neck buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }
}
