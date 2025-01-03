package com.rita.product_management.core.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rita.product_management.core.domain.user.User;

public interface EmailGateway {
    void sendToken(String to, String token) throws JsonProcessingException;
    void sendUpdateNotification(String to, String updateInfo) throws JsonProcessingException;
    void sendDeleteNotification(String to) throws JsonProcessingException;
    void sendCreateAccountNotification(User accountCreated) throws JsonProcessingException;
}
