package com.chocobo.customshop.model.validator;

/**
 * {@code Validator} is a functional interface implementing instances of which
 * are user to validate an object of specified type by specified rules.
 * @author Evgeniy Sokolchik
 */
@FunctionalInterface
public interface Validator<T> {

    String SERVICE_EXCEPTION = "serviceException";

    /**
     * Method that specifies rules of validating objects.
     *
     * @param t is an object instance to validate
     * @return boolean is a result of validation.
     */
    boolean validate(T t);
}
