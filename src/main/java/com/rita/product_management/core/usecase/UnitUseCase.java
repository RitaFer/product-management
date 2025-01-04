package com.rita.product_management.core.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;

import java.util.SequencedCollection;

public interface UnitUseCase<I> {
    void execute(@Valid I command) throws JsonProcessingException;
}
