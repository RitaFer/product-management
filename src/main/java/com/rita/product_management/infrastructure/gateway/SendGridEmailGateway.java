package com.rita.product_management.infrastructure.gateway;

import com.rita.product_management.core.gateway.EmailGateway;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SendGridEmailGateway implements EmailGateway {

    private final SendGrid sendGrid;

    public SendGridEmailGateway(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    @Override
    public void sendToken(String to, String token) {
        Email from = new Email("your_email@example.com"); // TODO: input my e-mail
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", "Your code is: "+token);
        Mail mail = new Mail(from, "New code for reset password", toEmail, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            if (response.getStatusCode() >= 400) {
                throw new RuntimeException("[SendGridEmailGateway] Failed to send email: " + response.getBody());
            }
        } catch (IOException ex) {
            throw new RuntimeException("[SendGridEmailGateway] Error sending email", ex);
        }
    }
}
