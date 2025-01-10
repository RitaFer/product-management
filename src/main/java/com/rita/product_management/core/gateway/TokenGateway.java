package com.rita.product_management.core.gateway;

import com.rita.product_management.core.domain.Token;

import java.util.Optional;

public interface TokenGateway {

    void save(Token token);
    Token generateToken(String userId);
    Optional<Token> validateToken(String token);

}
