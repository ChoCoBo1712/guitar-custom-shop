package com.chocobo.customshop.model.service.criteria;

/**
 * {@code WoodFilterCriteria} enum is used to choose a filter strategy
 * in {@link com.chocobo.customshop.model.service.WoodService#filter} method.
 * @author Evgeniy Sokolchik
 */
public enum WoodFilterCriteria {
    NONE,
    ID,
    NAME
}
