package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.dao.BodyDao;
import com.chocobo.customshop.model.entity.Body;

import java.util.List;
import java.util.Optional;

public class BodyDaoImpl implements BodyDao {

    private static BodyDao instance;

    private static final String SELECT_ALL = """
            SELECT body_id, name, id_wood
            FROM bodies
            WHERE deleted <> 1
            ORDER BY body_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_ALL = """
            SELECT COUNT(body_id)
            FROM bodies
            WHERE deleted <> 1;
            """;

    private static final String SELECT_BY_ID = """
            SELECT body_id, name, id_wood
            FROM bodies
            WHERE body_id = ?;
            """;

    private static final String SELECT_MULTIPLE_BY_ID = """
            SELECT body_id, name, id_wood
            FROM bodies
            WHERE body_id LIKE CONCAT('%', ?, '%') AND deleted <> 1
            ORDER BY body_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_ID = """
            SELECT COUNT(body_id)
            FROM bodies
            WHERE body_id LIKE CONCAT('%', ?, '%') AND deleted <> 1;
            """;

    private static final String SELECT_MULTIPLE_BY_WOOD_ID = """
            SELECT body_id, name, id_wood
            FROM bodies
            WHERE id_wood LIKE CONCAT('%', ?, '%') AND deleted <> 1
            ORDER BY body_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_WOOD_ID = """
            SELECT COUNT(body_id)
            FROM bodies
            WHERE id_wood LIKE CONCAT('%', ?, '%') AND deleted <> 1;
            """;

    private static final String SELECT_MULTIPLE_BY_NAME = """
            SELECT body_id, name, id_wood
            FROM bodies
            WHERE name LIKE CONCAT('%', ?, '%') AND deleted <> 1
            ORDER BY body_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_NAME = """
            SELECT COUNT(body_id)
            FROM bodies
            WHERE name LIKE CONCAT('%', ?, '%') AND deleted <> 1;
            """;

    private static final String SELECT_MULTIPLE_BY_NAME_AND_WOOD = """
            SELECT body_id, bodies.name, id_wood
            FROM bodies
            INNER JOIN woods ON bodies.id_wood = woods.wood_id
            WHERE bodies.deleted + woods.deleted = 0 AND
            CONCAT(woods.name, ' ', bodies.name) LIKE CONCAT('%', ?, '%')
            ORDER BY body_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_NAME_AND_WOOD = """
            SELECT COUNT(body_id)
            FROM bodies
            INNER JOIN woods ON bodies.id_wood = woods.wood_id
            WHERE bodies.deleted + woods.deleted = 0 AND
            CONCAT(woods.name, ' ', bodies.name) LIKE CONCAT('%', ?, '%');
            """;

    private static final String INSERT = """
            INSERT INTO bodies(name, id_wood)
            VALUES(?, ?);
            """;

    private static final String UPDATE = """
            UPDATE bodies
            SET name = ?, id_wood = ?
            WHERE body_id = ?;
            """;

    private static final String DELETE = """
            UPDATE bodies
            SET deleted = 1
            WHERE body_id = ?;
            """;

    public static BodyDao getInstance() {
        if (instance == null) {
            instance = new BodyDaoImpl();
        }
        return instance;
    }

    @Override
    public long insert(Body entity) throws DaoException {
        return executeInsert(
                INSERT,
                entity.getName(),
                entity.getWoodId()
        );
    }

    @Override
    public void update(Body entity) throws DaoException {
        executeUpdateOrDelete(
                UPDATE,
                entity.getName(),
                entity.getWoodId(),
                entity.getEntityId()
        );
    }

    @Override
    public void delete(long id) throws DaoException {
        executeUpdateOrDelete(DELETE, id);
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
    public long selectCountById(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_ID, keyword);
    }

    @Override
    public List<Body> selectAll(int offset, int length) throws DaoException {
        return executeSelectMultiple(SELECT_ALL, offset, length);
    }

    @Override
    public long selectCountAll() throws DaoException {
        return executeSelectCount(SELECT_COUNT_ALL);
    }

    @Override
    public List<Body> selectByWoodId(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_WOOD_ID, keyword, offset, length);
    }

    @Override
    public long selectCountByWoodId(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_WOOD_ID, keyword);
    }

    @Override
    public List<Body> selectByName(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_NAME, keyword, offset, length);
    }

    @Override
    public long selectCountByName(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_NAME, keyword);
    }

    @Override
    public List<Body> selectByNameAndWood(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_NAME_AND_WOOD, keyword, offset, length);
    }

    @Override
    public long selectCountByNameAndWood(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_NAME_AND_WOOD, keyword);
    }
}
