package com.rita.product_management.entrypoint.api.controller.impl;

import com.rita.product_management.core.usecase.user.ChangePasswordUseCase;
import com.rita.product_management.core.usecase.user.SendTokenUseCase;
import com.rita.product_management.core.usecase.user.command.AuthCommand;
import com.rita.product_management.core.usecase.user.command.ChangePasswordCommand;
import com.rita.product_management.core.usecase.user.command.SendTokenCommand;
import com.rita.product_management.entrypoint.api.controller.AuthController;
import com.rita.product_management.entrypoint.api.dto.request.AuthRequest;
import com.rita.product_management.entrypoint.api.dto.request.ChangePasswordRequest;
import com.rita.product_management.entrypoint.api.dto.response.AuthResponse;
import com.rita.product_management.core.usecase.user.AuthenticateUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthenticateUseCase authenticateUseCase;
    private final SendTokenUseCase sendTokenUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest request) {
        log.debug("Login attempt for username: {}", request.username());
        AuthResponse response = authenticateUseCase.execute(new AuthCommand(request.username(), request.password()));
        log.debug("Login successful for username: {}", request.username());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<Void> sendResetToken(String username) {
        log.debug("Forget password requested for username: {}", username);
        sendTokenUseCase.execute(new SendTokenCommand(username));
        log.debug("Token sent for username: {}", username);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> changePassword(ChangePasswordRequest request) {
        log.debug("Change password attempt for token: {}", request.token());
        changePasswordUseCase.execute(new ChangePasswordCommand(request.token(), request.newPassword(), request.confirmNewPassword()));
        log.debug("Password changed successfully for token: {}", request.token());
        return ResponseEntity.noContent().build();
    }

}
