package com.rita.product_management.core.gateway;

import com.rita.product_management.core.domain.AuditLog;
import com.rita.product_management.entrypoint.api.dto.filters.AuditFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditLogGateway {

    AuditLog save(final AuditLog auditLog);
    AuditLog findById(final String id);
    Page<AuditLog> findAllWithFilters(final Pageable pageable, final AuditFilter filter);

}
