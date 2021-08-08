package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends BaseEntity> {

    long insert(T entity) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(long id) throws DaoException;

    Optional<T> selectById(long id) throws DaoException;

    List<T> selectAll(int offset) throws DaoException;
}