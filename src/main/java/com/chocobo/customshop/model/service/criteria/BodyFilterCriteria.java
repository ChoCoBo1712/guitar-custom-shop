package com.chocobo.customshop.model.service.criteria;

/**
 * {@code BodyFilterCriteria} enum is used to choose a filter strategy
 * in {@link com.chocobo.customshop.model.service.BodyService#filter} method.
 * @author Evgeniy Sokolchik
 */
public enum BodyFilterCriteria {
    NONE,
    ID,
    NAME,
    WOOD_ID,
    NAME_AND_WOOD
}
