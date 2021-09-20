package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.dao.GuitarDao;
import com.chocobo.customshop.model.entity.Guitar;

import java.util.List;
import java.util.Optional;

public class GuitarDaoImpl implements GuitarDao {

    private static GuitarDao instance;

    public static GuitarDao getInstance() {
        if (instance == null) {
            instance = new GuitarDaoImpl();
        }
        return instance;
    }

    @Override
    public long insert(Guitar entity) throws DaoException {
        return 0;
    }

    @Override
    public void update(Guitar entity) throws DaoException {

    }

    @Override
    public void delete(long id) throws DaoException {

    }

    @Override
    public Optional<Guitar> selectById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Guitar> selectById(int offset, int length, String keyword) throws DaoException {
        return null;
    }

    @Override
    public long selectCountById(String keyword) throws DaoException {
        return 0;
    }

    @Override
    public List<Guitar> selectAll(int offset, int length) throws DaoException {
        return null;
    }

    @Override
    public long selectCountAll() throws DaoException {
        return 0;
    }
}
