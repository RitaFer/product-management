package com.rita.product_management.dataprovider.database.specification;

import com.rita.product_management.dataprovider.database.entity.AuditLogEntity;
import com.rita.product_management.dataprovider.database.entity.UserEntity;
import com.rita.product_management.entrypoint.api.dto.filters.AuditFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuditLogSpecification extends BaseSpecification<AuditLogEntity, AuditFilter> {

    public Join<UserEntity, AuditLogEntity> modifiedByJoin;


    @Override
    public Specification<AuditLogEntity> getFilter(AuditFilter filter) {
        return (root, query, cb) -> {
            modifiedByJoin = root.join(Field.modifiedBy.name(), JoinType.LEFT);

            return Specification.where(attributeEquals(AuditLogSpecification.Field.id.name(), filter.getId()))
                    .and(attributeEquals(Field.action.name(), filter.getAction()))
                    .and(attributeContains(Field.entityName.name(), filter.getEntityName()))
                    .and(attributeEquals(Field.entityId.name(), filter.getEntityId()))
                    .and(attributeEquals(Field.field.name(), filter.getField()))
                    .and(attributeEquals(Field.oldValue.name(), filter.getOldValue()))
                    .and(attributeEquals(Field.newValue.name(), filter.getNewValue()))
                    .and(attributeEquals(modifiedByJoin, AuditLogSpecification.Field.id.name(), filter.getModifiedBy()))
                    .and(attributeBetweenDates(Field.modifiedDate.name(), filter.getModifiedDateInitial(), filter.getModifiedDateFinal()))
                    .toPredicate(root, query, cb);
        };
    }

    private enum Field {
        id,
        action,
        entityName,
        entityId,
        field,
        oldValue,
        newValue,
        modifiedBy,
        modifiedDate
    }
}
