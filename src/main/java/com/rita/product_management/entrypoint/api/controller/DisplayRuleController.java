package com.rita.product_management.entrypoint.api.controller;

import com.rita.product_management.entrypoint.api.dto.request.*;
import com.rita.product_management.entrypoint.api.dto.response.DisplayRuleResponse;
import com.rita.product_management.entrypoint.api.dto.response.DisplayRulesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Display Rules API", description = "Endpoints for display rules management")
@RequestMapping(path = "/rules", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public interface DisplayRuleController {

    @PostMapping()
    @Operation(
            summary = "Create New Rule",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Display Rule Created",
                            content = @Content(
                                    schema = @Schema(implementation = DisplayRuleResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"hiddenFields\": [\"name\", \"active\"] }"
                                    )
                            ))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Display Rule Request",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateDisplayRuleRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid Request",
                                    summary = "Example of a valid request",
                                    value = "{ \"hiddenFields\": [\"name\", \"active\"] }"
                            )
                    )
            )
    )
    ResponseEntity<DisplayRuleResponse> createDisplayRule(@RequestBody @Valid CreateDisplayRuleRequest request);

    @PutMapping()
    @Operation(
            summary = "Update Rule",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Display Rule Updated",
                            content = @Content(
                                    schema = @Schema(implementation = DisplayRuleResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"hiddenFields\": [\"name\", \"active\"] }"
                                    )
                            ))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Display Rule Request",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UpdateCategoryRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid Request",
                                    summary = "Example of a valid request",
                                    value = "{ " +
                                            "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                            "\"hiddenFields\": [\"name\", \"active\"] }"
                            )
                    )
            )
    )
    ResponseEntity<DisplayRuleResponse> updateDisplayRule(@RequestBody @Valid UpdateDisplayRuleRequest request);

    @GetMapping("/{id}")
    @Operation(
            summary = "Find DisplayRule",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Search id",
                            required = true,
                            example = "12327b98-023a-4b33-9563-308684a61b3d"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "DisplayRule Found",
                            content = @Content(
                                    schema = @Schema(implementation = DisplayRuleResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"hiddenFields\": [\"name\", \"active\"] }"
                                    )
                            ))
            }
    )
    ResponseEntity<DisplayRuleResponse> findDisplayRule(@PathVariable("id") String id);

    @GetMapping()
    @Operation(
            summary = "List of rules",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "The page number (zero-based) to retrieve",
                            example = "0"
                    ),
                    @Parameter(
                            name = "size",
                            description = "The number of elements per page",
                            example = "20"
                    ),
                    @Parameter(
                            name = "sort",
                            description = "Sorting criteria in the format: property(,asc | desc). Default is name,asc",
                            example = "name,asc"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of Rules",
                            content = @Content(
                                    schema = @Schema(implementation = DisplayRulesResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"isActive\": true }"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "List of Rules Is Empty",
                            content = @Content(
                                    schema = @Schema()
                            )
                    )
            }
    )
    ResponseEntity<?> listCategories(
            @Parameter(hidden = true)
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable);

    @PatchMapping()
    @Operation(
            summary = "Switch a rule",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Display Rule Switched",
                            content = @Content(
                                    schema = @Schema()
                            ))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Switch Display Rule Request",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = SwitchDisplayRuleRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid Request",
                                    summary = "Example of a valid request",
                                    value = "{ " +
                                            "\"ids\": [\"12327b98-023a-4b33-9563-308684a61b3d\", \"0ae1e804-df33-47c6-bc2c-4b8671d96f88\"]" +
                                            "\"isActive\": true }"
                            )
                    )
            )
    )
    ResponseEntity<Void> switchDisplayRule(@RequestBody @Valid SwitchDisplayRuleRequest request);

    @DeleteMapping()
    @Operation(
            summary = "Delete a Rule",
            parameters = {
                    @Parameter(
                            name = "ids",
                            description = "List of ids for delete",
                            required = true,
                            example =  "[\"12327b98-023a-4b33-9563-308684a61b3d\"]"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Display Rules Deleted",
                            content = @Content(
                                    schema = @Schema()
                            ))
            }
    )
    ResponseEntity<Void> deleteDisplayRule(@RequestBody List<String> ids);

}
