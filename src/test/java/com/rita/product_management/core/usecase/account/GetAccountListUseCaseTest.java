package com.rita.product_management.core.usecase.account;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.account.command.GetAccountListCommand;
import com.rita.product_management.entrypoint.api.dto.response.AccountsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetAccountListUseCaseTest {

    private GetAccountListUseCase getAccountListUseCase;

    @Mock
    private UserGateway userGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getAccountListUseCase = new GetAccountListUseCase(userGateway);
    }

    @Test
    void givenValidCommand_whenExecute_thenReturnAccountsResponse() {
        PageRequest pageable = PageRequest.of(0, 10);
        GetAccountListCommand command = new GetAccountListCommand(pageable);
        User mockUser = User.builder().id("1").active(true).name("John Doe").build();
        List<User> users = Collections.singletonList(mockUser);
        Page<User> userPage = new PageImpl<>(users, pageable, 1);
        when(userGateway.findAll(pageable)).thenReturn(userPage);
        Page<AccountsResponse> result = getAccountListUseCase.execute(command);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("1", result.getContent().getFirst().id());
        assertEquals("John Doe", result.getContent().getFirst().name());
        verify(userGateway, times(1)).findAll(pageable);
    }

    @Test
    void givenUserGatewayThrowsException_whenExecute_thenThrowRuntimeException() {
        PageRequest pageable = PageRequest.of(0, 10);
        GetAccountListCommand command = new GetAccountListCommand(pageable);
        when(userGateway.findAll(pageable)).thenThrow(new RuntimeException("Database error"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> getAccountListUseCase.execute(command));
        assertEquals("Failed to execute GetAccountListUseCase", exception.getMessage());
        verify(userGateway, times(1)).findAll(pageable);
    }

}
