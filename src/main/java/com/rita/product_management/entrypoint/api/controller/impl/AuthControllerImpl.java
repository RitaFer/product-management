package com.rita.product_management.entrypoint.api.controller.impl;

import com.rita.product_management.core.usecase.user.SendTokenUseCase;
import com.rita.product_management.core.usecase.user.command.SendTokenCommand;
import com.rita.product_management.entrypoint.api.controller.AuthController;
import com.rita.product_management.entrypoint.api.dto.request.AuthRequest;
import com.rita.product_management.entrypoint.api.dto.request.ChangePassword;
import com.rita.product_management.entrypoint.api.dto.response.AuthResponse;
import com.rita.product_management.core.usecase.user.AuthenticateUseCase;
import com.rita.product_management.entrypoint.api.mapper.AuthMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthControllerImpl implements AuthController {

    private final AuthMapper authMapper;
    private final AuthenticateUseCase authenticateUseCase;
    private final SendTokenUseCase sendTokenUseCase;

    public AuthControllerImpl(AuthenticateUseCase authenticateUseCase, AuthMapper authMapper, SendTokenUseCase sendTokenUseCase) {
        this.authMapper = authMapper;
        this.authenticateUseCase = authenticateUseCase;
        this.sendTokenUseCase = sendTokenUseCase;
    }

    @Override
    public ResponseEntity<AuthResponse> doLogin(AuthRequest request) {
        AuthResponse response = authenticateUseCase.execute(authMapper.fromRequestToCommand(request));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> forgetPassword(String username) {
        sendTokenUseCase.execute(new SendTokenCommand(username));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> changePassword(ChangePassword username) {
        return ResponseEntity.ok().build();
    }

}
