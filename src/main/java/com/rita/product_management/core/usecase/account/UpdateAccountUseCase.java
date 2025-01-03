package com.rita.product_management.core.usecase.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rita.product_management.core.common.exception.NoChangesException;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.UseCase;
import com.rita.product_management.core.usecase.account.command.UpdateAccountCommand;
import com.rita.product_management.entrypoint.api.dto.response.AccountResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class UpdateAccountUseCase implements UseCase<UpdateAccountCommand, AccountResponse> {

    private final UserGateway userGateway;
    private final EmailGateway emailGateway;

    public UpdateAccountUseCase(UserGateway userGateway, EmailGateway emailGateway) {
        this.userGateway = userGateway;
        this.emailGateway = emailGateway;
    }

    @Override
    public AccountResponse execute(UpdateAccountCommand command) throws JsonProcessingException {
        User user = userGateway.findUserById(command.id());
        UpdateResult updateResult = updateUserFields(user, command);

        if (!updateResult.hasChanges()) {
            throw new NoChangesException("No fields were updated.");
        }

        User updatedUser = userGateway.save(user);
        emailGateway.sendUpdateNotification(updatedUser.getEmail(), updateResult.getChangesDescription());

        return mapToAccountResponse(updatedUser);
    }

    private UpdateResult updateUserFields(User user, UpdateAccountCommand command) {
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

        return result;
    }

    private AccountResponse mapToAccountResponse(User user) {
        return new AccountResponse(
                user.getId(),
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
            if (hasChanges) {
                changesDescription.setLength(changesDescription.length() - 2);
            }
            return changesDescription.toString();
        }
    }

}
