package com.rita.product_management.core.usecase.display_rule;

import com.rita.product_management.core.gateway.DisplayRuleGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.display_rule.command.DeleteDisplayRuleCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class DeleteDisplayRuleUseCase implements UnitUseCase<DeleteDisplayRuleCommand> {

    private final DisplayRuleGateway displayRuleGateway;

    @Override
    public void execute(DeleteDisplayRuleCommand command) {
        log.info("Executing DeleteDisplayRuleUseCase for rules: [{}]", command.ids());

        for (String id : command.ids()) {
            log.debug("Processing deletion for rule ID: [{}]", id);
            displayRuleGateway.delete(id);
            log.debug("DisplayRule successfully deleted: [{}]", id);
        }

        log.info("DeleteDisplayRuleUseCase executed successfully for rules: [{}]", command.ids());
    }

}
