package com.rita.product_management.core.usecase.category;

import com.rita.product_management.core.common.exception.NoChangesException;
import com.rita.product_management.core.domain.Category;
import com.rita.product_management.core.gateway.CategoryGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.category.command.UpdateCategoryCommand;
import com.rita.product_management.entrypoint.api.dto.response.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class UpdateCategoryUseCase implements UseCase<UpdateCategoryCommand, CategoryResponse> {

    private final CategoryGateway categoryGateway;

    @Override
    public CategoryResponse execute(UpdateCategoryCommand command) {
        log.info("Executing UpdateCategoryUseCase for category ID: [{}]", command.id());

        try {
            Category category = categoryGateway.findById(command.id());
            log.debug("Category found: [{}]", category.getId());

            UpdateResult updateResult = updateCategoryFields(category, command);

            if (!updateResult.hasChanges()) {
                log.warn("No fields were updated for category ID: [{}]", command.id());
                throw new NoChangesException("No fields were updated.");
            }

            Category updatedCategory = categoryGateway.save(category);
            log.info("Category successfully updated: [{}]", updatedCategory);


            CategoryResponse response = mapToCategoryResponse(updatedCategory);
            log.info("CategoryResponse successfully created for category: [{}]", response.id());

            return response;

        } catch (NoChangesException e) {
            log.warn("No changes detected for category ID: [{}]. Message: [{}]", command.id(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred during account update for category ID: [{}]", command.id(), e);
            throw new RuntimeException("Failed to execute UpdateCategoryUseCase", e);
        }
    }

    private UpdateResult updateCategoryFields(Category category, UpdateCategoryCommand command) {
        log.debug("Updating fields for category ID: [{}]", category.getId());
        UpdateResult result = new UpdateResult();

        if (!category.getName().equals(command.name())) {
            category.setName(command.name());
            result.addChange("name");
        }

        if (!category.getActive().equals(command.active())) {
            category.setActive(command.active());
            result.addChange("active");
        }

        if (!category.getType().equals(command.type())) {
            category.setType(command.type());
            result.addChange("type");
        }

        log.debug("Fields updated for category ID: [{}]: [{}]", category.getId(), result.getChangesDescription());
        return result;
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        log.debug("Mapping Category to CategoryResponse for category ID: [{}]", category.getId());
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getActive(),
                category.getType()
        );
    }

    private static class UpdateResult {
        private final StringBuilder changesDescription;
        private boolean hasChanges;

        public UpdateResult() {
            this.changesDescription = new StringBuilder("Updated fields: ");
            this.hasChanges = false;
        }

        public void addChange(String field) {
            changesDescription.append(field).append(", ");
            hasChanges = true;
        }

        public boolean hasChanges() {
            return hasChanges;
        }

        public String getChangesDescription() {
            if (hasChanges) {
                changesDescription.setLength(changesDescription.length() - 2);
            }
            return changesDescription.toString();
        }
    }

}
