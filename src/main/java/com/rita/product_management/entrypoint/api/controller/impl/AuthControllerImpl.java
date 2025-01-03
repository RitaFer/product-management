package com.rita.product_management.entrypoint.api.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rita.product_management.core.usecase.user.ChangePasswordUseCase;
import com.rita.product_management.core.usecase.user.SendTokenUseCase;
import com.rita.product_management.core.usecase.user.command.ChangePasswordCommand;
import com.rita.product_management.core.usecase.user.command.SendTokenCommand;
import com.rita.product_management.entrypoint.api.controller.AuthController;
import com.rita.product_management.entrypoint.api.dto.request.AuthRequest;
import com.rita.product_management.entrypoint.api.dto.request.ChangePasswordRequest;
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
    private final ChangePasswordUseCase changePasswordUseCase;

    public AuthControllerImpl(AuthenticateUseCase authenticateUseCase, AuthMapper authMapper, SendTokenUseCase sendTokenUseCase, ChangePasswordUseCase changePasswordUseCase) {
        this.authMapper = authMapper;
        this.authenticateUseCase = authenticateUseCase;
        this.sendTokenUseCase = sendTokenUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
    }

    @Override
    public ResponseEntity<AuthResponse> doLogin(AuthRequest request) {
        AuthResponse response = authenticateUseCase.execute(authMapper.fromRequestToCommand(request));
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
