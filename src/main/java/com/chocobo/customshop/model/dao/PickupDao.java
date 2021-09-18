package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.model.entity.Pickup;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface PickupDao extends BaseDao<Pickup> {

    @Override
    default Pickup buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }
}
