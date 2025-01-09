package com.rita.product_management.entrypoint.api.controller;

import com.rita.product_management.entrypoint.api.dto.request.*;
import com.rita.product_management.entrypoint.api.dto.response.AccountResponse;
import com.rita.product_management.entrypoint.api.dto.response.AccountsResponse;
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

@Tag(name = "Accounts API", description = "Endpoints for accounts management")
@RequestMapping(path = "/accounts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public interface AccountController {

        @PostMapping()
        @Operation(
                summary = "Create Account",
                responses = {
                        @ApiResponse(
                                responseCode = "201",
                                description = "Account Created",
                                content = @Content(
                                        schema = @Schema(implementation = AccountResponse.class),
                                        examples = @ExampleObject(value =
                                                "{ " +
                                                        "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                        "\"username\": \"johndoe123\", " +
                                                        "\"name\": \"John Doe\", " +
                                                        "\"email\": \"john@gmail.com\", " +
                                                        "\"role\": \"ADMIN\" }"
                                        )
                                ))
                },
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        description = "Account Request",
                        required = true,
                        content = @Content(
                                schema = @Schema(implementation = CreateAccountRequest.class),
                                examples = @ExampleObject(
                                        name = "Valid Request",
                                        summary = "Example of a valid request",
                                        value = "{ " +
                                                "\"name\": \"John Doe\", " +
                                                "\"email\": \"john@gmail.com\", " +
                                                "\"role\": \"ADMIN\" }"
                                )
                        )
                )
        )
        ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid CreateAccountRequest request);

        @PutMapping()
        @Operation(
                summary = "Update Account",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Account Updated",
                                content = @Content(
                                        schema = @Schema(implementation = AccountsResponse.class),
                                        examples = @ExampleObject(value =
                                                "{ " +
                                                        "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                        "\"username\": \"johndoe123\", " +
                                                        "\"name\": \"John Doe\", " +
                                                        "\"email\": \"john@gmail.com\", " +
                                                        "\"role\": \"ADMIN\" }"
                                        )
                                ))
                },
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        description = "Account Request",
                        required = true,
                        content = @Content(
                                schema = @Schema(implementation = UpdateAccountRequest.class),
                                examples = @ExampleObject(
                                        name = "Valid Request",
                                        summary = "Example of a valid request",
                                        value = "{ " +
                                                "\"name\": \"John Doe\", " +
                                                "\"email\": \"john@gmail.com\", " +
                                                "\"role\": \"ADMIN\" }"
                                )
                        )
                )
        )
        ResponseEntity<AccountResponse> updateAccount(@RequestBody @Valid UpdateAccountRequest request);

    @GetMapping("/{id}")
    @Operation(
            summary = "Find Account",
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
                            description = "Account Found",
                            content = @Content(
                                    schema = @Schema(implementation = AccountResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"username\": \"johndoe123\", " +
                                                    "\"name\": \"John Doe\", " +
                                                    "\"email\": \"john@gmail.com\", " +
                                                    "\"role\": \"ADMIN\" }"
                                    )
                            ))
            }
    )
    ResponseEntity<AccountResponse> findAccount(@PathVariable("id") String id);

    @GetMapping()
        @Operation(
                summary = "List of Accounts",
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
                                description = "List of Accounts",
                                content = @Content(
                                        schema = @Schema(implementation = AccountsResponse.class),
                                        examples = @ExampleObject(value =
                                                "{ " +
                                                        "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                        "\"isActive\": true, " +
                                                        "\"name\": \"Dorflex\" }"
                                        )
                                )
                        ),
                        @ApiResponse(
                                responseCode = "204",
                                description = "List of Accounts Is Empty",
                                content = @Content(
                                        schema = @Schema()
                                )
                        )
                }
        )
        ResponseEntity<?> listAccounts(
                @Parameter(hidden = true)
                @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable);

        @PatchMapping()
        @Operation(
                summary = "Switch Accounts",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Accounts Switched",
                                content = @Content(
                                        schema = @Schema()
                                ))
                },
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        description = "Switch Accounts Request",
                        required = true,
                        content = @Content(
                                schema = @Schema(implementation = SwitchAccountRequest.class),
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
        ResponseEntity<Void> switchAccount(@RequestBody @Valid SwitchAccountRequest request);

        @DeleteMapping()
        @Operation(
                summary = "Delete Accounts",
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
                                description = "Accounts Deleted",
                                content = @Content(
                                        schema = @Schema()
                                ))
                }
        )
        ResponseEntity<Void> deleteAccount(@RequestBody List<String> ids);

}
