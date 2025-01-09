package com.rita.product_management.dataprovider.mapper;

import com.rita.product_management.core.domain.DisplayRule;
import com.rita.product_management.dataprovider.database.entity.DisplayRuleEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DisplayRuleMapper {

    DisplayRule fromEntityToModel (DisplayRuleEntity displayRuleEntity);
    DisplayRuleEntity fromModelToEntity (DisplayRule displayRule);

}
