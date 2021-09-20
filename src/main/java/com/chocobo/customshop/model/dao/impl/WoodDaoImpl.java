package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.dao.WoodDao;
import com.chocobo.customshop.model.entity.Wood;

import java.util.List;
import java.util.Optional;

public class WoodDaoImpl implements WoodDao {

    private static WoodDao instance;

    private static final String SELECT_ALL =
            "SELECT wood_id, name " +
            "FROM woods " +
            "WHERE deleted <> 1 " +
            "ORDER BY wood_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_COUNT_ALL =
            "SELECT COUNT(wood_id) " +
            "FROM woods " +
            "WHERE deleted <> 1;";

    private static final String SELECT_BY_ID =
            "SELECT wood_id, name " +
            "FROM woods " +
            "WHERE wood_id = ?;";

    private static final String SELECT_MULTIPLE_BY_ID =
            "SELECT wood_id, name " +
            "FROM woods " +
            "WHERE wood_id LIKE CONCAT('%', ?, '%') AND deleted <> 1 " +
            "ORDER BY wood_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_COUNT_BY_ID =
            "SELECT COUNT(wood_id) " +
            "FROM woods " +
            "WHERE wood_id LIKE CONCAT('%', ?, '%') AND deleted <> 1;";

    private static final String SELECT_MULTIPLE_BY_NAME =
            "SELECT wood_id, name " +
            "FROM woods " +
            "WHERE name LIKE CONCAT('%', ?, '%') AND deleted <> 1 " +
            "ORDER BY wood_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_COUNT_BY_NAME =
            "SELECT COUNT(wood_id) " +
            "FROM woods " +
            "WHERE name LIKE CONCAT('%', ?, '%') AND deleted <> 1;";

    private static final String INSERT =
            "INSERT INTO woods(name) " +
            "VALUES(?);";

    private static final String UPDATE =
            "UPDATE woods " +
            "SET name = ? " +
            "WHERE wood_id = ?;";

    private static final String DELETE =
            "UPDATE woods " +
            "SET deleted = 1 " +
            "WHERE wood_id = ?;";

    public static WoodDao getInstance() {
        if (instance == null) {
            instance = new WoodDaoImpl();
        }
        return instance;
    }

    @Override
    public long insert(Wood entity) throws DaoException {
        return executeInsert(INSERT, entity.getName());
    }

    @Override
    public void update(Wood entity) throws DaoException {
        executeUpdateOrDelete(UPDATE, entity.getName(), entity.getEntityId());
    }

    @Override
    public void delete(long id) throws DaoException {
        executeUpdateOrDelete(DELETE, id);
    }

    @Override
    public Optional<Wood> selectById(long id) throws DaoException {
        return executeSelect(SELECT_BY_ID, id);
    }

    @Override
    public List<Wood> selectById(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_ID, keyword, offset, length);
    }

    @Override
    public long selectCountById(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_ID, keyword);
    }

    @Override
    public List<Wood> selectAll(int offset, int length) throws DaoException {
        return executeSelectMultiple(SELECT_ALL, offset, length);
    }

    @Override
    public long selectCountAll() throws DaoException {
        return executeSelectCount(SELECT_COUNT_ALL);
    }

    @Override
    public List<Wood> selectByName(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_NAME, keyword, offset, length);
    }

    @Override
    public long selectCountByName(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_NAME, keyword);
    }
}
