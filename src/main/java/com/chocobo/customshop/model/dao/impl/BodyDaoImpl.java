package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.dao.BodyDao;
import com.chocobo.customshop.model.dao.UserDao;
import com.chocobo.customshop.model.entity.Body;

import java.util.List;
import java.util.Optional;

public class BodyDaoImpl implements BodyDao {

    private static BodyDao instance;

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
        return Optional.empty();
    }

    @Override
    public List<Body> selectById(int offset, int length, String keyword) throws DaoException {
        return null;
    }

    @Override
    public List<Body> selectAll(int offset, int length) throws DaoException {
        return null;
    }
}
