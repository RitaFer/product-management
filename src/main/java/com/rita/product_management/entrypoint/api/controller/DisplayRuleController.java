package com.rita.product_management.entrypoint.api.controller;

import com.rita.product_management.entrypoint.api.config.ErrorMessage;
import com.rita.product_management.entrypoint.api.dto.request.CreateDisplayRuleRequest;
import com.rita.product_management.entrypoint.api.dto.request.SwitchDisplayRuleRequest;
import com.rita.product_management.entrypoint.api.dto.request.UpdateDisplayRuleRequest;
import com.rita.product_management.entrypoint.api.dto.response.DisplayRuleResponse;
import com.rita.product_management.entrypoint.api.dto.response.DisplayRulesResponse;
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

@RequestMapping("/rules")
public interface DisplayRuleController {

    @Operation(summary = "Create new rule")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", description = "Rule created", content = @Content(schema = @Schema(implementation = DisplayRuleResponse.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    ResponseEntity<DisplayRuleResponse> createDisplayRule(@RequestBody @Valid CreateDisplayRuleRequest request);

    @Operation(summary = "Update a category")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "DisplayRule updated", content = @Content(schema = @Schema(implementation = DisplayRuleResponse.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    ResponseEntity<DisplayRuleResponse> updateDisplayRule(@RequestBody @Valid UpdateDisplayRuleRequest request);

    @Operation(summary = "List of rules")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "List of rules", content = @Content(schema = @Schema(implementation = DisplayRulesResponse.class)))
    @ApiResponse(responseCode = "204", description = "List of rules is empty", content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    ResponseEntity<?> listCategories(
            @Parameter(description = "Pageable parameters")
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable);

    @Operation(summary = "Switch a rule")
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "DisplayRule switched", content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    ResponseEntity<Void> switchDisplayRule(@RequestBody @Valid SwitchDisplayRuleRequest request);

    @Operation(summary = "Delete a rule")
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "DisplayRule deleted", content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    ResponseEntity<Void> deleteDisplayRule(@RequestBody List<String> ids);

}
