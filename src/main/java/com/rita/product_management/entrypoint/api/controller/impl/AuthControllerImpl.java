package com.rita.product_management.entrypoint.api.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthenticateUseCase authenticateUseCase;
    private final SendTokenUseCase sendTokenUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    @Override
    public ResponseEntity<AuthResponse> doLogin(AuthRequest request) {
        AuthResponse response = authenticateUseCase.execute(new AuthCommand(request.username(), request.password()));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> forgetPassword(String username) throws JsonProcessingException {
        sendTokenUseCase.execute(new SendTokenCommand(username));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> changePassword(ChangePasswordRequest request) {
        changePasswordUseCase.execute(new ChangePasswordCommand(request.token(), request.password1(), request.password2()));
        return ResponseEntity.ok().build();
    }

}
