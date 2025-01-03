package com.rita.product_management.entrypoint.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rita.product_management.entrypoint.api.config.ErrorMessage;
import com.rita.product_management.entrypoint.api.dto.request.CreateAccountRequest;
import com.rita.product_management.entrypoint.api.dto.request.SwitchAccountRequest;
import com.rita.product_management.entrypoint.api.dto.request.UpdateAccountRequest;
import com.rita.product_management.entrypoint.api.dto.response.AccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/accounts")
public interface AccountController {

        @Operation(summary = "Create new account")
        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        @ApiResponse(responseCode = "201", description = "Account created", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid CreateAccountRequest request) throws JsonProcessingException;

        @Operation(summary = "Update an account")
        @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        @ApiResponse(responseCode = "200", description = "Account updated", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        ResponseEntity<AccountResponse> updateAccount(@RequestBody @Valid UpdateAccountRequest request) throws JsonProcessingException;

        @Operation(summary = "List of accounts")
        @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        @ApiResponse(responseCode = "200", description = "List of accounts", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
        @ApiResponse(responseCode = "204", description = "List of accounts is empty", content = @Content(schema = @Schema()))
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        ResponseEntity<?> listAccount(
                @Parameter(description = "Pageable parameters")
                @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable);

        @Operation(summary = "Switch an account")
        @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        @ApiResponse(responseCode = "200", description = "Account switched", content = @Content(schema = @Schema()))
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        ResponseEntity<Void> switchAccount(@RequestBody @Valid SwitchAccountRequest request) throws JsonProcessingException;

        @Operation(summary = "Delete an account")
        @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        @ApiResponse(responseCode = "200", description = "Account deleted", content = @Content(schema = @Schema()))
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        ResponseEntity<Void> deleteAccount(@RequestBody List<String> ids) throws JsonProcessingException;

}
