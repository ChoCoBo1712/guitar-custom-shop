package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.Neck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.chocobo.customshop.model.dao.TableColumn.*;

public interface NeckDao extends BaseDao<Neck> {

    List<Neck> selectByWoodId(int offset, int length, String keyword) throws DaoException;

    long selectCountByWoodId(String keyword) throws DaoException;

    List<Neck> selectByFretboardWoodId(int offset, int length, String keyword) throws DaoException;

    long selectCountByFretboardWoodId(String keyword) throws DaoException;

    List<Neck> selectByName(int offset, int length, String keyword) throws DaoException;

    long selectCountByName(String keyword) throws DaoException;

    List<Neck> selectByNameAndWood(int offset, int length, String keyword) throws DaoException;

    long selectCountByNameAndWood(String keyword) throws DaoException;

    @Override
    default Neck buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return (Neck) Neck.builder()
                .setName(resultSet.getString(NECK_NAME))
                .setWoodId(resultSet.getLong(ID_NECK_WOOD))
                .setFretboardId(resultSet.getLong(ID_FRETBOARD_WOOD))
                .setEntityId(resultSet.getLong(NECK_ID))
                .build();
    }
}
