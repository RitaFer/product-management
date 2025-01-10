package com.rita.product_management.core.usecase.category;

import com.rita.product_management.core.domain.Category;
import com.rita.product_management.core.gateway.CategoryGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.category.command.GetCategoryCommand;
import com.rita.product_management.entrypoint.api.dto.response.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class GetCategoryUseCase implements UseCase<GetCategoryCommand, CategoryResponse> {

    private final CategoryGateway categoryGateway;

    @Override
    public CategoryResponse execute(GetCategoryCommand command) {
        log.info("Executing GetCategoryUseCase: [{}]", command.id());

        Category category = categoryGateway.findById(command.id());
        return mapToCategoryResponse(category);
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        log.debug("Mapping Category to CategoryResponse for categoryId: [{}]", category.getId());
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getActive(),
                category.getType()
        );
    }

}
