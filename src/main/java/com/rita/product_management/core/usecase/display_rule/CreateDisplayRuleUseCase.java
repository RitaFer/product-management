package com.rita.product_management.core.usecase.display_rule;

import com.rita.product_management.core.domain.DisplayRule;
import com.rita.product_management.core.gateway.DisplayRuleGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.display_rule.command.CreateDisplayRuleCommand;
import com.rita.product_management.entrypoint.api.dto.response.DisplayRuleResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class CreateDisplayRuleUseCase implements UseCase<CreateDisplayRuleCommand, DisplayRuleResponse> {

    private final DisplayRuleGateway displayRuleGateway;

    @Override
    public DisplayRuleResponse execute(CreateDisplayRuleCommand command) {
        log.info("Executing...");

        try {
            DisplayRule displayRule = displayRuleGateway.save(DisplayRule.builder().isActive(false).hiddenFields(command.hiddenFields()).build());
            log.debug("DisplayRule created and saved successfully: [{}]", displayRule.getId());

            DisplayRuleResponse response = mapToDisplayRuleResponse(displayRule);
            log.debug("DisplayRuleResponse successfully created for displayRule: [{}]", response);

            return response;

        } catch (Exception e) {
            log.error("Unexpected error occurred during account creation...", e);
            throw new RuntimeException("Failed to execute CreateDisplayRuleUseCase", e);
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
