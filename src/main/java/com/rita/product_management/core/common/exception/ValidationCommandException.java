package com.rita.product_management.core.common.exception;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class ValidationCommandException extends RuntimeException {
    private Set<ConstraintViolation<Command>> violations;

    public ValidationCommandException(String message, Set<ConstraintViolation<Command>> violations){
        super(message);
        this.violations = violations;
    }

}
