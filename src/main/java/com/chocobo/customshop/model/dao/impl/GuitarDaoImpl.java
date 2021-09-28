package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.dao.GuitarDao;
import com.chocobo.customshop.model.entity.Guitar;

import java.util.List;
import java.util.Optional;

public class GuitarDaoImpl implements GuitarDao {

    private static GuitarDao instance;

    private static final String INSERT = """
            INSERT INTO guitars(name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?);
            """;

    private static final String UPDATE = """
            UPDATE guitars
            SET name = ?, picture_path = ?, id_body = ?, id_neck = ?, id_pickup = ?, id_user = ?, color = ?, neck_joint = ?
            WHERE guitar_id = ?;
            """;

    private static final String DELETE = """
            UPDATE guitars
            SET deleted = 1
            WHERE guitar_id = ?;
            """;

    private static final String SELECT_BY_ID = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint
            FROM guitars
            WHERE guitar_id = ?;
            """;

    private static final String SELECT_MULTIPLE_BY_ID = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint
            FROM guitars
            WHERE guitar_id LIKE CONCAT('%', ?, '%') AND deleted <> 1
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_ID = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE guitar_id LIKE CONCAT('%', ?, '%') AND deleted <> 1;
            """;

    private static final String SELECT_ALL = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint
            FROM guitars
            WHERE deleted <> 1
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_ALL = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE deleted <> 1;
            """;

    private static final String SELECT_MULTIPLE_BY_NAME = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint
            FROM guitars
            WHERE name LIKE CONCAT('%', ?, '%') AND deleted <> 1
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_NAME = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE name LIKE CONCAT('%', ?, '%') AND deleted <> 1;
            """;

    private static final String SELECT_MULTIPLE_BY_BODY_ID = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint
            FROM guitars
            WHERE id_body LIKE CONCAT('%', ?, '%') AND deleted <> 1
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_BODY_ID = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE id_body LIKE CONCAT('%', ?, '%') AND deleted <> 1;
            """;

    private static final String SELECT_MULTIPLE_BY_NECK_ID = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint
            FROM guitars
            WHERE id_neck LIKE CONCAT('%', ?, '%') AND deleted <> 1
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_NECK_ID = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE id_neck LIKE CONCAT('%', ?, '%') AND deleted <> 1;
            """;

    private static final String SELECT_MULTIPLE_BY_PICKUP_ID = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint
            FROM guitars
            WHERE id_pickup LIKE CONCAT('%', ?, '%') AND deleted <> 1
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_PICKUP_ID = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE id_pickup LIKE CONCAT('%', ?, '%') AND deleted <> 1;
            """;

    private static final String SELECT_MULTIPLE_BY_USER_ID = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint
            FROM guitars
            WHERE id_user LIKE CONCAT('%', ?, '%') AND deleted <> 1
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_USER_ID = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE id_user LIKE CONCAT('%', ?, '%') AND deleted <> 1;
            """;

    private static final String SELECT_MULTIPLE_BY_COLOR = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint
            FROM guitars
            WHERE color LIKE CONCAT('%', ?, '%') AND deleted <> 1
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_COLOR = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE color LIKE CONCAT('%', ?, '%') AND deleted <> 1;
            """;

    private static final String SELECT_MULTIPLE_BY_NECK_JOINT = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint
            FROM guitars
            WHERE neck_joint LIKE ? AND deleted <> 1
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_NECK_JOINT = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE neck_joint LIKE ? AND deleted <> 1;
            """;

    public static GuitarDao getInstance() {
        if (instance == null) {
            instance = new GuitarDaoImpl();
        }
        return instance;
    }

    @Override
    public long insert(Guitar entity) throws DaoException {
        return executeInsert(
                INSERT,
                entity.getName(),
                entity.getPicturePath(),
                entity.getBodyId(),
                entity.getNeckId(),
                entity.getPickupId(),
                entity.getUserId(),
                entity.getColor(),
                entity.getNeckJoint().toString()
        );
    }

    @Override
    public void update(Guitar entity) throws DaoException {
        executeUpdateOrDelete(
                UPDATE,
                entity.getName(),
                entity.getPicturePath(),
                entity.getBodyId(),
                entity.getNeckId(),
                entity.getPickupId(),
                entity.getUserId(),
                entity.getColor(),
                entity.getNeckJoint().toString(),
                entity.getEntityId()
        );
    }

    @Override
    public void delete(long id) throws DaoException {
        executeUpdateOrDelete(DELETE, id);
    }

    @Override
    public Optional<Guitar> selectById(long id) throws DaoException {
        return executeSelect(SELECT_BY_ID, id);
    }

    @Override
    public List<Guitar> selectById(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_ID, keyword, offset, length);
    }

    @Override
    public long selectCountById(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_ID, keyword);
    }

    @Override
    public List<Guitar> selectAll(int offset, int length) throws DaoException {
        return executeSelectMultiple(SELECT_ALL, offset, length);
    }

    @Override
    public long selectCountAll() throws DaoException {
        return executeSelectCount(SELECT_COUNT_ALL);
    }

    @Override
    public List<Guitar> selectByName(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_NAME, keyword, offset, length);
    }

    @Override
    public long selectCountByName(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_NAME, keyword);
    }

    @Override
    public List<Guitar> selectByBodyId(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_BODY_ID, keyword, offset, length);
    }

    @Override
    public long selectCountByBodyId(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_BODY_ID, keyword);
    }

    @Override
    public List<Guitar> selectByNeckId(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_NECK_ID, keyword, offset, length);
    }

    @Override
    public long selectCountByNeckId(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_NECK_ID, keyword);
    }

    @Override
    public List<Guitar> selectByPickupId(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_PICKUP_ID, keyword, offset, length);
    }

    @Override
    public long selectCountByPickupId(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_PICKUP_ID, keyword);
    }

    @Override
    public List<Guitar> selectByUserId(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_USER_ID, keyword, offset, length);
    }

    @Override
    public long selectCountByUserId(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_USER_ID, keyword);
    }

    @Override
    public List<Guitar> selectByColor(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_COLOR, keyword, offset, length);
    }

    @Override
    public long selectCountByColor(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_COLOR, keyword);
    }

    @Override
    public List<Guitar> selectByNeckJoint(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_NECK_JOINT, keyword, offset, length);
    }

    @Override
    public long selectCountByNeckJoint(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_NECK_JOINT, keyword);
    }
}
