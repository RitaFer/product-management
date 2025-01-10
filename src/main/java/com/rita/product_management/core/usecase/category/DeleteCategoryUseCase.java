package com.rita.product_management.core.usecase.category;

import com.rita.product_management.core.gateway.CategoryGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.category.command.DeleteCategoryCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class DeleteCategoryUseCase implements UnitUseCase<DeleteCategoryCommand> {

    private final CategoryGateway categoryGateway;

    @Override
    public void execute(DeleteCategoryCommand command) {
        log.info("Executing DeleteCategoryUseCase for category IDs: [{}]", command.ids());

        for (String id : command.ids()) {
            log.debug("Processing deletion for category ID: [{}]", id);
            categoryGateway.delete(id);
            log.debug("Category successfully deleted: [{}]", id);
        }

        log.info("DeleteCategoryUseCase executed successfully for category IDs: [{}]", command.ids());
    }

}
