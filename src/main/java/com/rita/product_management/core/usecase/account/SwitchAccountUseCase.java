package com.rita.product_management.core.usecase.account;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.account.command.SwitchAccountCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class SwitchAccountUseCase implements UnitUseCase<SwitchAccountCommand> {

    private static final String NOTIFICATION_MESSAGE = "Your account has a new active status = ";
    private final UserGateway userGateway;
    private final EmailGateway emailGateway;

    @Override
    public void execute(SwitchAccountCommand command) {
        log.info("Executing SwitchAccountUseCase for user IDs: [{}], active status: [{}]", command.ids(), command.active());

        for (String id : command.ids()) {
            try {
                log.debug("Processing switch status for user ID: [{}]", id);

                User user = userGateway.findUserById(id);
                log.debug("User found: [{}]", user);

                user.setActive(command.active());
                userGateway.save(user);
                log.debug("User status successfully updated to [{}]: [{}]", command.active(), user);

                emailGateway.sendUpdateNotification(user.getEmail(), NOTIFICATION_MESSAGE + command.active());
                log.debug("Status update notification email sent successfully to: [{}]", user.getEmail());

            } catch (Exception e) {
                log.error("Unexpected error occurred during status update for user ID: [{}]", id, e);
            }
        }

        log.info("SwitchAccountUseCase executed successfully for user IDs: [{}]", command.ids());
    }

}

