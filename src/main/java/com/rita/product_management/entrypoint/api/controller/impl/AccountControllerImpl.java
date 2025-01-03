package com.rita.product_management.entrypoint.api.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rita.product_management.core.usecase.account.*;
import com.rita.product_management.core.usecase.account.command.*;
import com.rita.product_management.entrypoint.api.controller.AccountController;
import com.rita.product_management.entrypoint.api.dto.request.CreateAccountRequest;
import com.rita.product_management.entrypoint.api.dto.request.SwitchAccountRequest;
import com.rita.product_management.entrypoint.api.dto.request.UpdateAccountRequest;
import com.rita.product_management.entrypoint.api.dto.response.AccountResponse;
import com.rita.product_management.entrypoint.api.dto.response.AccountsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountControllerImpl implements AccountController {

    private final SwitchAccountUseCase switchAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;
    private final GetAccountListUseCase getAccountListUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;

    public AccountControllerImpl(SwitchAccountUseCase switchAccountUseCase, DeleteAccountUseCase deleteAccountUseCase, GetAccountListUseCase getAccountListUseCase, CreateAccountUseCase createAccountUseCase, UpdateAccountUseCase updateAccountUseCase) {
        this.switchAccountUseCase = switchAccountUseCase;
        this.deleteAccountUseCase = deleteAccountUseCase;
        this.getAccountListUseCase = getAccountListUseCase;
        this.createAccountUseCase = createAccountUseCase;
        this.updateAccountUseCase = updateAccountUseCase;
    }

    @Override
    public ResponseEntity<AccountResponse> createAccount(CreateAccountRequest request) throws JsonProcessingException {
        AccountResponse response = createAccountUseCase.execute(new CreateAccountCommand(request.name(), request.email(), request.role()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<AccountResponse> updateAccount(UpdateAccountRequest request) throws JsonProcessingException {
        AccountResponse response = updateAccountUseCase.execute(new UpdateAccountCommand(request.id(), request.name(), request.email(), request.role()));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> listAccount(Pageable pageable) {
        Page<AccountsResponse> response = getAccountListUseCase.execute(new GetAccountListCommand(pageable));
        if (response.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<Void> switchAccount(SwitchAccountRequest request) throws JsonProcessingException {
        switchAccountUseCase.execute(new SwitchAccountCommand(request.ids(), request.active()));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteAccount(List<String> ids) throws JsonProcessingException {
        deleteAccountUseCase.execute(new DeleteAccountCommand(ids));
        return ResponseEntity.ok().build();
    }
}
