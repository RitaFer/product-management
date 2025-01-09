package com.rita.product_management.core.usecase.account;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.account.command.SwitchAccountCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SwitchAccountUseCaseTest {
//
//    @Mock
//    private UserGateway userGateway;
//
//    @Mock
//    private EmailGateway emailGateway;
//
//    @InjectMocks
//    private SwitchAccountUseCase switchAccountUseCase;
//
//    @Test
//    void givenValidUserId_whenExecute_thenUpdateUserStatusAndSendNotificationSuccessfully() {
//        String userId = "user1";
//        SwitchAccountCommand command = new SwitchAccountCommand(List.of(userId), true);
//        User user = User.builder().id(userId).email("user1@example.com").active(false).build();
//        given(userGateway.findUserById(userId)).willReturn(user);
//        switchAccountUseCase.execute(command);
//        then(userGateway).should().findUserById(userId);
//        then(userGateway).should().save(user);
//        assertTrue(user.getActive());
//        then(emailGateway).should().sendUpdateNotification(user.getEmail(), "Your account has a new active status = true");
//    }
//
//    @Test
//    void givenMultipleUsersAndOneNotFound_whenExecute_thenContinueProcessingOtherUsers() {
//        String userId1 = "user1";
//        String userId2 = "user2";
//        SwitchAccountCommand command = new SwitchAccountCommand(List.of(userId1, userId2), true);
//        User user1 = User.builder().id(userId1).email("user1@example.com").active(false).build();
//        given(userGateway.findUserById(userId1)).willReturn(user1);
//        given(userGateway.findUserById(userId2)).willThrow(new RuntimeException("User not found"));
//        switchAccountUseCase.execute(command);
//        then(userGateway).should().findUserById(userId1);
//        then(userGateway).should().save(user1);
//        then(emailGateway).should().sendUpdateNotification(user1.getEmail(), "Your account has a new active status = true");
//        then(userGateway).should().findUserById(userId2);
//        then(emailGateway).shouldHaveNoMoreInteractions();
//    }
//
//    @Test
//    void givenEmailServiceError_whenExecute_thenLogError() {
//        String userId = "user1";
//        SwitchAccountCommand command = new SwitchAccountCommand(List.of(userId), true);
//        User user = User.builder().id(userId).email("user1@example.com").active(false).build();
//        given(userGateway.findUserById(userId)).willReturn(user);
//        willThrow(new RuntimeException("Email service error")).given(emailGateway).sendUpdateNotification(user.getEmail(), "Your account has a new active status = true");
//        switchAccountUseCase.execute(command);
//        then(userGateway).should().findUserById(userId);
//        then(userGateway).should().save(user);
//        then(emailGateway).should().sendUpdateNotification(user.getEmail(), "Your account has a new active status = true");
//    }

}
