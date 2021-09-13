package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends AbstractEntity> {

    long insert(T entity) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(long id) throws DaoException;

    Optional<T> selectById(long id) throws DaoException;

    List<T> selectById(int offset, int length, String keyword) throws DaoException;

    List<T> selectAll(int offset, int length) throws DaoException;
}