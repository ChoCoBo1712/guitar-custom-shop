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

/**
 * {@code WoodDao} interface extends {@link BaseDao}.
 * It provides means of manipulating stored {@link Wood} entities.
 * @author Evgeniy Sokolchik
 */
public interface WoodDao extends BaseDao<Wood> {

    /**
     * Select multiple {@link Wood} entities with name specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Wood> selectByName(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Wood} entities with name specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByName(String keyword) throws DaoException;

    @Override
    default Wood buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return (Wood) Wood.builder()
                .setName(resultSet.getString(WOOD_NAME))
                .setEntityId(resultSet.getLong(WOOD_ID))
                .build();
    }
}
