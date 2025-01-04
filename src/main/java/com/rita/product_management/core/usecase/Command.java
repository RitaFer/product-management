package com.rita.product_management.core.usecase;

import com.rita.product_management.core.common.exception.ValidationCommandException;
import jakarta.validation.Validation;

public interface Command {

    default boolean validate() {
        var factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();
        var violations = validator.validate(this);

        if(!violations.isEmpty()){
            throw new ValidationCommandException("Validation Error", violations);
        }
        return true;
    }

}
