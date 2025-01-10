package com.rita.product_management.entrypoint.api.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DisplayRuleResponse(
        @NotNull String id,
        @NotNull @NotEmpty List<String> hiddenFields
) {
}
