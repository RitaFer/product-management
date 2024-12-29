package com.rita.product_management.infrastructure.configuration;

import com.sendgrid.SendGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

//@Configuration
public class SendGridConfig {

//    TODO: enable when have api key
//    @Value("${sendgrid.api-key}")
//    private String sendGridApiKey;
//
//    @Bean
//    public SendGrid sendGrid() {
//        return new SendGrid(sendGridApiKey);
//    }
}
