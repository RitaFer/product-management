package com.rita.product_management.core.usecase.account;

import com.rita.product_management.core.common.exception.NoChangesException;
import com.rita.product_management.core.common.exception.UserNotFoundException;
import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.account.command.UpdateAccountCommand;
import com.rita.product_management.entrypoint.api.dto.response.AccountResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateAccountUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private EmailGateway emailGateway;

    @InjectMocks
    private UpdateAccountUseCase updateAccountUseCase;

    @Test
    void givenValidCommand_whenUserFieldsUpdated_thenReturnsAccountResponse() {
        UpdateAccountCommand command = new UpdateAccountCommand("1", "New Name", "newemail@example.com", UserType.ADMIN);
        User existingUser = User.builder().id("1").name("Old Name").email("oldemail@example.com").role(UserType.STOCKIST).build();
        User updatedUser = User.builder().id("1").name("New Name").email("newemail@example.com").role(UserType.ADMIN).build();
        when(userGateway.findUserById(command.id())).thenReturn(existingUser);
        when(userGateway.save(existingUser)).thenReturn(updatedUser);
        AccountResponse response = updateAccountUseCase.execute(command);
        assertNotNull(response);
        assertEquals(command.name(), response.name());
        assertEquals(command.email(), response.email());
        assertEquals(command.role(), response.role());
        verify(emailGateway, times(1)).sendUpdateNotification(command.email(), "Updated fields: name, email, ro");
    }

    @Test
    void givenCommandWithNoChanges_whenExecuted_thenThrowsNoChangesException() {
        UpdateAccountCommand command = new UpdateAccountCommand("1", "Same Name", "sameemail@example.com", UserType.ADMIN);
        User existingUser = User.builder().id("1").name("Same Name").email("sameemail@example.com").role(UserType.ADMIN).build();
        when(userGateway.findUserById(command.id())).thenReturn(existingUser);
        NoChangesException exception = assertThrows(NoChangesException.class, () -> updateAccountUseCase.execute(command));
        assertEquals("No fields were updated.", exception.getMessage());
        verify(userGateway, never()).save(any());
        verify(emailGateway, never()).sendUpdateNotification(any(), any());
    }

    @Test
    void givenInvalidUserId_whenExecuted_thenThrowsRuntimeException() {
        UpdateAccountCommand command = new UpdateAccountCommand("999", "Name", "email@example.com", UserType.STOCKIST);
        when(userGateway.findUserById(command.id())).thenThrow(new UserNotFoundException("User not found"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> updateAccountUseCase.execute(command));
        assertTrue(exception.getMessage().contains("Failed to execute UpdateAccountUseCase"));
        verify(userGateway, times(1)).findUserById(command.id());
        verify(userGateway, never()).save(any());
        verify(emailGateway, never()).sendUpdateNotification(any(), any());
    }

}
