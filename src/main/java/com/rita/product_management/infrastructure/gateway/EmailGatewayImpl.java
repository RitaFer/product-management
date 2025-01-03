package com.rita.product_management.infrastructure.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class EmailGatewayImpl implements EmailGateway {

    @Value("${email-server.key:}")
    private String KEY;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String EMAIL_API_URL = "https://send.api.mailtrap.io/api/send";

    @Override
    public void sendToken(String to, String token) {
        log.info("Sending token email to: [{}]", to);
        try {
            String jsonBody = createJsonBody("New code for reset password", "Your code is: \"" + token + "\"", to);
            createEmailRequest(jsonBody);
            log.info("Token email successfully sent to: [{}]", to);
        } catch (Exception e) {
            handleException("sendToken", e, to);
        }
    }

    @Override
    public void sendUpdateNotification(String to, String updateInfo) {
        log.info("Sending update notification email to: [{}]", to);
        try {
            String jsonBody = createJsonBody("New Update In Your Account", "Update Info: \"" + updateInfo + "\"", to);
            createEmailRequest(jsonBody);
            log.info("Update notification email successfully sent to: [{}]", to);
        } catch (Exception e) {
            handleException("sendUpdateNotification", e, to);
        }
    }

    @Override
    public void sendDeleteNotification(String to) {
        log.info("Sending delete notification email to: [{}]", to);
        try {
            String jsonBody = createJsonBody(
                    "Your Account Was Deleted",
                    "Your account was deleted from our system. If you have any questions, please let us know.",
                    to
            );
            createEmailRequest(jsonBody);
            log.info("Delete notification email successfully sent to: [{}]", to);
        } catch (Exception e) {
            handleException("sendDeleteNotification", e, to);
        }
    }

    @Override
    public void sendCreateAccountNotification(User accountCreated) {
        log.info("Sending account creation notification email to: [{}]", accountCreated.getEmail());
        try {
            String jsonBody = createJsonBody(
                    "Your Account Was Created",
                    "Your password is: \"" + accountCreated.getPassword() + "\"",
                    accountCreated.getEmail()
            );
            createEmailRequest(jsonBody);
            log.info("Account creation notification email successfully sent to: [{}]", accountCreated.getEmail());
        } catch (Exception e) {
            handleException("sendCreateAccountNotification", e, accountCreated.getEmail());
        }
    }

    private String createJsonBody(String subject, String text, String to) throws JsonProcessingException {
        log.debug("Creating JSON body for email to: [{}], subject: [{}]", to, subject);
        Map<String, Object> jsonBody = createJsonBodyHeader();
        Map<String, String> recipient = new HashMap<>();
        recipient.put("email", to);

        jsonBody.put("to", List.of(recipient));
        jsonBody.put("subject", subject);
        jsonBody.put("text", text);

        String json = objectMapper.writeValueAsString(jsonBody);
        log.debug("JSON body created successfully for email to: [{}]", to);
        return json;
    }

    private void createEmailRequest(String jsonBody) {
        log.debug("Sending email request to Mailtrap API...");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + KEY);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    EMAIL_API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Failed to send email. Response status: [{}], body: [{}]",
                        response.getStatusCode(), response.getBody());
                throw new RuntimeException("Failed to send email: " + response.getStatusCode());
            }
            log.debug("Email request sent successfully. Response: [{}]", response.getBody());
        } catch (Exception e) {
            log.error("Error while sending email request to Mailtrap API: [{}]", e.getMessage(), e);
            throw new RuntimeException("Error while sending email request", e);
        }
    }

    private Map<String, Object> createJsonBodyHeader() {
        log.debug("Creating email JSON header...");
        Map<String, Object> jsonBody = new HashMap<>();
        Map<String, String> from = new HashMap<>();
        from.put("email", "hello@demomailtrap.com");
        from.put("name", "Mailtrap Test");

        jsonBody.put("from", from);
        log.debug("Email JSON header created successfully.");
        return jsonBody;
    }

    private void handleException(String methodName, Exception e, String recipient) {
        log.error("Error in method [{}] while sending email to [{}]: [{}]", methodName, recipient, e.getMessage(), e);
    }

}
