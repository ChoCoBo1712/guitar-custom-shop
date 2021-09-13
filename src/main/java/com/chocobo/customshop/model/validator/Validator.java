package com.chocobo.customshop.model.validator;

import org.apache.commons.lang3.tuple.Pair;

@FunctionalInterface
public interface Validator<T> {

    Pair<Boolean, String> validate(T t);
}
