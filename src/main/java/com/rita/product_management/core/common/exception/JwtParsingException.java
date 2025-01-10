package com.rita.product_management.core.common.exception;

public class JwtParsingException  extends RuntimeException {

    public JwtParsingException(String message) {
        super(message);
    }

}