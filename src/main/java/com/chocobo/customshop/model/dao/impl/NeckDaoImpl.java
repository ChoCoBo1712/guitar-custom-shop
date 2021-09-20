package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.dao.NeckDao;
import com.chocobo.customshop.model.entity.Neck;

import java.util.List;
import java.util.Optional;

public class NeckDaoImpl implements NeckDao {

    private static NeckDao instance;

    public static NeckDao getInstance() {
        if (instance == null) {
            instance = new NeckDaoImpl();
        }
        return instance;
    }

    @Override
    public long insert(Neck entity) throws DaoException {
        return 0;
    }

    @Override
    public void update(Neck entity) throws DaoException {

    }

    @Override
    public void delete(long id) throws DaoException {

    }

    @Override
    public Optional<Neck> selectById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Neck> selectById(int offset, int length, String keyword) throws DaoException {
        return null;
    }

    @Override
    public long selectCountById(String keyword) throws DaoException {
        return 0;
    }

    @Override
    public List<Neck> selectAll(int offset, int length) throws DaoException {
        return null;
    }

    @Override
    public long selectCountAll() throws DaoException {
        return 0;
    }
}
