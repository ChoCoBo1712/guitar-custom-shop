package com.chocobo.customshop.model.dao;

import com.chocobo.customshop.exception.DaoException;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.Wood;

import java.util.List;
import java.util.Optional;

public interface WoodDao extends BaseDao<Wood> {

    Optional<Wood> selectByName(String name) throws DaoException;

    List<Wood> selectByName(int offset, int length, String keyword) throws DaoException;
}
