package com.rita.product_management.entrypoint.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rita.product_management.entrypoint.api.config.ErrorMessage;
import com.rita.product_management.entrypoint.api.dto.request.AuthRequest;
import com.rita.product_management.entrypoint.api.dto.request.ChangePasswordRequest;
import com.rita.product_management.entrypoint.api.dto.response.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
public interface AuthController {

    @Operation(summary = "Login")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Login created", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    ResponseEntity<AuthResponse> doLogin(@RequestBody @Valid AuthRequest request);

    @Operation(summary = "Forget Password")
    @PostMapping(path = "/forget-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Requested token for reset password", content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    ResponseEntity<Void> forgetPassword(@RequestParam @Valid String username) throws JsonProcessingException;

    @Operation(summary = "Change Password")
    @PostMapping(path = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "New password saved", content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "403", description = "Invalid Code", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request);

}
