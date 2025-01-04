package com.rita.product_management.dataprovider.mapper;

import com.rita.product_management.core.domain.Token;
import com.rita.product_management.dataprovider.database.entity.TokenEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-03T20:12:04-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class TokenMapperImpl implements TokenMapper {

    @Override
    public Token fromEntityToModel(TokenEntity tokenEntity) {
        if ( tokenEntity == null ) {
            return null;
        }

        Token.TokenBuilder token = Token.builder();

        token.id( tokenEntity.getId() );
        token.token( tokenEntity.getToken() );
        token.userId( tokenEntity.getUserId() );
        token.tokenUsed( tokenEntity.getTokenUsed() );
        token.createdAt( tokenEntity.getCreatedAt() );
        token.expiredAt( tokenEntity.getExpiredAt() );

        return token.build();
    }

    @Override
    public TokenEntity fromModelToEntity(Token token) {
        if ( token == null ) {
            return null;
        }

        TokenEntity.TokenEntityBuilder tokenEntity = TokenEntity.builder();

        tokenEntity.id( token.getId() );
        tokenEntity.token( token.getToken() );
        tokenEntity.userId( token.getUserId() );
        tokenEntity.tokenUsed( token.getTokenUsed() );
        tokenEntity.createdAt( token.getCreatedAt() );
        tokenEntity.expiredAt( token.getExpiredAt() );

        return tokenEntity.build();
    }

    @Override
    public String map(Token value) {
        if ( value == null ) {
            return null;
        }

        String string = new String();

        return string;
    }
}
