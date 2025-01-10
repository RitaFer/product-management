package com.rita.product_management.core.usecase;

import jakarta.validation.Valid;

public interface UseCase<I extends Command, O> {
    O execute (@Valid I command);
}
