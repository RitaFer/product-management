package com.rita.product_management.core.usecase.category;

import com.rita.product_management.core.domain.Category;
import com.rita.product_management.core.gateway.CategoryGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.category.command.SwitchCategoryCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class SwitchCategoryUseCase implements UnitUseCase<SwitchCategoryCommand> {

    private final CategoryGateway categoryGateway;

    @Override
    public void execute(SwitchCategoryCommand command) {
        log.info("Executing SwitchCategoryUseCase for categories IDs: [{}], is active status: [{}]", command.ids(), command.isActive());

        for (String id : command.ids()) {
            try {
                log.debug("Processing switch status for category ID: [{}]", id);

                Category category = categoryGateway.findById(id);
                log.debug("Category found: [{}]", category);

                category.setIsActive(command.isActive());
                categoryGateway.save(category);
                log.debug("Category status successfully updated to [{}]: [{}]", command.isActive(), category.getId());

            } catch (Exception e) {
                log.error("Unexpected error occurred during status update for category ID: [{}]", id, e);
            }
        }

        log.info("SwitchCategoryUseCase executed successfully for category IDs: [{}]", command.ids());
    }

}

