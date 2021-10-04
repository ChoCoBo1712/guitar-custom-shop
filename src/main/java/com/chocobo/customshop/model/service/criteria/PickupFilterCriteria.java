package com.chocobo.customshop.model.service.criteria;

/**
 * {@code PickupFilterCriteria} enum is used to choose a filter strategy
 * in {@link com.chocobo.customshop.model.service.PickupService#filter} method.
 * @author Evgeniy Sokolchik
 */
public enum PickupFilterCriteria {
    NONE,
    ID,
    NAME
}
