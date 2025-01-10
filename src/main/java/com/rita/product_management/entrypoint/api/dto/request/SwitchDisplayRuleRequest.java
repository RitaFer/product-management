package com.rita.product_management.entrypoint.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record SwitchDisplayRuleRequest(
        @NotNull(message = "cannot be null")
        String id,
        @NotNull(message = "cannot be null")
        Boolean isActive
) {
}
