package com.rita.product_management.dataprovider.mapper;

import com.rita.product_management.core.domain.Token;
import com.rita.product_management.dataprovider.database.entity.TokenEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TokenMapper {

    Token fromEntityToModel(TokenEntity tokenEntity);
    TokenEntity fromModelToEntity(Token token);
    String map(Token value);

}
