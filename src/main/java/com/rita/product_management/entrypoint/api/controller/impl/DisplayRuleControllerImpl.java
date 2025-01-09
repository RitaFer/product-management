package com.rita.product_management.entrypoint.api.controller.impl;

import com.rita.product_management.core.usecase.display_rule.*;
import com.rita.product_management.core.usecase.display_rule.command.*;
import com.rita.product_management.entrypoint.api.controller.DisplayRuleController;
import com.rita.product_management.entrypoint.api.dto.request.CreateDisplayRuleRequest;
import com.rita.product_management.entrypoint.api.dto.request.SwitchDisplayRuleRequest;
import com.rita.product_management.entrypoint.api.dto.request.UpdateDisplayRuleRequest;
import com.rita.product_management.entrypoint.api.dto.response.DisplayRuleResponse;
import com.rita.product_management.entrypoint.api.dto.response.DisplayRulesResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class DisplayRuleControllerImpl implements DisplayRuleController {

    private final CreateDisplayRuleUseCase createDisplayRuleUseCase;
    private final UpdateDisplayRuleUseCase updateDisplayRuleUseCase;
    private final GetDisplayRuleUseCase getDisplayRuleUseCase;
    private final GetDisplayRuleListUseCase getDisplayRuleListUseCase;
    private final SwitchDisplayRuleUseCase switchDisplayRuleUseCase;
    private final DeleteDisplayRuleUseCase deleteDisplayRuleUseCase;


    @Override
    public ResponseEntity<DisplayRuleResponse> createDisplayRule(@Valid CreateDisplayRuleRequest request) {
        DisplayRuleResponse response = createDisplayRuleUseCase.execute(new CreateDisplayRuleCommand(request.hiddenFields()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<DisplayRuleResponse> updateDisplayRule(@Valid UpdateDisplayRuleRequest request) {
        DisplayRuleResponse response = updateDisplayRuleUseCase.execute(new UpdateDisplayRuleCommand(request.id(), request.hiddenFields()));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<DisplayRuleResponse> findDisplayRule(String id) {
        DisplayRuleResponse response = getDisplayRuleUseCase.execute(new GetDisplayRuleCommand(id));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> listCategories(Pageable pageable) {
        Page<DisplayRulesResponse> response = getDisplayRuleListUseCase.execute(new GetDisplayRuleListCommand(pageable));
        if (response.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<Void> switchDisplayRule(@Valid SwitchDisplayRuleRequest request) {
        switchDisplayRuleUseCase.execute(new SwitchDisplayRuleCommand(request.id(), request.isActive()));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteDisplayRule(List<String> ids) {
        deleteDisplayRuleUseCase.execute(new DeleteDisplayRuleCommand(ids));
        return ResponseEntity.ok().build();
    }

}
