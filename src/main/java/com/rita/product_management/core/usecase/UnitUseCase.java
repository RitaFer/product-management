package com.rita.product_management.core.usecase;

import jakarta.validation.Valid;

public interface UnitUseCase<I> {
    void execute(@Valid I command);
}
