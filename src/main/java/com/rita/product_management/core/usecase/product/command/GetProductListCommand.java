package com.rita.product_management.core.usecase.product.command;

import com.rita.product_management.core.usecase.Command;
import com.rita.product_management.entrypoint.api.dto.filters.ProductFilter;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

public record GetProductListCommand(
        @NotNull Pageable pageable,
        @NotNull ProductFilter filter
) implements Command {

}