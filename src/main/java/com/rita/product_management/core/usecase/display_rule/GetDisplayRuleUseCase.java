package com.rita.product_management.core.usecase.display_rule;

import com.rita.product_management.core.domain.DisplayRule;
import com.rita.product_management.core.gateway.DisplayRuleGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.display_rule.command.GetDisplayRuleCommand;
import com.rita.product_management.entrypoint.api.dto.response.DisplayRuleResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class GetDisplayRuleUseCase implements UseCase<GetDisplayRuleCommand, DisplayRuleResponse> {

    private final DisplayRuleGateway categoryGateway;

    @Override
    public DisplayRuleResponse execute(GetDisplayRuleCommand command) {
        log.info("Executing GetDisplayRuleUseCase: [{}]", command.id());

        DisplayRule displayRule = categoryGateway.findById(command.id());
        return mapToDisplayRuleResponse(displayRule);
    }

    private DisplayRuleResponse mapToDisplayRuleResponse(DisplayRule displayRule) {
        log.debug("Mapping DisplayRule to DisplayRuleResponse for displayRuleId: [{}]", displayRule.getId());
        return new DisplayRuleResponse(
                displayRule.getId(),
                displayRule.getHiddenFields()
        );
    }

}
