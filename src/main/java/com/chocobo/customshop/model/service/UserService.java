package com.chocobo.customshop.model.service;

import com.chocobo.customshop.exception.ServiceException;
import com.chocobo.customshop.model.entity.Body;
import com.chocobo.customshop.model.entity.User;
import com.chocobo.customshop.model.entity.User.UserRole;
import com.chocobo.customshop.model.entity.User.UserStatus;
import com.chocobo.customshop.model.entity.Wood;
import com.chocobo.customshop.model.service.criteria.UserFilterCriteria;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

/**
 * {@code UserService} interface provides functionality that allows to manipulate {@link User}
 * entities without relying to exact database.
 * @author Evgeniy Sokolchik
 */
public interface UserService {

    /**
     * Check if {@link User} with specified email already exists in DB.
     *
     * @param email to check.
     * @return {@code boolean} that specifies is email unique.
     * @throws ServiceException if an error occurred executing the method.
     */
    boolean isEmailUnique(String email) throws ServiceException;

    /**
     * Check if {@link User} with specified login already exists in DB.
     *
     * @param login to check.
     * @return {@code boolean} that specifies is email unique.
     * @throws ServiceException if an error occurred executing the method.
     */
    boolean isLoginUnique(String login) throws ServiceException;

    /**
     * Insert values to create a new {@link User} entity in the DB.
     *
     * @param email {@code User} email.
     * @param login {@code User} login.
     * @param password {@code User} password.
     * @param role {@code User} role.
     * @param status {@code User} status.
     * @return {@code long} value that represents id of created entity.
     * @throws ServiceException if an error occurred executing the method.
     */
    long register(String email, String login, String password, UserRole role, UserStatus status)
            throws ServiceException;

    /**
     * Find {@link User} entity in the DB.
     *
     * @param login {@code User} login.
     * @param password {@code User} woodId.
     * @return {@code User} entity wrapped with {@link Optional}.
     * @throws ServiceException if an error occurred executing the method.
     */
    Optional<User> login(String login, String password) throws ServiceException;

    /**
     * Find {@link User} entity by its unique id.
     *
     * @param id unique id of user.
     * @return {@code User} entity wrapped with {@link Optional}.
     * @throws ServiceException if an error occurred executing the method.
     */
    Optional<User> findById(long id) throws ServiceException;

    /**
     * Find {@link User} entity by its email.
     *
     * @param email of user.
     * @return {@code User} entity wrapped with {@link Optional}.
     * @throws ServiceException if an error occurred executing the method.
     */
    Optional<User> findByEmail(String email) throws ServiceException;

    /**
     * Update a {@link User} entity in the DB.
     *
     * @param user the {@code User} entity.
     * @throws ServiceException if an error occurred executing the method.
     */
    void update(User user) throws ServiceException;

    /**
     * Update a {@link User} entity in the DB with specified new password to hash.
     *
     * @param user the {@code User} entity.
     * @param password a password to hash and update entity by.
     * @throws ServiceException if an error occurred executing the method.
     */
    void updateWithPassword(User user, String password) throws ServiceException;

    /**
     * Delete a {@link User} entity with specified id from the data store.
     *
     * @param id unique id of the {@code User} entity to delete.
     * @throws ServiceException if an error occurred executing the method.
     */
    void delete(long id) throws ServiceException;

    /**
     * Find {@link User} entities that satisfy certain criteria.
     *
     * @param start lower bound index from which the result collection will start.
     * @param length of the result collection.
     * @param criteria instance of {@link UserFilterCriteria that specifies a criteria for filtering.
     * @param keyword stored entities will be filtered by.
     * @return {@link Pair} of {@code long} value and {@link List} of {@code User} entities
     * that represent count and collection of all found entities satisfied filter criteria and filter value
     * @throws ServiceException if an error occurred executing the method.
     */
    Pair<Long, List<User>> filter(int start, int length, UserFilterCriteria criteria, String keyword)
            throws ServiceException;
}
