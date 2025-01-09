package com.rita.product_management.core.usecase.display_rule;

import com.rita.product_management.core.domain.DisplayRule;
import com.rita.product_management.core.gateway.DisplayRuleGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.display_rule.command.GetDisplayRuleListCommand;
import com.rita.product_management.entrypoint.api.dto.response.DisplayRulesResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class GetDisplayRuleListUseCase implements UseCase<GetDisplayRuleListCommand, Page<DisplayRulesResponse>> {

    private final DisplayRuleGateway displayRuleGateway;

    @Override
    public Page<DisplayRulesResponse> execute(GetDisplayRuleListCommand command) {
        log.info("Executing GetDisplayRuleListUseCase with pagination: [{}]", command.pageable());

        try {
            Page<DisplayRulesResponse> displayRules = displayRuleGateway.findAll(command.pageable())
                    .map(this::mapToDisplayRulesResponse);
            log.info("Successfully fetched [{}] displayRules.", displayRules.getTotalElements());
            return displayRules;

        } catch (Exception e) {
            log.error("Error occurred while fetching displayRule list with pagination: [{}]", command.pageable(), e);
            throw new RuntimeException("Failed to execute GetDisplayRuleListUseCase", e);
        }
    }

    private DisplayRulesResponse mapToDisplayRulesResponse(DisplayRule displayRule) {
        log.debug("Mapping DisplayRule to DisplayRulesResponse for displayRuleId: [{}]", displayRule.getId());
        return new DisplayRulesResponse(
                displayRule.getId(),
                displayRule.getIsActive()
        );
    }

}
