package com.rita.product_management.core.gateway;

import com.rita.product_management.core.domain.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditLogGateway {
    AuditLog save(final AuditLog auditLog);
    Page<AuditLog> findAll(final Pageable pageable);
}
