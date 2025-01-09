package com.rita.product_management.entrypoint.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateDisplayRuleRequest(
        @NotNull String id,
        @NotNull(message = "cannot be null")
        @NotEmpty(message = "cannot be empty")
        List<String> hiddenFields
) {
}
