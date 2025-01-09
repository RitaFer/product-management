package com.rita.product_management.core.usecase.display_rule;

import com.rita.product_management.core.domain.DisplayRule;
import com.rita.product_management.core.gateway.DisplayRuleGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.display_rule.command.UpdateDisplayRuleCommand;
import com.rita.product_management.entrypoint.api.dto.response.DisplayRuleResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class UpdateDisplayRuleUseCase implements UseCase<UpdateDisplayRuleCommand, DisplayRuleResponse> {

    private final DisplayRuleGateway displayRuleGateway;

    @Override
    public DisplayRuleResponse execute(UpdateDisplayRuleCommand command) {
        log.info("Executing UpdateDisplayRuleUseCase for displayRule ID: [{}]", command.id());

        try {
            DisplayRule displayRule = displayRuleGateway.findDisplayRuleById(command.id());
            log.debug("DisplayRule found: [{}]", displayRule.getId());

            displayRule.setHiddenFields(command.hiddenFields());

            DisplayRule updatedDisplayRule = displayRuleGateway.save(displayRule);
            log.info("DisplayRule successfully updated: [{}]", updatedDisplayRule.getId());

            return mapToDisplayRuleResponse(updatedDisplayRule);
        }catch (Exception e) {
            log.error("Unexpected error occurred during account update for displayRule ID: [{}]", command.id(), e);
            throw new RuntimeException("Failed to execute UpdateDisplayRuleUseCase", e);
        }
    }

    private DisplayRuleResponse mapToDisplayRuleResponse(DisplayRule displayRule) {
        log.debug("Mapping DisplayRule to DisplayRuleResponse for displayRuleId: [{}]", displayRule.getId());
        return new DisplayRuleResponse(
                displayRule.getId(),
                displayRule.getHiddenFields()
        );
    }

}
