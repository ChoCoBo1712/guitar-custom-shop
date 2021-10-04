package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.criteria.WoodFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

/**
 * {@code WoodService} interface provides functionality that allows to manipulate {@link Wood}
 * entities without relying to exact database.
 * @author Evgeniy Sokolchik
 */
public interface WoodService {

    /**
     * Find {@link Wood} entity by its unique id.
     *
     * @param id unique id of wood.
     * @return {@code Wood} entity wrapped with {@link Optional}.
     * @throws ServiceException if an error occurred executing the method.
     */
    Optional<Wood> findById(long id) throws ServiceException;

    /**
     * Insert values to create a new {@link Wood} entity in the DB.
     *
     * @param name {@code Wood} name.
     * @return {@code long} value that represents id of created entity.
     * @throws ServiceException if an error occurred executing the method.
     */
    long insert(String name) throws ServiceException;

    /**
     * Update a {@link Wood} entity in the DB.
     *
     * @param wood the {@code Wood} entity.
     * @throws ServiceException if an error occurred executing the method.
     */
    void update(Wood wood) throws ServiceException;

    /**
     * Delete a {@link Wood} entity with specified id from the data store.
     *
     * @param id unique id of the {@code Wood} entity to delete.
     * @throws ServiceException if an error occurred executing the method.
     */
    void delete(long id) throws ServiceException;

    /**
     * Find {@link Wood} entities that satisfy certain criteria.
     *
     * @param start lower bound index from which the result collection will start.
     * @param length of the result collection.
     * @param criteria instance of {@link WoodFilterCriteria} that specifies a criteria for filtering.
     * @param keyword stored entities will be filtered by.
     * @return {@link Pair} of {@code long} value and {@link List} of {@code Wood} entities
     * that represent count and collection of all found entities satisfied filter criteria and filter value
     * @throws ServiceException if an error occurred executing the method.
     */
    Pair<Long, List<Wood>> filter(int start, int length, WoodFilterCriteria criteria, String keyword)
            throws ServiceException;
}
