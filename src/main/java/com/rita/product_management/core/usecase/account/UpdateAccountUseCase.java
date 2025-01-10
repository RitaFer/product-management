package com.rita.product_management.core.usecase.account;

import com.rita.product_management.core.common.exception.NoChangesException;
import com.rita.product_management.core.domain.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.account.command.UpdateAccountCommand;
import com.rita.product_management.entrypoint.api.dto.response.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Component
@Validated
@AllArgsConstructor
public class UpdateAccountUseCase implements UseCase<UpdateAccountCommand, AccountResponse> {

    private final UserGateway userGateway;
    private final EmailGateway emailGateway;

    @Override
    public AccountResponse execute(UpdateAccountCommand command) {
        log.info("Executing UpdateAccountUseCase for user ID: [{}]", command.id());
        User user = userGateway.findUserById(command.id());
        log.debug("User found: [{}]", user);

        try {
            UpdateResult updateResult = updateUserFields(user, command);

            if (!updateResult.hasChanges()) {
                log.warn("No fields were updated for user ID: [{}]", command.id());
                throw new NoChangesException("No fields were updated.");
            }

            User updatedUser = userGateway.save(user);
            log.debug("User successfully updated: [{}]", updatedUser);

            emailGateway.sendUpdateNotification(updatedUser.getEmail(), updateResult.getChangesDescription());
            log.debug("Update notification email sent successfully to: [{}]", updatedUser.getEmail());

            AccountResponse response = mapToAccountResponse(updatedUser);
            log.info("AccountResponse successfully created for user: [{}]", response.id());

            return response;
        } catch (NoChangesException e) {
            log.warn("No changes detected for user ID: [{}]. Message: [{}]", command.id(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred during account update for user ID: [{}]", command.id(), e);
            throw new RuntimeException("Failed to execute UpdateAccountUseCase", e);
        }
    }

    private UpdateResult updateUserFields(User user, UpdateAccountCommand command) {
        log.debug("Updating fields for user ID: [{}]", user.getId());
        UpdateResult result = new UpdateResult();

        if (!user.getName().equals(command.name())) {
            user.setName(command.name());
            result.addChange("name");
        }

        if (!user.getEmail().equals(command.email())) {
            user.setEmail(command.email());
            result.addChange("email");
        }

        if (!user.getRole().equals(command.role())) {
            user.setRole(command.role());
            result.addChange("role");
        }

        log.debug("Fields updated for user ID: [{}]: [{}]", user.getId(), result.getChangesDescription());
        return result;
    }

    private AccountResponse mapToAccountResponse(User user) {
        log.debug("Mapping User to AccountResponse for user ID: [{}]", user.getId());
        return new AccountResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    private static class UpdateResult {
        private final StringBuilder changesDescription;
        private boolean hasChanges;

        public UpdateResult() {
            this.changesDescription = new StringBuilder("Updated fields: ");
            this.hasChanges = false;
        }

        public void addChange(String field) {
            changesDescription.append(field).append(", ");
            hasChanges = true;
        }

        public boolean hasChanges() {
            return hasChanges;
        }

        public String getChangesDescription() {
            return changesDescription.toString();
        }
    }

}
