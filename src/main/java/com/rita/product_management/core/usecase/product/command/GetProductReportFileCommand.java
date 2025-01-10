package com.rita.product_management.core.usecase.product.command;

import com.rita.product_management.core.domain.enums.FileType;
import com.rita.product_management.core.usecase.Command;
import com.rita.product_management.entrypoint.api.dto.filters.ProductFilter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record GetProductReportFileCommand(
        @NotNull Pageable pageable,
        @NotNull ProductFilter filter,
        @NotNull @NotEmpty List<String> fields,
        @NotNull FileType format
) implements Command {

}