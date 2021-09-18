package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.DatabaseConnectionException;
import com.chocobo.customshop.model.dao.UserDao;
import com.chocobo.customshop.model.dao.WoodDao;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.pool.DatabaseConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.chocobo.customshop.model.dao.TableColumn.*;

public class WoodDaoImpl implements WoodDao {

    private static WoodDao instance;

    private static final String SELECT_ALL =
            "SELECT wood_id, name " +
            "FROM woods " +
            "WHERE deleted <> 1 " +
            "LIMIT ?, ?;";

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

    private static final String SELECT_BY_NAME =
            "SELECT wood_id, name " +
            "FROM woods " +
            "WHERE name = ?;";

    private static final String SELECT_MULTIPLE_BY_NAME =
            "SELECT wood_id, name " +
            "FROM woods " +
            "WHERE name LIKE CONCAT('%', ?, '%') AND deleted <> 1 " +
            "ORDER BY wood_id " +
            "LIMIT ?, ?;";

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

//    @Override
//    public long insert(Wood entity) throws DaoException {
//        Connection connection = null;
//        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
//        try {
//            connection = pool.getConnection();
//            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
//            statement.setString(1, entity.getName());
//            statement.execute();
//
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            long generatedKey = generatedKeys.next() ? generatedKeys.getLong(1) : 0;
//            statement.close();
//            return generatedKey;
//        } catch (DatabaseConnectionException | SQLException e) {
//            throw new DaoException(e);
//        } finally {
//            pool.releaseConnection(connection);
//        }
//    }
//
//    @Override
//    public void update(Wood entity) throws DaoException {
//        Connection connection = null;
//        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
//        try {
//            connection = pool.getConnection();
//            PreparedStatement statement = connection.prepareStatement(UPDATE);
//            statement.setString(1, entity.getName());
//            statement.setString(2, String.valueOf(entity.getEntityId()));
//            statement.execute();
//
//            statement.close();
//        } catch (DatabaseConnectionException | SQLException e) {
//            throw new DaoException(e);
//        } finally {
//            pool.releaseConnection(connection);
//        }
//    }
//
//    @Override
//    public void delete(long id) throws DaoException {
//        Connection connection = null;
//        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
//        try {
//            connection = pool.getConnection();
//            PreparedStatement statement = connection.prepareStatement(DELETE);
//            statement.setString(1, String.valueOf(id));
//            statement.execute();
//
//            statement.close();
//        } catch (DatabaseConnectionException | SQLException e) {
//            throw new DaoException(e);
//        } finally {
//            pool.releaseConnection(connection);
//        }
//    }
//
//    @Override
//    public Optional<Wood> selectById(long id) throws DaoException {
//        Connection connection = null;
//        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
//        Wood wood = null;
//        try {
//            connection = pool.getConnection();
//            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
//            statement.setString(1, String.valueOf(id));
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                wood = (Wood) Wood.builder()
//                        .setName(resultSet.getString(WOOD_NAME))
//                        .setEntityId(resultSet.getLong(WOOD_ID))
//                        .build();
//            }
//            statement.close();
//            return Optional.ofNullable(wood);
//        } catch (DatabaseConnectionException | SQLException e) {
//            throw new DaoException(e);
//        } finally {
//            pool.releaseConnection(connection);
//        }
//    }
//
//    @Override
//    public List<Wood> selectById(int offset, int length, String keyword) throws DaoException {
//        Connection connection = null;
//        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
//        List<Wood> woodList = new ArrayList<>();
//        try {
//            connection = pool.getConnection();
//            PreparedStatement statement = connection.prepareStatement(SELECT_MULTIPLE_BY_ID);
//            statement.setString(1, keyword);
//            statement.setInt(2, offset);
//            statement.setInt(3, length);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                Wood wood = (Wood) Wood.builder()
//                        .setName(resultSet.getString(WOOD_NAME))
//                        .setEntityId(resultSet.getLong(WOOD_ID))
//                        .build();
//                woodList.add(wood);
//            }
//            statement.close();
//            return woodList;
//        } catch (DatabaseConnectionException | SQLException e) {
//            throw new DaoException(e);
//        } finally {
//            pool.releaseConnection(connection);
//        }
//    }
//
//    @Override
//    public List<Wood> selectAll(int offset, int length) throws DaoException {
//        Connection connection = null;
//        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
//        List<Wood> woodList = new ArrayList<>();
//        try {
//            connection = pool.getConnection();
//            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
//            statement.setInt(1, offset);
//            statement.setInt(2, length);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                Wood wood = (Wood) Wood.builder()
//                        .setName(resultSet.getString(WOOD_NAME))
//                        .setEntityId(resultSet.getLong(WOOD_ID))
//                        .build();
//                woodList.add(wood);
//            }
//            statement.close();
//            return woodList;
//        } catch (DatabaseConnectionException | SQLException e) {
//            throw new DaoException(e);
//        } finally {
//            pool.releaseConnection(connection);
//        }
//    }
//
//    @Override
//    public Optional<Wood> selectByName(String name) throws DaoException {
//        Connection connection = null;
//        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
//        Wood wood = null;
//        try {
//            connection = pool.getConnection();
//            PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME);
//            statement.setString(1, name);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                wood = (Wood) Wood.builder()
//                        .setName(resultSet.getString(WOOD_NAME))
//                        .setEntityId(resultSet.getLong(WOOD_ID))
//                        .build();
//            }
//            statement.close();
//            return Optional.ofNullable(wood);
//        } catch (DatabaseConnectionException | SQLException e) {
//            throw new DaoException(e);
//        } finally {
//            pool.releaseConnection(connection);
//        }
//    }
//
//    @Override
//    public List<Wood> selectByName(int offset, int length, String keyword) throws DaoException {
//        Connection connection = null;
//        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
//        List<Wood> woodList = new ArrayList<>();
//        try {
//            connection = pool.getConnection();
//            PreparedStatement statement = connection.prepareStatement(SELECT_MULTIPLE_BY_NAME);
//            statement.setString(1, keyword);
//            statement.setInt(2, offset);
//            statement.setInt(3, length);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                Wood wood = (Wood) Wood.builder()
//                        .setName(resultSet.getString(WOOD_NAME))
//                        .setEntityId(resultSet.getLong(WOOD_ID))
//                        .build();
//                woodList.add(wood);
//            }
//            statement.close();
//            return woodList;
//        } catch (DatabaseConnectionException | SQLException e) {
//            throw new DaoException(e);
//        } finally {
//            pool.releaseConnection(connection);
//        }
//    }

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
    public List<Wood> selectAll(int offset, int length) throws DaoException {
        return executeSelectMultiple(SELECT_ALL, offset, length);
    }

    @Override
    public Optional<Wood> selectByName(String name) throws DaoException {
        return executeSelect(SELECT_BY_NAME, name);
    }

    @Override
    public List<Wood> selectByName(int offset, int length, String keyword) throws DaoException {
        return executeSelectMultiple(SELECT_MULTIPLE_BY_NAME, keyword, offset, length);
    }
}
