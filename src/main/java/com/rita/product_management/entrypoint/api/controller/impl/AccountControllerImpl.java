package com.rita.product_management.entrypoint.api.controller.impl;

import com.rita.product_management.entrypoint.api.controller.AccountController;
import com.rita.product_management.entrypoint.api.controller.AuthController;
import com.rita.product_management.entrypoint.api.dto.request.CreateAccount;
import com.rita.product_management.entrypoint.api.dto.request.UpdateAccount;
import com.rita.product_management.entrypoint.api.dto.response.Account;
import com.rita.product_management.entrypoint.api.dto.response.Accounts;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountControllerImpl implements AccountController {

    @Override
    public ResponseEntity<Account> createAccount(CreateAccount request) {
        return null;
    }

    @Override
    public ResponseEntity<Account> updateAccount(UpdateAccount request) {
        return null;
    }

    @Override
    public ResponseEntity<Page<Accounts>> listAccount() {
        return null;
    }

    @Override
    public ResponseEntity<Void> switchAccount(List<String> request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteAccount(List<String> request) {
        return null;
    }
}
