package com.rita.product_management.core.gateway;

import com.rita.product_management.core.domain.Token;

public interface TokenGateway {
    Token generateToken(String userId);
    Boolean validateToken(String token);
}
