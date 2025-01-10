package com.rita.product_management.entrypoint.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SwitchCategoryRequest(
        @NotEmpty(message = "cannot be empty")
        @NotNull(message = "cannot be null")
        List<String> ids,
        @NotNull Boolean isActive
) {

}
