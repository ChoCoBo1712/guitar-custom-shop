package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.Pickup;
import com.chocobo.customshop.model.entity.Wood;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.chocobo.customshop.model.dao.TableColumn.*;

/**
 * {@code PickupDao} interface extends {@link BaseDao}.
 * It provides means of manipulating stored {@link Pickup} entities.
 * @author Evgeniy Sokolchik
 */
public interface PickupDao extends BaseDao<Pickup> {

    /**
     * Select multiple {@link Pickup} entities with name specified by keyword.
     *
     * @param offset amount of records that will be skipped from start in the query result.
     * @param length amount of records that will appear in the query result.
     * @param keyword specifies what pattern should entity's field match.
     * @return {@link List} of queried entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    List<Pickup> selectByName(int offset, int length, String keyword) throws DaoException;

    /**
     * Select total count of all stored {@link Pickup} entities with name specified by keyword.
     *
     * @param keyword specifies what pattern should entity's field match.
     * @return {@code long} value that represents number of found entities.
     * @throws DaoException if an error occurred while processing the query.
     */
    long selectCountByName(String keyword) throws DaoException;

    @Override
    default Pickup buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return (Pickup) Pickup.builder()
                .setName(resultSet.getString(PICKUP_NAME))
                .setEntityId(resultSet.getLong(PICKUP_ID))
                .build();
    }
}
