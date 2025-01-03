package com.rita.product_management.dataprovider.mapper;

import com.rita.product_management.core.domain.Token;
import com.rita.product_management.dataprovider.database.entity.TokenEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-02T20:46:00-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class TokenMapperImpl implements TokenMapper {

    @Override
    public Token fromEntityToModel(TokenEntity tokenEntity) {
        if ( tokenEntity == null ) {
            return null;
        }

        Token token = new Token();

        return token;
    }

    @Override
    public TokenEntity fromModelToEntity(Token token) {
        if ( token == null ) {
            return null;
        }

        TokenEntity tokenEntity = new TokenEntity();

        return tokenEntity;
    }
}
