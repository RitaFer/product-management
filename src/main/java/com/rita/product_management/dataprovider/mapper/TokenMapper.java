package com.rita.product_management.dataprovider.mapper;

import com.rita.product_management.core.domain.Token;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.dataprovider.database.entity.TokenEntity;
import com.rita.product_management.dataprovider.database.entity.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TokenMapper {
    Token fromEntityToModel(TokenEntity tokenEntity);
    TokenEntity fromModelToEntity(Token token);
}


