package com.rita.product_management.core.usecase.category;

import com.rita.product_management.core.domain.Category;
import com.rita.product_management.core.gateway.CategoryGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.category.command.GetCategoryListCommand;
import com.rita.product_management.entrypoint.api.dto.response.CategoriesResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class GetCategoryListUseCase implements UseCase<GetCategoryListCommand, Page<CategoriesResponse>> {

    private final CategoryGateway categoryGateway;

    @Override
    public Page<CategoriesResponse> execute(GetCategoryListCommand command) {
        log.info("Executing GetCategoryListUseCase with pagination: [{}]", command.pageable());

        try {
            Page<CategoriesResponse> categories = categoryGateway.findAll(command.pageable())
                    .map(this::mapToCategoriesResponse);
            log.info("Successfully fetched [{}] categories.", categories.getTotalElements());
            return categories;

        } catch (Exception e) {
            log.error("Error occurred while fetching category list with pagination: [{}]", command.pageable(), e);
            throw new RuntimeException("Failed to execute GetCategoryListUseCase", e);
        }
    }

    private CategoriesResponse mapToCategoriesResponse(Category category) {
        log.debug("Mapping Category to CategoriesResponse for categoryId: [{}]", category.getId());
        return new CategoriesResponse(
                category.getId(),
                category.getIsActive(),
                category.getName(),
                category.getActive()
        );
    }

}
