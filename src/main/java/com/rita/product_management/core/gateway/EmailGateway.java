package com.rita.product_management.core.gateway;

import com.rita.product_management.core.domain.User;

public interface EmailGateway {

    void sendToken(String to, String token);
    void sendUpdateNotification(String to, String updateInfo);
    void sendDeleteNotification(String to);
    void sendCreateAccountNotification(User accountCreated);

}
