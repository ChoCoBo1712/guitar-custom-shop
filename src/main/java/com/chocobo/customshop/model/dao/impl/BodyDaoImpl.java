package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.dao.BodyDao;
import com.chocobo.customshop.model.entity.Body;

import java.util.List;
import java.util.Optional;

public class BodyDaoImpl implements BodyDao {

    private static BodyDao instance;

    private static final String SELECT_ALL =
            "SELECT body_id, name, id_wood " +
            "FROM bodies " +
            "WHERE deleted <> 1 " +
            "LIMIT ?, ?;";

    private static final String SELECT_BY_ID =
            "SELECT body_id, name, id_wood " +
            "FROM bodies " +
            "WHERE body_id = ?;";

    private static final String SELECT_MULTIPLE_BY_ID =
            "SELECT body_id, name, id_wood " +
            "FROM bodies " +
            "WHERE body_id LIKE CONCAT('%', ?, '%') AND deleted <> 1 " +
            "ORDER BY body_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_MULTIPLE_BY_WOOD_ID =
            "SELECT body_id, name, id_wood " +
            "FROM bodies " +
            "WHERE id_wood LIKE CONCAT('%', ?, '%') AND deleted <> 1 " +
            "ORDER BY body_id " +
            "LIMIT ?, ?;";

    public static BodyDao getInstance() {
        if (instance == null) {
            instance = new BodyDaoImpl();
        }
        return instance;
    }

    @Override
    public long insert(Body entity) throws DaoException {
        return 0;
    }

    @Override
    public void update(Body entity) throws DaoException {

    }

    @Override
    public void delete(long id) throws DaoException {

    }

    @Override
    public Optional<Body> selectById(long id) throws DaoException {
        return executeSelect(SELECT_BY_ID, id);
    }

    @Override
    public List<Body> selectById(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_ID, keyword, offset, length);
    }

    @Override
    public List<Body> selectAll(int offset, int length) throws DaoException {
        return executeSelectMultiple(SELECT_ALL, offset, length);
    }

    @Override
    public List<Body> selectByWoodId(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_WOOD_ID, keyword, offset, length);
    }
}
