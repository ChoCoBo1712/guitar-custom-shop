package com.chocobo.customshop.model.dao.impl;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.dao.PickupDao;
import com.chocobo.customshop.model.entity.Pickup;

import java.util.List;
import java.util.Optional;

public class PickupDaoImpl implements PickupDao {

    private static PickupDao instance;

    public static PickupDao getInstance() {
        if (instance == null) {
            instance = new PickupDaoImpl();
        }
        return instance;
    }

    @Override
    public long insert(Pickup entity) throws DaoException {
        return 0;
    }

    @Override
    public void update(Pickup entity) throws DaoException {

    }

    @Override
    public void delete(long id) throws DaoException {

    }

    @Override
    public Optional<Pickup> selectById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Pickup> selectById(int offset, int length, String keyword) throws DaoException {
        return null;
    }

    @Override
    public List<Pickup> selectAll(int offset, int length) throws DaoException {
        return null;
    }
}
