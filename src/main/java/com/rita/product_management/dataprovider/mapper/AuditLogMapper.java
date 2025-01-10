package com.rita.product_management.dataprovider.mapper;

import com.rita.product_management.core.domain.AuditLog;
import com.rita.product_management.dataprovider.database.entity.AuditLogEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AuditLogMapper {

    AuditLog fromEntityToModel (AuditLogEntity auditLogEntity);
    AuditLogEntity fromModelToEntity (AuditLog auditLog);

}
