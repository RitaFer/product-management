package com.rita.product_management.core.usecase.account;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UnitUseCase;
import com.rita.product_management.core.usecase.account.command.DeleteAccountCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class DeleteAccountUseCase implements UnitUseCase<DeleteAccountCommand> {

    private final UserGateway userGateway;
    private final EmailGateway emailGateway;

    @Override
    public void execute(DeleteAccountCommand command) {
        log.info("Executing DeleteAccountUseCase for user IDs: [{}]", command.ids());

        for (String id : command.ids()) {
            try {
                log.debug("Processing deletion for user ID: [{}]", id);

                User user = userGateway.findUserById(id);
                log.debug("User found for deletion: [{}]", user);

                userGateway.delete(user);
                log.debug("User successfully deleted: [{}]", user);

                emailGateway.sendDeleteNotification(user.getEmail());
                log.debug("Delete notification email sent successfully to: [{}]", user.getEmail());

            } catch (Exception e) {
                log.error("Unexpected error occurred during account deletion for user ID: [{}]", id, e);
            }
        }

        log.info("DeleteAccountUseCase executed successfully for user IDs: [{}]", command.ids());
    }

}
