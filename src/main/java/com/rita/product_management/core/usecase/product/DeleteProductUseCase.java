package com.rita.product_management.core.usecase.product;

import com.rita.product_management.core.gateway.ProductGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.product.command.DeleteProductCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class DeleteProductUseCase implements UnitUseCase<DeleteProductCommand> {

    private final ProductGateway categoryGateway;

    @Override
    public void execute(DeleteProductCommand command) {
        log.info("Executing DeleteProductUseCase for user IDs: [{}]", command.ids());

        for (String id : command.ids()) {
            try {
                log.debug("Processing deletion for user ID: [{}]", id);
                categoryGateway.delete(id);
                log.debug("Product successfully deleted: [{}]", id);
            } catch (Exception e) {
                log.error("Unexpected error occurred during account deletion for user ID: [{}]", id, e);
            }
        }

        log.info("DeleteProductUseCase executed successfully for user IDs: [{}]", command.ids());
    }

}
