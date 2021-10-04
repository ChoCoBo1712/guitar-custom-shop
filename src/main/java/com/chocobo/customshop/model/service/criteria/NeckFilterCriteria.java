package com.chocobo.customshop.model.service.criteria;

/**
 * {@code NeckFilterCriteria} enum is used to choose a filter strategy
 * in {@link com.chocobo.customshop.model.service.NeckService#filter} method.
 * @author Evgeniy Sokolchik
 */
public enum NeckFilterCriteria {
    NONE,
    ID,
    NAME,
    WOOD_ID,
    FRETBOARD_WOOD_ID,
    NAME_AND_WOOD
}
