package com.chocobo.customshop.model.validator;

public interface Validator<T> {

    String SERVICE_EXCEPTION = "serviceException";

    boolean validate(T t);
}
