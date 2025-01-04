package com.rita.product_management.core.usecase.user;

import com.rita.product_management.core.domain.Token;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.TokenGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.user.command.SendTokenCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendTokenUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private TokenGateway tokenGateway;

    @Mock
    private EmailGateway emailGateway;

    @InjectMocks
    private SendTokenUseCase sendTokenUseCase;

    @Test
    void givenValidCommand_whenExecute_thenSendTokenSuccessfully() {
        SendTokenCommand command = new SendTokenCommand("username");
        User user = User.builder().id("1").username("username").email("user@example.com").build();
        Token token = Token.builder().token("token123").build();
        when(userGateway.findActiveUserByUsername("username")).thenReturn(user);
        when(tokenGateway.generateToken("1")).thenReturn(token);
        sendTokenUseCase.execute(command);
        verify(userGateway).findActiveUserByUsername("username");
        verify(tokenGateway).generateToken("1");
        verify(emailGateway).sendToken("user@example.com", "token123");
    }

    @Test
    void givenNonexistentUser_whenExecute_thenThrowException() {
        SendTokenCommand command = new SendTokenCommand("invalidUsername");
        when(userGateway.findActiveUserByUsername("invalidUsername")).thenThrow(new RuntimeException("User not found"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> sendTokenUseCase.execute(command));
        assertEquals("User not found", exception.getMessage());
        verify(userGateway).findActiveUserByUsername("invalidUsername");
        verifyNoInteractions(tokenGateway);
        verifyNoInteractions(emailGateway);
    }

    @Test
    void givenTokenGenerationFails_whenExecute_thenThrowException() {
        SendTokenCommand command = new SendTokenCommand("username");
        User user = User.builder().id("1").username("username").email("user@example.com").build();
        when(userGateway.findActiveUserByUsername("username")).thenReturn(user);
        when(tokenGateway.generateToken("1")).thenThrow(new RuntimeException("Token generation failed"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> sendTokenUseCase.execute(command));
        assertEquals("Token generation failed", exception.getMessage());
        verify(userGateway).findActiveUserByUsername("username");
        verify(tokenGateway).generateToken("1");
        verifyNoInteractions(emailGateway);
    }

}
