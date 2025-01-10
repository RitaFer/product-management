package com.rita.product_management.entrypoint.api.controller;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Audit API", description = "Endpoints for get audit logs")
@RequestMapping(path = "/logs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public interface AuditController {

    @GetMapping("/{id}")
    @Operation(
            summary = "Find AuditLog",
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
                            ))
            }
    )
    ResponseEntity<AuditLogResponse> findAudit(@PathVariable("id") String id);

    @GetMapping()
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
                            description = "Sorting criteria in the format: property(,asc | desc). Default is name,asc",
                            example = "name,asc"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filters to apply when generating the report",
                    required = false,
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
            @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestBody(required = false) AuditFilter filter);
}
