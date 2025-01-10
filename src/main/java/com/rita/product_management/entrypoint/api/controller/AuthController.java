package com.rita.product_management.entrypoint.api.controller;

import com.rita.product_management.entrypoint.api.config.ErrorMessage;
import com.rita.product_management.entrypoint.api.dto.request.AuthRequest;
import com.rita.product_management.entrypoint.api.dto.request.ChangePasswordRequest;
import com.rita.product_management.entrypoint.api.dto.response.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/auth")
@Tag(name = "Authentication API", description = "Endpoints related to authentication and user management")
public interface AuthController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Login",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Login created",
                            content = @Content(
                                    schema = @Schema(implementation = AuthResponse.class),
                                    examples = @ExampleObject(value = "{ \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\", \"expiration\": \"2025-01-09 13:30:01\" }")
                            )),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Invalid Information Provided",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{ \"status\": \"403\", \"message\": \"Invalid username or password\", \"timestamp\": \"2025-01-09 13:30:01\" }")
                            ))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login request",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AuthRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid Login Request",
                                    summary = "Example of a valid login request",
                                    value = "{ \"username\": \"johndoe123\", \"password\": \"Password@123\" }"
                            )
                    )
            )
    )
    ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request);

    @PostMapping(path = "/forget-password")
    @Operation(
            summary = "Send Reset Password Token",
            parameters = {
                    @Parameter(
                            name = "username",
                            description = "Username of the user requesting a password reset",
                            required = true,
                            example = "johndoe123"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Requested token for reset password"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{ \"status\": \"404\", \"message\": \"User not found\", \"timestamp\": \"2025-01-09 13:30:01\" }")
                            ))
            }
    )
    ResponseEntity<Void> sendResetToken(@RequestParam @NotNull(message = "cannot be null") String username);

    @PostMapping(path = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Change Password",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "New password saved"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Invalid Code",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{ \"status\": \"403\", \"message\": \"Invalid token provided\", \"timestamp\": \"2025-01-09 13:30:01\" }")
                            ))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Change password payload",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ChangePasswordRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid Change Password Request",
                                    summary = "Example of a valid change password request",
                                    value = "{ \"token\": \"A2xj09\", \"newPassword\": \"NewPassword@123\", \"confirmNewPassword\": \"NewPassword@123\" }"
                            )
                    )
            )
    )
    ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request);

}
