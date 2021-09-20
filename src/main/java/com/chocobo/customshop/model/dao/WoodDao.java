package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.Wood;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.chocobo.customshop.model.dao.TableColumn.WOOD_ID;
import static com.chocobo.customshop.model.dao.TableColumn.WOOD_NAME;

public interface WoodDao extends BaseDao<Wood> {

    List<Wood> selectByName(int offset, int length, String keyword) throws DaoException;

    long selectCountByName(String keyword) throws DaoException;

    @Override
    default Wood buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return (Wood) Wood.builder()
                .setName(resultSet.getString(WOOD_NAME))
                .setEntityId(resultSet.getLong(WOOD_ID))
                .build();
    }
}
