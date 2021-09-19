package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.Pickup;
import com.chocobo.customshop.model.entity.Wood;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.chocobo.customshop.model.dao.TableColumn.*;

public interface PickupDao extends BaseDao<Pickup> {

    List<Pickup> selectByName(int offset, int length, String keyword) throws DaoException;

    @Override
    default Pickup buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return (Pickup) Pickup.builder()
                .setName(resultSet.getString(PICKUP_NAME))
                .setEntityId(resultSet.getLong(PICKUP_ID))
                .build();
    }
}
