package com.rita.product_management.core.usecase.account;

import com.rita.product_management.core.domain.enums.UserType;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.account.command.CreateAccountCommand;
import com.rita.product_management.entrypoint.api.dto.response.AccountResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAccountUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private EmailGateway emailGateway;

    @InjectMocks
    private CreateAccountUseCase createAccountUseCase;

    @Test
    void givenValidCommand_whenExecute_thenReturnAccountResponse() {
        CreateAccountCommand command = new CreateAccountCommand("John Doe", "john.doe@example.com", UserType.ADMIN);
        User savedUser = User.builder().id("1").name("John Doe").email("john.doe@example.com").role(UserType.ADMIN).build();
        given(userGateway.save(any(User.class))).willReturn(savedUser);
        doNothing().when(emailGateway).sendCreateAccountNotification(any(User.class));
        AccountResponse response = createAccountUseCase.execute(command);
        assertNotNull(response);
        assertEquals("1", response.id());
        assertEquals("John Doe", response.name());
        assertEquals("john.doe@example.com", response.email());
        assertEquals(UserType.ADMIN, response.role());
        verify(userGateway).save(any(User.class));
        verify(emailGateway).sendCreateAccountNotification(any(User.class));
    }

    @Test
    void givenUserGatewayThrowsException_whenExecute_thenThrowRuntimeException() {
        CreateAccountCommand command = new CreateAccountCommand("John Doe", "john.doe@example.com", UserType.ADMIN);
        given(userGateway.save(any(User.class))).willThrow(new RuntimeException("Database error"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> createAccountUseCase.execute(command));
        assertEquals("Failed to execute CreateAccountUseCase", exception.getMessage());
        verify(userGateway).save(any(User.class));
        verify(emailGateway, never()).sendCreateAccountNotification(any(User.class));
    }

    @Test
    void givenEmailGatewayThrowsException_whenExecute_thenThrowRuntimeException() {
        CreateAccountCommand command = new CreateAccountCommand("John Doe", "john.doe@example.com", UserType.STOCKIST);
        User savedUser = User.builder().id("1").name("John Doe").email("john.doe@example.com").role(UserType.ADMIN).build();
        given(userGateway.save(any(User.class))).willReturn(savedUser);
        doThrow(new RuntimeException("Email service error")).when(emailGateway).sendCreateAccountNotification(any(User.class));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> createAccountUseCase.execute(command));
        assertEquals("Failed to execute CreateAccountUseCase", exception.getMessage());
        verify(userGateway).save(any(User.class));
        verify(emailGateway).sendCreateAccountNotification(any(User.class));
    }

}
