package com.chocobo.customshop.model.service.criteria;

/**
 * {@code GuitarFilterCriteria} enum is used to choose a filter strategy
 * in {@link com.chocobo.customshop.model.service.GuitarService#filter} and
 * {@link com.chocobo.customshop.model.service.GuitarService#filterForActiveOrder} methods.
 * @author Evgeniy Sokolchik
 */
public enum GuitarFilterCriteria {
    NONE,
    ID,
    NAME,
    BODY_ID,
    NECK_ID,
    PICKUP_ID,
    USER_ID,
    COLOR,
    NECK_JOINT,
    ORDER_STATUS
}
