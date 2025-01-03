package com.rita.product_management.infrastructure.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmailGatewayImpl implements EmailGateway {

    @Value("${email-server.key:}")
    private String KEY;

    @Override
    public void sendToken(String to, String token) throws JsonProcessingException {
        Map<String, Object> jsonBody = createJsonBodyHeader();
        Map<String, String> recipient = new HashMap<>();
        recipient.put("email", to);

        jsonBody.put("to", List.of(recipient));
        jsonBody.put("subject", "New code for reset password");
        jsonBody.put("text", "Your code is: \""+token+"\"");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(jsonBody);
        createEmailRequest(json);
    }

    @Override
    public void sendUpdateNotification(String to, String updateInfo) throws JsonProcessingException {
        Map<String, Object> jsonBody = createJsonBodyHeader();
        Map<String, String> recipient = new HashMap<>();
        recipient.put("email", to);

        jsonBody.put("to", List.of(recipient));
        jsonBody.put("subject", "New Update In Your Account");
        jsonBody.put("text", "Your code is: \""+updateInfo+"\"");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(jsonBody);
        createEmailRequest(json);
    }

    @Override
    public void sendDeleteNotification(String to) throws JsonProcessingException {
        Map<String, Object> jsonBody = createJsonBodyHeader();
        Map<String, String> recipient = new HashMap<>();
        recipient.put("email", to);

        jsonBody.put("to", List.of(recipient));
        jsonBody.put("subject", "Your Account Was Deleted");
        jsonBody.put("text", "Your account was deleted from our system, any questions, please. Let us know.");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(jsonBody);
        createEmailRequest(json);
    }

    @Override
    public void sendCreateAccountNotification(User accountCreated) throws JsonProcessingException {
        Map<String, Object> jsonBody = createJsonBodyHeader();
        Map<String, String> recipient = new HashMap<>();
        recipient.put("email", accountCreated.getEmail());

        jsonBody.put("to", List.of(recipient));
        jsonBody.put("subject", "Your Account Was Created");
        jsonBody.put("text", "Your password is: \""+accountCreated.getPassword()+"\"");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(jsonBody);
        createEmailRequest(json);
    }

    private void createEmailRequest(String jsonBody){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+KEY);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        String url = "https://send.api.mailtrap.io/api/send";
        restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
    }

    private Map<String, Object> createJsonBodyHeader(){
        Map<String, Object> jsonBody = new HashMap<>();
        Map<String, String> from = new HashMap<>();
        from.put("email", "hello@demomailtrap.com");
        from.put("name", "Mailtrap Test");

        jsonBody.put("from", from);
        return jsonBody;
    }

}
