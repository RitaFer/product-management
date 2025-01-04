package com.rita.product_management.core.usecase.account;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.account.command.DeleteAccountCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteAccountUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private EmailGateway emailGateway;

    @InjectMocks
    private DeleteAccountUseCase deleteAccountUseCase;

    @Test
    void givenValidUserIds_whenExecute_thenUsersAreDeletedAndEmailsAreSent() {
        String userId1 = "user1";
        String userId2 = "user2";
        User user1 = User.builder().id(userId1).email("user1@example.com").build();
        User user2 = User.builder().id(userId2).email("user2@example.com").build();
        when(userGateway.findUserById(userId1)).thenReturn(user1);
        when(userGateway.findUserById(userId2)).thenReturn(user2);
        DeleteAccountCommand command = new DeleteAccountCommand(List.of(userId1, userId2));
        deleteAccountUseCase.execute(command);
        verify(userGateway, times(2)).findUserById(any());
        verify(userGateway, times(2)).delete(any());
        verify(emailGateway, times(2)).sendDeleteNotification(any());
        verifyNoMoreInteractions(userGateway);
        verifyNoMoreInteractions(emailGateway);
    }

    @Test
    void givenInvalidUserId_whenExecute_thenLogErrorAndContinueProcessing() {
        String validUserId = "validUser";
        String invalidUserId = "invalidUser";
        User validUser = User.builder().id(validUserId).email("validUser@example.com").build();
        when(userGateway.findUserById(validUserId)).thenReturn(validUser);
        when(userGateway.findUserById(invalidUserId)).thenThrow(new RuntimeException("User not found"));
        DeleteAccountCommand command = new DeleteAccountCommand(List.of(validUserId, invalidUserId));
        deleteAccountUseCase.execute(command);
        verify(userGateway).findUserById(validUserId);
        verify(userGateway).findUserById(invalidUserId);
        verify(userGateway).delete(validUser);
        verify(emailGateway).sendDeleteNotification(validUser.getEmail());
        verifyNoMoreInteractions(emailGateway);
    }

    @Test
    void givenEmptyUserIdList_whenExecute_thenDoNothing() {
        DeleteAccountCommand command = new DeleteAccountCommand(Collections.emptyList());
        deleteAccountUseCase.execute(command);
        verifyNoInteractions(userGateway);
        verifyNoInteractions(emailGateway);
    }

}
