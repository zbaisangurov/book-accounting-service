package com.kode.bookaccountingservice.exception;

import java.util.List;

public class ValidationErrorResponse {

    private final List<Violation> violations;

    public List<Violation> getViolations() {
        return violations;
    }

    public ValidationErrorResponse(List<Violation> violations) {
        this.violations = violations;
    }
}
