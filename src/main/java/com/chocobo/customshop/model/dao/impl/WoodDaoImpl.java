package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.exception.DatabaseConnectionException;
import com.chocobo.customshop.model.dao.UserDao;
import com.chocobo.customshop.model.dao.WoodDao;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static WoodDao getInstance() {
        if (instance == null) {
            instance = new WoodDaoImpl();
        }
        return instance;
    }

    @Override
    public long insert(Wood entity) throws DaoException {
        return 0;
    }

    @Override
    public void update(Wood entity) throws DaoException {

    }

    @Override
    public void delete(long id) throws DaoException {

    }

    @Override
    public Optional<Wood> selectById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Wood> selectAll(int offset, int length) throws DaoException {
        Connection connection = null;
        DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();
        List<Wood> woodList = new ArrayList<>();
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            statement.setInt(1, offset);
            statement.setInt(2, length);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Wood wood = (Wood) Wood.builder()
                        .setName(resultSet.getString(WOOD_NAME))
                        .setEntityId(resultSet.getLong(WOOD_ID))
                        .build();
                woodList.add(wood);
            }
            statement.close();
            return woodList;
        } catch (DatabaseConnectionException | SQLException e) {
            throw new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
