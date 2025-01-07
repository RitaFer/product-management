package com.rita.product_management.entrypoint.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SwitchCategoryRequest(
        @NotEmpty
        List<String> ids,
        @NotNull Boolean active
) {

}
