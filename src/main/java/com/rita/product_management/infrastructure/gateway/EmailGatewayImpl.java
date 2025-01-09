package com.rita.product_management.infrastructure.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rita.product_management.core.domain.user.User;
import com.rita.product_management.core.gateway.EmailGateway;
import jakarta.annotation.PostConstruct;
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

    @Value("${email-server.key}")
    private String KEY;

    @Value("${email-server.url}")
    private String emailApiUrl;

    @Value("${email-server.from-email}")
    private String fromEmail;

    @Value("${email-server.from-name}")
    private String fromName;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    private void validateKey() {
        if (KEY == null || KEY.isEmpty()) {
            throw new IllegalStateException("Email server key is not configured. Please check your application properties.");
        }
        log.info("Email server key validated successfully.");
    }

    @Override
    public void sendToken(String to, String token) {
        sendEmailRequest("New Code For Reset Password", "Your code is: \"" + token + "\"", to);
    }

    @Override
    public void sendUpdateNotification(String to, String updateInfo) {
        sendEmailRequest("New Update In Your Account", "Update info: \"" + updateInfo + "\"", to);
    }

    @Override
    public void sendDeleteNotification(String to) {
        sendEmailRequest(
                "Your Account Was Deleted",
                "Your account was deleted from our system. If you have any questions, please let us know.",
                to
        );
    }

    @Override
    public void sendCreateAccountNotification(User accountCreated) {
        sendEmailRequest(
                "Your Account Was Created",
                "Your username is: " + accountCreated.getUsername() + "\nYour password is: " + accountCreated.getPassword(),
                accountCreated.getEmail()
        );
    }

    private void sendEmailRequest(String subject, String text, String to) {
        try {
            String jsonBody = objectMapper.writeValueAsString(createBaseEmailBody(subject, text, to));
            HttpHeaders headers = createHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    emailApiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Failed to send email. Status: [{}], Body: [{}]", response.getStatusCode(), response.getBody());
            }

            log.info("Email sent successfully to: [{}], Subject: [{}]", to, subject);
        } catch (JsonProcessingException e) {
            log.error("Error while processing JSON for email to [{}]: {}", to, e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error while sending email to [{}]: {}", to, e.getMessage(), e);
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + KEY);
        return headers;
    }

    private Map<String, Object> createBaseEmailBody(String subject, String text, String to) {
        Map<String, Object> jsonBody = createJsonBodyHeader();
        Map<String, String> recipient = new HashMap<>();
        recipient.put("email", to);

        jsonBody.put("to", List.of(recipient));
        jsonBody.put("subject", subject);
        jsonBody.put("text", text);

        return jsonBody;
    }

    private Map<String, Object> createJsonBodyHeader() {
        Map<String, Object> jsonBody = new HashMap<>();
        Map<String, String> from = new HashMap<>();
        from.put("email", fromEmail);
        from.put("name", fromName);
        jsonBody.put("from", from);
        return jsonBody;
    }

}
