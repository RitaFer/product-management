package com.rita.product_management.core.gateway;

public interface EmailGateway {
    void sendToken(String to, String token);
}
