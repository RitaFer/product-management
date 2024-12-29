package com.rita.product_management.entrypoint.api.controller;

import com.rita.product_management.entrypoint.api.config.ErrorMessage;
import com.rita.product_management.entrypoint.api.dto.request.CreateAccount;
import com.rita.product_management.entrypoint.api.dto.request.UpdateAccount;
import com.rita.product_management.entrypoint.api.dto.response.Account;
import com.rita.product_management.entrypoint.api.dto.response.Accounts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/accounts")
public interface AccountController {

        @Operation(summary = "Create new account")
        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        @ApiResponse(responseCode = "201", description = "Account created", content = @Content(schema = @Schema(implementation = Account.class)))
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        ResponseEntity<Account> createAccount(@RequestBody @Valid CreateAccount request);

        @Operation(summary = "Update an account")
        @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        @ApiResponse(responseCode = "200", description = "Account updated", content = @Content(schema = @Schema(implementation = Account.class)))
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        ResponseEntity<Account> updateAccount(@RequestBody @Valid UpdateAccount request);

        @Operation(summary = "List of accounts")
        @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        @ApiResponse(responseCode = "200", description = "List of accounts", content = @Content(schema = @Schema(implementation = Account.class)))
        @ApiResponse(responseCode = "204", description = "List of accounts is empty", content = @Content(schema = @Schema()))
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        ResponseEntity<Page<Accounts>> listAccount();

        @Operation(summary = "Switch an account")
        @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        @ApiResponse(responseCode = "200", description = "Account switched", content = @Content(schema = @Schema()))
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        ResponseEntity<Void> switchAccount(@RequestBody @Valid List<String> request);

        @Operation(summary = "Delete an account")
        @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        @ApiResponse(responseCode = "200", description = "Account deleted", content = @Content(schema = @Schema()))
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
        ResponseEntity<Void> deleteAccount(@RequestBody @Valid List<String> request);

}
