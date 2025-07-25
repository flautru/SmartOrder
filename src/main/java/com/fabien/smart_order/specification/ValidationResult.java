package com.fabien.smart_order.specification;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidationResult {
    private final List<String> errorMessages;

    public ValidationResult() {
        this.errorMessages = new ArrayList<>();
    }

    public boolean isValid() {
        return errorMessages.isEmpty();
    }

    public List<String> getErrorMessages() {
        return new ArrayList<>(errorMessages);
    }

    public void addError(final String errorMessage) {
        errorMessages.add(errorMessage);
    }

    public String getAllErrorsAsString() {
        return String.join(", ", errorMessages);
    }
}
