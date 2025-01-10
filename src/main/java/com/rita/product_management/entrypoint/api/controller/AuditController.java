package com.rita.product_management.entrypoint.api.controller;

import com.rita.product_management.entrypoint.api.config.ErrorMessage;
import com.rita.product_management.entrypoint.api.dto.filters.AuditFilter;
import com.rita.product_management.entrypoint.api.dto.response.AuditLogResponse;
import com.rita.product_management.entrypoint.api.dto.response.AuditLogsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/logs")
@Tag(name = "Audit API", description = "Endpoints for get audit logs")
public interface AuditController {

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Find AuditLog",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Search id",
                            required = true,
                            example = "12327b98-023a-4b33-9563-308684a61b3d"
                    ),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "AuditLog Found",
                            content = @Content(
                                    schema = @Schema(implementation = AuditLogResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"action\": \"UPDATE\", " +
                                                    "\"entityName\": \"Product\", " +
                                                    "\"entityId\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"field\": \"name\", " +
                                                    "\"oldValue\": \"Old Product Name\", " +
                                                    "\"newValue\": \"New Product Name\", " +
                                                    "\"modifiedBy\": { " +
                                                    "   \"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "   \"name\": \"Admin User\" " +
                                                    "}, " +
                                                    "\"modifiedDate\": \"2025-01-10T15:30:00\" " +
                                                    "}"
                                    )
                            )),
                    @ApiResponse(
                            responseCode = "404",
                            description = "AuditLog not found",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{ \"status\": \"404\", \"message\": \"AuditLog with id = 99b5-69f7e8c4a3cc, not found.\", \"timestamp\": \"2025-01-09 13:30:01\" }")
                            ))
            }
    )
    ResponseEntity<AuditLogResponse> findAudit(@PathVariable("id") String id);

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "List of AuditLogs",
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
                            description = "Sorting criteria in the format: property(,asc | desc). Default is id,asc",
                            example = "name,asc"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filters to apply when generating the report",
                    content = @Content(
                            schema = @Schema(implementation = AuditFilter.class),
                            examples = @ExampleObject(value =
                                    "{ " +
                                            "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                            "\"action\": \"UPDATE\", " +
                                            "\"entityName\": \"Product\", " +
                                            "\"entityId\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                            "\"field\": \"name\", " +
                                            "\"oldValue\": \"Old Product Name\", " +
                                            "\"newValue\": \"New Product Name\", " +
                                            "\"modifiedBy\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                            "\"modifiedDatInitial\": \"2025-01-10T00:00:00\", " +
                                            "\"modifiedDateFinal\": \"2025-01-10T23:59:59\" " +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of AuditLogs",
                            content = @Content(
                                    schema = @Schema(implementation = AuditLogsResponse.class),
                                    examples = @ExampleObject(value =
                                            "{ " +
                                                    "\"id\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"action\": \"UPDATE\", " +
                                                    "\"entityName\": \"Product\", " +
                                                    "\"entityId\": \"283894f9-1610-49f5-815b-63f3efafb253\", " +
                                                    "\"field\": \"name\", " +
                                                    "\"modifiedDate\": \"2025-01-10T15:30:00\" " +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "List of AuditLogs Is Empty",
                            content = @Content(
                                    schema = @Schema()
                            )
                    )
            }
    )
    ResponseEntity<?> listAuditLogs(
            @Parameter(hidden = true)
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestBody(required = false) AuditFilter filter);
}
