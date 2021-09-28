package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.dao.PickupDao;
import com.chocobo.customshop.model.entity.Pickup;
import com.chocobo.customshop.model.entity.Wood;

import java.util.List;
import java.util.Optional;

public class PickupDaoImpl implements PickupDao {

    private static PickupDao instance;

    private static final String SELECT_ALL = """
            SELECT pickup_id, name
            FROM pickups
            WHERE deleted <> 1
            ORDER BY pickup_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_ALL = """
            SELECT COUNT(pickup_id)
            FROM pickups
            WHERE deleted <> 1;
            """;

    private static final String SELECT_BY_ID = """
            SELECT pickup_id, name
            FROM pickups
            WHERE pickup_id = ?;
            """;

    private static final String SELECT_MULTIPLE_BY_ID = """
            SELECT pickup_id, name
            FROM pickups
            WHERE pickup_id LIKE CONCAT('%', ?, '%') AND deleted <> 1
            ORDER BY pickup_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_ID = """
            SELECT COUNT(pickup_id)
            FROM pickups
            WHERE pickup_id LIKE CONCAT('%', ?, '%') AND deleted <> 1;
            """;

    private static final String SELECT_MULTIPLE_BY_NAME = """
            SELECT pickup_id, name
            FROM pickups
            WHERE name LIKE CONCAT('%', ?, '%') AND deleted <> 1
            ORDER BY pickup_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_NAME = """
            SELECT COUNT(pickup_id)
            FROM pickups
            WHERE name LIKE CONCAT('%', ?, '%') AND deleted <> 1;
            """;

    private static final String INSERT = """
            INSERT INTO pickups(name)
            VALUES(?);
            """;

    private static final String UPDATE = """
            UPDATE pickups
            SET name = ?
            WHERE pickup_id = ?;
            """;

    private static final String DELETE = """
            UPDATE pickups
            SET deleted = 1
            WHERE pickup_id = ?;
            """;

    public static PickupDao getInstance() {
        if (instance == null) {
            instance = new PickupDaoImpl();
        }
        return instance;
    }

    @Override
    public long insert(Pickup entity) throws DaoException {
        return executeInsert(INSERT, entity.getName());
    }

    @Override
    public void update(Pickup entity) throws DaoException {
        executeUpdateOrDelete(UPDATE, entity.getName(), entity.getEntityId());
    }

    @Override
    public void delete(long id) throws DaoException {
        executeUpdateOrDelete(DELETE, id);
    }

    @Override
    public Optional<Pickup> selectById(long id) throws DaoException {
        return executeSelect(SELECT_BY_ID, id);
    }

    @Override
    public List<Pickup> selectById(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_ID, keyword, offset, length);
    }

    @Override
    public long selectCountById(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_ID, keyword);
    }

    @Override
    public List<Pickup> selectAll(int offset, int length) throws DaoException {
        return executeSelectMultiple(SELECT_ALL, offset, length);
    }

    @Override
    public long selectCountAll() throws DaoException {
        return executeSelectCount(SELECT_COUNT_ALL);
    }

    @Override
    public List<Pickup> selectByName(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_NAME, keyword, offset, length);
    }

    @Override
    public long selectCountByName(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_NAME, keyword);
    }
}
