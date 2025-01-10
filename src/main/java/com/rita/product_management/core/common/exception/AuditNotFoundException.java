package com.rita.product_management.core.common.exception;

public class AuditNotFoundException extends RuntimeException {
    public AuditNotFoundException(String message) {
        super(message);
    }
}
