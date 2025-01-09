package com.rita.product_management.core.usecase.user;

import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.UserGateway;
import com.rita.product_management.core.usecase.user.command.AuthCommand;
import com.rita.product_management.entrypoint.api.dto.response.AuthResponse;
import com.rita.product_management.infrastructure.security.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthenticateUseCaseTest {

//    @Mock
//    private JwtUtil jwtUtil;
//
//    @Mock
//    private UserGateway userGateway;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @InjectMocks
//    private AuthenticateUseCase authenticateUseCase;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void givenValidCredentials_whenExecute_thenGenerateTokenSuccessfully() {
//        String username = "testUser";
//        String password = "password123";
//        AuthCommand command = new AuthCommand(username, password);
//        User user = new User(); // Assume this is um objeto User válido
//        AuthResponse expectedResponse = new AuthResponse("generatedToken", LocalDateTime.now().plusHours(1));
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
//        when(userGateway.findUserByUsername(username)).thenReturn(user);
//        when(jwtUtil.generateToken(user)).thenReturn(expectedResponse);
//        AuthResponse actualResponse = authenticateUseCase.execute(command);
//        assertEquals(expectedResponse, actualResponse);
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//        verify(userGateway).findUserByUsername(username);
//        verify(jwtUtil).generateToken(user);
//    }
//
//    @Test
//    void givenInvalidCredentials_whenExecute_thenThrowBadCredentialsException() {
//        String username = "testUser";
//        String password = "wrongPassword";
//        AuthCommand command = new AuthCommand(username, password);
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenThrow(new BadCredentialsException("Invalid credentials"));
//        assertThrows(BadCredentialsException.class, () -> authenticateUseCase.execute(command));
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//        verifyNoInteractions(userGateway);
//        verifyNoInteractions(jwtUtil);
//    }
//
//    @Test
//    void givenNonexistentUser_whenExecute_thenThrowUserNotFoundException() {
//        String username = "nonexistentUser";
//        String password = "password123";
//        AuthCommand command = new AuthCommand(username, password);
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
//        when(userGateway.findUserByUsername(username)).thenThrow(new RuntimeException("User not found"));
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> authenticateUseCase.execute(command));
//        assertEquals("User not found", exception.getMessage());
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//        verify(userGateway).findUserByUsername(username);
//        verifyNoInteractions(jwtUtil);
//    }
//
}
