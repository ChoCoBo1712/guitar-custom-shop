package com.chocobo.customshop.model.service.criteria;

/**
 * {@code UserFilterCriteria} enum is used to choose a filter strategy
 * in {@link com.chocobo.customshop.model.service.UserService#filter} method.
 * @author Evgeniy Sokolchik
 */
public enum UserFilterCriteria {
    NONE,
    ID,
    EMAIL,
    LOGIN,
    ROLE,
    STATUS
}
