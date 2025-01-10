package com.rita.product_management.dataprovider.database.gateway.impl;

import com.rita.product_management.core.common.exception.AuditNotFoundException;
import com.rita.product_management.core.domain.AuditLog;
import com.rita.product_management.core.gateway.AuditLogGateway;
import com.rita.product_management.dataprovider.database.entity.AuditLogEntity;
import com.rita.product_management.dataprovider.database.repository.AuditLogRepository;
import com.rita.product_management.dataprovider.database.specification.AuditLogSpecification;
import com.rita.product_management.dataprovider.mapper.AuditLogMapper;
import com.rita.product_management.entrypoint.api.dto.filters.AuditFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuditLogGatewayImpl implements AuditLogGateway {

    private final AuditLogMapper auditLogMapper;
    private final AuditLogRepository auditLogRepository;
    private final AuditLogSpecification auditLogSpecification;

    @Override
    public AuditLog save(AuditLog auditLog) {
        log.debug("Saving audit with details: [{}]", auditLog);
        AuditLog savedAudit = auditLogMapper.fromEntityToModel(
                auditLogRepository.save(auditLogMapper.fromModelToEntity(auditLog))
        );
        log.debug("Audit saved successfully with ID: [{}]", savedAudit.getId());
        return savedAudit;
    }

    @Override
    public AuditLog findById(String id) {
        return auditLogRepository.findById(id)
                .map(auditLogMapper::fromEntityToModel)
                .orElseThrow(() -> new AuditNotFoundException(id));
    }

    @Override
    public Page<AuditLog> findAllWithFilters(Pageable pageable, AuditFilter filter) {
        log.debug("Fetching all audits with pagination: [{}]", pageable);
        Specification<AuditLogEntity> specification = auditLogSpecification.getFilter(filter);
        Page<AuditLog> audits = auditLogRepository.findAll(specification, pageable).map(auditLogMapper::fromEntityToModel);
        log.debug("Fetched [{}] audits.", audits.getTotalElements());
        return audits;
    }

}
