package com.rita.product_management.entrypoint.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record SwitchDisplayRuleRequest(
        @NotNull String id,
        @NotNull Boolean isActive
) {
}
