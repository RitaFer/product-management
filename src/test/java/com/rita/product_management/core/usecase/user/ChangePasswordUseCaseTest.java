package com.rita.product_management.core.usecase.user;

import com.rita.product_management.core.common.exception.BusinessException;
import com.rita.product_management.core.domain.Token;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.TokenGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.user.command.ChangePasswordCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChangePasswordUseCaseTest {

    @Mock
    private TokenGateway tokenGateway;

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private ChangePasswordUseCase changePasswordUseCase;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    void givenValidTokenAndMatchingPasswordsThenChangePassword() {
        String tokenValue = "validToken";
        String username = "testUser";
        String newPassword = "newPassword123";
        ChangePasswordCommand command = new ChangePasswordCommand(tokenValue, newPassword, newPassword);
        Token token = Token.builder().token(tokenValue).userId(username).build();
        User user = User.builder().username(username).password("oldPassword123").build();
        when(tokenGateway.validateToken(tokenValue)).thenReturn(true);
        when(tokenGateway.findToken(tokenValue)).thenReturn(token);
        when(userGateway.findActiveUserByUsername(username)).thenReturn(user);
        changePasswordUseCase.execute(command);
        verify(userGateway).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals(newPassword, savedUser.getPassword());
    }

    @Test
    void givenInvalidTokenThenThrowException() {
        String tokenValue = "invalidToken";
        ChangePasswordCommand command = new ChangePasswordCommand(tokenValue, "password123", "password123");
        when(tokenGateway.validateToken(tokenValue)).thenReturn(false);
        BusinessException exception = assertThrows(BusinessException.class, () -> changePasswordUseCase.execute(command));
        assertEquals("Invalid information provided", exception.getMessage());
    }

    @Test
    void givenNonMatchingPasswordsThenThrowException() {
        String tokenValue = "validToken";
        ChangePasswordCommand command = new ChangePasswordCommand(tokenValue, "password123", "password456");
        when(tokenGateway.validateToken(tokenValue)).thenReturn(true);
        BusinessException exception = assertThrows(BusinessException.class, () -> changePasswordUseCase.execute(command));
        assertEquals("Invalid information provided", exception.getMessage());
    }

    @Test
    void givenValidTokenButUserNotFoundThenThrowException() {
        String tokenValue = "validToken";
        String newPassword = "newPassword123";
        ChangePasswordCommand command = new ChangePasswordCommand(tokenValue, newPassword, newPassword);
        Token token = Token.builder().token(tokenValue).userId("nonExistentUser").build();
        when(tokenGateway.validateToken(tokenValue)).thenReturn(true);
        when(tokenGateway.findToken(tokenValue)).thenReturn(token);
        when(userGateway.findActiveUserByUsername("nonExistentUser")).thenThrow(new BusinessException("User not found"));
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            changePasswordUseCase.execute(command);
        });
        assertEquals("User not found", exception.getMessage());
    }

}
