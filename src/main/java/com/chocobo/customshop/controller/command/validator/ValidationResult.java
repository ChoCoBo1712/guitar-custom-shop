package com.chocobo.customshop.controller.command.validator;

public class ValidationResult {

    private static final String DEFAULT_POSITIVE_MSG = "Validation successful";

    private final boolean valid;
    private final String errorMessage;

    public ValidationResult() {
        this.valid = true;
        this.errorMessage = DEFAULT_POSITIVE_MSG;
    }

    public ValidationResult(String errorMessage) {
        this.valid = false;
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
