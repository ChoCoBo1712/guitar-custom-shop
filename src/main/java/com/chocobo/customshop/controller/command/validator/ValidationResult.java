package com.chocobo.customshop.controller.command.validator;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private final boolean valid;
    private final List<String> errorList;

    public ValidationResult() {
        this.valid = true;
        errorList = new ArrayList<>();
    }

    public void addToErrorList(String error) {
        errorList.add(error);
    }

    public boolean isValid() {
        return valid;
    }

    public List<String> getErrorList() {
        return new ArrayList<>(errorList);
    }
}
