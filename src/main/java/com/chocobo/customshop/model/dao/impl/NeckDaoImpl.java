package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.dao.NeckDao;
import com.chocobo.customshop.model.entity.Neck;

import java.util.List;
import java.util.Optional;

public class NeckDaoImpl implements NeckDao {

    private static NeckDao instance;

    private static final String SELECT_ALL =
            "SELECT neck_id, name, id_neck_wood, id_fretboard_wood " +
            "FROM necks " +
            "WHERE deleted <> 1 " +
            "ORDER BY neck_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_COUNT_ALL =
            "SELECT COUNT(neck_id) " +
            "FROM necks " +
            "WHERE deleted <> 1;";

    private static final String SELECT_BY_ID =
            "SELECT neck_id, name, id_neck_wood, id_fretboard_wood " +
            "FROM necks " +
            "WHERE neck_id = ?;";

    private static final String SELECT_MULTIPLE_BY_ID =
            "SELECT neck_id, name, id_neck_wood, id_fretboard_wood " +
            "FROM necks " +
            "WHERE neck_id LIKE CONCAT('%', ?, '%') AND deleted <> 1 " +
            "ORDER BY neck_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_COUNT_BY_ID =
            "SELECT COUNT(neck_id) " +
            "FROM necks " +
            "WHERE neck_id LIKE CONCAT('%', ?, '%') AND deleted <> 1;";

    private static final String SELECT_MULTIPLE_BY_WOOD_ID =
            "SELECT neck_id, name, id_neck_wood, id_fretboard_wood " +
            "FROM necks " +
            "WHERE id_neck_wood LIKE CONCAT('%', ?, '%') AND deleted <> 1 " +
            "ORDER BY neck_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_COUNT_BY_WOOD_ID =
            "SELECT COUNT(neck_id) " +
            "FROM necks " +
            "WHERE id_neck_wood LIKE CONCAT('%', ?, '%') AND deleted <> 1;";

    private static final String SELECT_MULTIPLE_BY_FRETBOARD_WOOD_ID =
            "SELECT neck_id, name, id_neck_wood, id_fretboard_wood " +
            "FROM necks " +
            "WHERE id_fretboard_wood LIKE CONCAT('%', ?, '%') AND deleted <> 1 " +
            "ORDER BY neck_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_COUNT_BY_FRETBOARD_WOOD_ID =
            "SELECT COUNT(neck_id) " +
            "FROM necks " +
            "WHERE id_fretboard_wood LIKE CONCAT('%', ?, '%') AND deleted <> 1;";

    private static final String SELECT_MULTIPLE_BY_NAME =
            "SELECT neck_id, name, id_neck_wood, id_fretboard_wood " +
            "FROM necks " +
            "WHERE name LIKE CONCAT('%', ?, '%') AND deleted <> 1 " +
            "ORDER BY neck_id " +
            "LIMIT ?, ?;";

    private static final String SELECT_COUNT_BY_NAME =
            "SELECT COUNT(neck_id) " +
            "FROM necks " +
            "WHERE name LIKE CONCAT('%', ?, '%') AND deleted <> 1;";

    private static final String INSERT =
            "INSERT INTO necks(name, id_neck_wood, id_fretboard_wood) " +
            "VALUES(?, ?, ?, ?);";

    private static final String UPDATE =
            "UPDATE necks " +
            "SET name = ?, id_neck_wood = ?, id_fretboard_wood = ? " +
            "WHERE neck_id = ?;";

    private static final String DELETE =
            "UPDATE necks " +
            "SET deleted = 1 " +
            "WHERE neck_id = ?;";

    public static NeckDao getInstance() {
        if (instance == null) {
            instance = new NeckDaoImpl();
        }
        return instance;
    }

    @Override
    public long insert(Neck entity) throws DaoException {
        return executeInsert(
                INSERT,
                entity.getName(),
                entity.getWoodId(),
                entity.getFretboardWoodId()
        );
    }

    @Override
    public void update(Neck entity) throws DaoException {
        executeUpdateOrDelete(
                UPDATE,
                entity.getName(),
                entity.getWoodId(),
                entity.getFretboardWoodId(),
                entity.getEntityId()
        );
    }

    @Override
    public void delete(long id) throws DaoException {
        executeUpdateOrDelete(DELETE, id);
    }

    @Override
    public Optional<Neck> selectById(long id) throws DaoException {
        return executeSelect(SELECT_BY_ID, id);
    }

    @Override
    public List<Neck> selectById(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_ID, keyword, offset, length);
    }

    @Override
    public long selectCountById(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_ID, keyword);
    }

    @Override
    public List<Neck> selectAll(int offset, int length) throws DaoException {
        return executeSelectMultiple(SELECT_ALL, offset, length);
    }

    @Override
    public long selectCountAll() throws DaoException {
        return executeSelectCount(SELECT_COUNT_ALL);
    }

    @Override
    public List<Neck> selectByWoodId(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_WOOD_ID, keyword, offset, length);
    }

    @Override
    public long selectCountByWoodId(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_WOOD_ID, keyword);
    }

    @Override
    public List<Neck> selectByFretboardWoodId(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_FRETBOARD_WOOD_ID, keyword, offset, length);
    }

    @Override
    public long selectCountByFretboardWoodId(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_FRETBOARD_WOOD_ID, keyword);
    }

    @Override
    public List<Neck> selectByName(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_NAME, keyword, offset, length);
    }

    @Override
    public long selectCountByName(String keyword) throws DaoException {
        return executeSelectCount(SELECT_COUNT_BY_NAME, keyword);
    }
}
