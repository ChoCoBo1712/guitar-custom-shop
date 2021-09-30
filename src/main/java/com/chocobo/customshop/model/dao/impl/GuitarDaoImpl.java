package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.dao.GuitarDao;
import com.chocobo.customshop.model.entity.Guitar;

import java.util.List;
import java.util.Optional;

public class GuitarDaoImpl implements GuitarDao {

    private static GuitarDao instance;

    private static final String INSERT = """
            INSERT INTO guitars(name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

    private static final String UPDATE = """
            UPDATE guitars
            SET name = ?, picture_path = ?, id_body = ?, id_neck = ?, id_pickup = ?, id_user = ?, color = ?, neck_joint = ?, order_status=?
            WHERE guitar_id = ?;
            """;

    private static final String DELETE = """
            UPDATE guitars
            SET deleted = 1
            WHERE guitar_id = ?;
            """;

    private static final String SELECT_BY_ID = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
            FROM guitars
            WHERE guitar_id = ?;
            """;

    private static final String SELECT_MULTIPLE_BY_ID = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
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
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
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
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
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
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
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
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
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
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
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
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
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
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
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
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
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

    private static final String SELECT_MULTIPLE_BY_ORDER_STATUS = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
            FROM guitars
            WHERE order_status LIKE ? AND deleted <> 1
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_ORDER_STATUS = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE order_status LIKE ? AND deleted <> 1;
            """;

    private static final String SELECT_MULTIPLE_BY_NAME_FOR_USER = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
            FROM guitars
            WHERE name LIKE CONCAT('%', ?, '%') AND id_user = ? AND deleted <> 1
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_NAME_FOR_USER = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE name LIKE CONCAT('%', ?, '%') AND id_user = ? AND deleted <> 1;
            """;

    private static final String SELECT_ALL_FOR_ACTIVE_ORDER = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
            FROM guitars
            WHERE deleted <> 1 AND order_status <> 'COMPLETED'
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_ALL_FOR_ACTIVE_ORDER = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE deleted <> 1 AND order_status <> 'COMPLETED';
            """;

    private static final String SELECT_MULTIPLE_BY_NAME_FOR_ACTIVE_ORDER = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
            FROM guitars
            WHERE name LIKE CONCAT('%', ?, '%') AND deleted <> 1 AND order_status <> 'COMPLETED'
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_NAME_FOR_ACTIVE_ORDER = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE name LIKE CONCAT('%', ?, '%') AND deleted <> 1 AND order_status <> 'COMPLETED';
            """;

    private static final String SELECT_MULTIPLE_BY_BODY_ID_FOR_ACTIVE_ORDER = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
            FROM guitars
            WHERE id_body LIKE CONCAT('%', ?, '%') AND deleted <> 1 AND order_status <> 'COMPLETED'
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_BODY_ID_FOR_ACTIVE_ORDER = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE id_body LIKE CONCAT('%', ?, '%') AND deleted <> 1 AND order_status <> 'COMPLETED';
            """;

    private static final String SELECT_MULTIPLE_BY_NECK_ID_FOR_ACTIVE_ORDER = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
            FROM guitars
            WHERE id_neck LIKE CONCAT('%', ?, '%') AND deleted <> 1 AND order_status <> 'COMPLETED'
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_NECK_ID_FOR_ACTIVE_ORDER = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE id_neck LIKE CONCAT('%', ?, '%') AND deleted <> 1 AND order_status <> 'COMPLETED';
            """;

    private static final String SELECT_MULTIPLE_BY_PICKUP_ID_FOR_ACTIVE_ORDER = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
            FROM guitars
            WHERE id_pickup LIKE CONCAT('%', ?, '%') AND deleted <> 1 AND order_status <> 'COMPLETED'
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_PICKUP_ID_FOR_ACTIVE_ORDER = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE id_pickup LIKE CONCAT('%', ?, '%') AND deleted <> 1 AND order_status <> 'COMPLETED';
            """;

    private static final String SELECT_MULTIPLE_BY_USER_ID_FOR_ACTIVE_ORDER = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
            FROM guitars
            WHERE id_user LIKE CONCAT('%', ?, '%') AND deleted <> 1 AND order_status <> 'COMPLETED'
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_USER_ID_FOR_ACTIVE_ORDER = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE id_user LIKE CONCAT('%', ?, '%') AND deleted <> 1 AND order_status <> 'COMPLETED';
            """;

    private static final String SELECT_MULTIPLE_BY_COLOR_FOR_ACTIVE_ORDER = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
            FROM guitars
            WHERE color LIKE CONCAT('%', ?, '%') AND deleted <> 1 AND order_status <> 'COMPLETED'
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_COLOR_FOR_ACTIVE_ORDER = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE color LIKE CONCAT('%', ?, '%') AND deleted <> 1 AND order_status <> 'COMPLETED';
            """;

    private static final String SELECT_MULTIPLE_BY_NECK_JOINT_FOR_ACTIVE_ORDER = """
            SELECT guitar_id, name, picture_path, id_body, id_neck, id_pickup, id_user, color, neck_joint, order_status
            FROM guitars
            WHERE neck_joint LIKE ? AND deleted <> 1 AND order_status <> 'COMPLETED'
            ORDER BY guitar_id
            LIMIT ?, ?;
            """;

    private static final String SELECT_COUNT_BY_NECK_JOINT_FOR_ACTIVE_ORDER = """
            SELECT COUNT(guitar_id)
            FROM guitars
            WHERE neck_joint LIKE ? AND deleted <> 1 AND order_status <> 'COMPLETED';
            """;

    public static GuitarDao getInstance() {
        if (instance == null) {
            instance = new GuitarDaoImpl();
        }
        return instance;
    }

    @Override
    public long insert(Guitar entity) throws DaoException {
        return QueryExecutor.createExecutor().executeInsert(
                INSERT,
                entity.getName(),
                entity.getPicturePath(),
                entity.getBodyId(),
                entity.getNeckId(),
                entity.getPickupId(),
                entity.getUserId(),
                entity.getColor(),
                entity.getNeckJoint().toString(),
                entity.getOrderStatus().toString()
        );
    }

    @Override
    public void update(Guitar entity) throws DaoException {
        QueryExecutor.createExecutor().executeUpdateOrDelete(
                UPDATE,
                entity.getName(),
                entity.getPicturePath(),
                entity.getBodyId(),
                entity.getNeckId(),
                entity.getPickupId(),
                entity.getUserId(),
                entity.getColor(),
                entity.getNeckJoint().toString(),
                entity.getOrderStatus().toString(),
                entity.getEntityId()
        );
    }

    @Override
    public void delete(long id) throws DaoException {
        QueryExecutor.createExecutor().executeUpdateOrDelete(DELETE, id);
    }

    @Override
    public Optional<Guitar> selectById(long id) throws DaoException {
        return QueryExecutor.createExecutor().executeSelect(SELECT_BY_ID, this, id);
    }

    @Override
    public List<Guitar> selectById(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_ID, this, keyword, offset, length);
    }

    @Override
    public long selectCountById(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_ID, keyword);
    }

    @Override
    public List<Guitar> selectAll(int offset, int length) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectMultiple(SELECT_ALL, this, offset, length);
    }

    @Override
    public long selectCountAll() throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_ALL);
    }

    @Override
    public List<Guitar> selectByName(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_NAME, this, keyword, offset, length);
    }

    @Override
    public long selectCountByName(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_NAME, keyword);
    }

    @Override
    public List<Guitar> selectByBodyId(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_BODY_ID, this, keyword, offset, length);
    }

    @Override
    public long selectCountByBodyId(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_BODY_ID, keyword);
    }

    @Override
    public List<Guitar> selectByNeckId(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_NECK_ID, this, keyword, offset, length);
    }

    @Override
    public long selectCountByNeckId(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_NECK_ID, keyword);
    }

    @Override
    public List<Guitar> selectByPickupId(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_PICKUP_ID, this, keyword, offset, length);
    }

    @Override
    public long selectCountByPickupId(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_PICKUP_ID, keyword);
    }

    @Override
    public List<Guitar> selectByUserId(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_USER_ID, this, keyword, offset, length);
    }

    @Override
    public long selectCountByUserId(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_USER_ID, keyword);
    }

    @Override
    public List<Guitar> selectByColor(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_COLOR, this, keyword, offset, length);
    }

    @Override
    public long selectCountByColor(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_COLOR, keyword);
    }

    @Override
    public List<Guitar> selectByNeckJoint(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_NECK_JOINT, this, keyword, offset, length);
    }

    @Override
    public long selectCountByNeckJoint(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_NECK_JOINT, keyword);
    }

    @Override
    public List<Guitar> selectByOrderStatus(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_ORDER_STATUS, this, keyword, offset, length);
    }

    @Override
    public long selectCountByOrderStatus(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_ORDER_STATUS, keyword);
    }

    @Override
    public List<Guitar> selectByNameForUser(int offset, int length, String keyword, String userId) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_NAME_FOR_USER, this, keyword, userId, offset, length);
    }

    @Override
    public long selectCountByNameForUser(String keyword, String userId) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_NAME_FOR_USER, keyword, userId);
    }

    @Override
    public List<Guitar> selectAllForActiveOrder(int offset, int length) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_ALL_FOR_ACTIVE_ORDER, this, offset, length);
    }

    @Override
    public long selectCountAllForActiveOrder() throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_ALL_FOR_ACTIVE_ORDER);
    }

    @Override
    public List<Guitar> selectByNameForActiveOrder(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_NAME_FOR_ACTIVE_ORDER, this, keyword, offset, length);
    }

    @Override
    public long selectCountByNameForActiveOrder(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_NAME_FOR_ACTIVE_ORDER, keyword);
    }

    @Override
    public List<Guitar> selectByBodyIdForActiveOrder(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_BODY_ID_FOR_ACTIVE_ORDER, this, keyword, offset, length);
    }

    @Override
    public long selectCountByBodyIdForActiveOrder(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_BODY_ID_FOR_ACTIVE_ORDER, keyword);
    }

    @Override
    public List<Guitar> selectByNeckIdForActiveOrder(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_NECK_ID_FOR_ACTIVE_ORDER, this, keyword, offset, length);
    }

    @Override
    public long selectCountByNeckIdForActiveOrder(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_NECK_ID_FOR_ACTIVE_ORDER, keyword);
    }

    @Override
    public List<Guitar> selectByPickupIdForActiveOrder(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_PICKUP_ID_FOR_ACTIVE_ORDER, this, keyword, offset, length);
    }

    @Override
    public long selectCountByPickupIdForActiveOrder(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_PICKUP_ID_FOR_ACTIVE_ORDER, keyword);
    }

    @Override
    public List<Guitar> selectByUserIdForActiveOrder(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_USER_ID_FOR_ACTIVE_ORDER, this, keyword, offset, length);
    }

    @Override
    public long selectCountByUserIdForActiveOrder(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_USER_ID_FOR_ACTIVE_ORDER, keyword);
    }

    @Override
    public List<Guitar> selectByColorForActiveOrder(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_COLOR_FOR_ACTIVE_ORDER, this, keyword, offset, length);
    }

    @Override
    public long selectCountByColorForActiveOrder(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_COLOR_FOR_ACTIVE_ORDER, keyword);
    }

    @Override
    public List<Guitar> selectByNeckJointForActiveOrder(int offset, int length, String keyword) throws DaoException {
        return QueryExecutor.createExecutor()
                .executeSelectMultiple(SELECT_MULTIPLE_BY_NECK_JOINT_FOR_ACTIVE_ORDER, this, keyword, offset, length);
    }

    @Override
    public long selectCountByNeckJointForActiveOrder(String keyword) throws DaoException {
        return QueryExecutor.createExecutor().executeSelectCount(SELECT_COUNT_BY_NECK_JOINT_FOR_ACTIVE_ORDER, keyword);
    }
}
