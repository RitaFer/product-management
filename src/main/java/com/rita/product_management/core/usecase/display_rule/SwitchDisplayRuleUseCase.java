package com.rita.product_management.core.usecase.display_rule;

import com.rita.product_management.core.common.exception.BusinessException;
import com.rita.product_management.core.domain.DisplayRule;
import com.rita.product_management.core.gateway.DisplayRuleGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.display_rule.command.SwitchDisplayRuleCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class SwitchDisplayRuleUseCase implements UnitUseCase<SwitchDisplayRuleCommand> {

    private final DisplayRuleGateway displayRuleGateway;

    @Override
    public void execute(SwitchDisplayRuleCommand command) {
        log.info("Executing SwitchDisplayRuleUseCase for ruleId: [{}] and active status: [{}]", command.id(), command.isActive());

        if (command.isActive()) {
            log.info("Validating that no other active DisplayRule exists...");
            boolean hasOtherActiveRules = displayRuleGateway.existsAnotherActiveDisplayRule();
            if (hasOtherActiveRules) {
                log.error("Cannot activate DisplayRule [{}] because another active rule already exists.", command.id());
                throw new BusinessException("Cannot activate more than one DisplayRule at the same time.");
            }
        }

        DisplayRule displayRule = displayRuleGateway.findById(command.id());
        log.debug("DisplayRule found: [{}]", displayRule);

        displayRule.setIsActive(command.isActive());
        displayRuleGateway.save(displayRule);
        log.info("DisplayRule status successfully updated to [{}]: [{}]", command.isActive(), displayRule.getId());
    }

}

