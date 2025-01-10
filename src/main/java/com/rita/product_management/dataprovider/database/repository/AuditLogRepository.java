package com.rita.product_management.dataprovider.database.repository;

import com.rita.product_management.dataprovider.database.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, String>, JpaSpecificationExecutor<AuditLogEntity> {

}
