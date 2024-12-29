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

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "role", target = "role")
    Token fromEntityToModel(TokenEntity tokenEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "role", target = "role")
    TokenEntity fromModelToEntity(Token token);
}


