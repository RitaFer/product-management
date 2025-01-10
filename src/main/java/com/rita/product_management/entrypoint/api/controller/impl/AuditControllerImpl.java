package com.rita.product_management.entrypoint.api.controller.impl;

import com.rita.product_management.core.usecase.audit_log.GetAuditListUseCase;
import com.rita.product_management.core.usecase.audit_log.GetAuditUseCase;
import com.rita.product_management.core.usecase.audit_log.command.GetAuditLogCommand;
import com.rita.product_management.core.usecase.audit_log.command.GetAuditLogListCommand;
import com.rita.product_management.entrypoint.api.controller.AuditController;
import com.rita.product_management.entrypoint.api.dto.filters.AuditFilter;
import com.rita.product_management.entrypoint.api.dto.response.AuditLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuditControllerImpl implements AuditController {

    private final GetAuditUseCase getAuditLogUseCase;
    private final GetAuditListUseCase getAuditLogReportUseCase;

    @Override
    public ResponseEntity<AuditLogResponse> findAudit(String id) {
        AuditLogResponse response = getAuditLogUseCase.execute(new GetAuditLogCommand(id));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> listAuditLogs(Pageable pageable, AuditFilter filter) {
        Page<?> response = getAuditLogReportUseCase.execute(new GetAuditLogListCommand(pageable, filter));
        if (response.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(response);
        }
    }

}
