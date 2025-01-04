package com.rita.product_management.core.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;

public interface UseCase<I extends Command, O> {
    O execute (@Valid I command) throws JsonProcessingException;
}
