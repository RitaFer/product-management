package com.rita.product_management.core.usecase.category;

import com.rita.product_management.core.domain.Category;
import com.rita.product_management.core.gateway.CategoryGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.category.command.CreateCategoryCommand;
import com.rita.product_management.entrypoint.api.dto.response.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class CreateCategoryUseCase implements UseCase<CreateCategoryCommand, CategoryResponse> {

    private final CategoryGateway categoryGateway;

    @Override
    public CategoryResponse execute(CreateCategoryCommand command) {
        log.info("Executing CreateCategoryUseCase for name: [{}], type: [{}]", command.name(), command.type());

        try {
            Category category = categoryGateway.save(new Category(
                    command.name(),
                    command.active(),
                    command.type()
            ));
            log.debug("Category created and saved successfully: [{}]", category.getId());

            CategoryResponse response = mapToCategoryResponse(category);
            log.info("CategoryResponse successfully created for category: [{}]", response.id());

            return response;

        } catch (Exception e) {
            log.error("Unexpected error occurred during account creation for name: [{}]", command.name(), e);
            throw new RuntimeException("Failed to execute CreateCategoryUseCase", e);
        }
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
