package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Pickup;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.criteria.PickupFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

/**
 * {@code PickupService} interface provides functionality that allows to manipulate {@link Pickup}
 * entities without relying to exact database.
 * @author Evgeniy Sokolchik
 */
public interface PickupService {

    /**
     * Find {@link Pickup} entity by its unique id.
     *
     * @param id unique id of pickup.
     * @return {@code Pickup} entity wrapped with {@link Optional}.
     * @throws ServiceException if an error occurred executing the method.
     */
    Optional<Pickup> findById(long id) throws ServiceException;

    /**
     * Insert values to create a new {@link Pickup} entity in the DB.
     *
     * @param name {@code Pickup} name.
     * @return {@code long} value that represents id of created entity.
     * @throws ServiceException if an error occurred executing the method.
     */
    long insert(String name) throws ServiceException;

    /**
     * Update a {@link Pickup} entity in the DB.
     *
     * @param pickup the {@code Pickup} entity.
     * @throws ServiceException if an error occurred executing the method.
     */
    void update(Pickup pickup) throws ServiceException;

    /**
     * Delete a {@link Pickup} entity with specified id from the data store.
     *
     * @param id unique id of the {@code Pickup} entity to delete.
     * @throws ServiceException if an error occurred executing the method.
     */
    void delete(long id) throws ServiceException;

    /**
     * Find {@link Pickup} entities that satisfy certain criteria.
     *
     * @param start lower bound index from which the result collection will start.
     * @param length of the result collection.
     * @param criteria instance of {@link PickupFilterCriteria} that specifies a criteria for filtering.
     * @param keyword stored entities will be filtered by.
     * @return {@link Pair} of {@code long} value and {@link List} of {@code Pickup} entities
     * that represent count and collection of all found entities satisfied filter criteria and filter value
     * @throws ServiceException if an error occurred executing the method.
     */
    Pair<Long, List<Pickup>> filter(int start, int length, PickupFilterCriteria criteria, String keyword)
            throws ServiceException;
}
