package com.rita.product_management.core.usecase.category.command;

import com.rita.product_management.core.usecase.Command;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

public record GetCategoryListCommand(
        @NotNull Pageable pageable
) implements Command {

}
