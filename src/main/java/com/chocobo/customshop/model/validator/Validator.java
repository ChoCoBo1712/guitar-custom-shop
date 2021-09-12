package com.chocobo.customshop.model.validator;

import org.apache.commons.lang3.tuple.Pair;

@FunctionalInterface
public interface Validator {

    Pair<Boolean, String> validate(Object object);
}
