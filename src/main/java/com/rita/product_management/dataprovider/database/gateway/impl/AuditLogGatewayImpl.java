package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.domain.AuditLog;
import com.rita.product_management.core.gateway.AuditLogGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuditLogGatewayImpl implements AuditLogGateway {
    @Override
    public AuditLog save(AuditLog auditLog) {
        return null;
    }

    @Override
    public Page<AuditLog> findAll(Pageable pageable) {
        return null;
    }
}
