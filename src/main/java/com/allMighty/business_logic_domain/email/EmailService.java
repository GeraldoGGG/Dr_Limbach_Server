package com.allMighty.business_logic_domain.email;

import com.allMighty.config.email.BrevoConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sendinblue.ApiException;
import sibApi.TransactionalEmailsApi;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.util.Collections;

@Service
@AllArgsConstructor
public class EmailService {

    private final BrevoConfig brevoConfig;

    public void sendEmailWithTemplate(String userEmail, String name, Long templateId) {
        TransactionalEmailsApi apiInstance = brevoConfig.configTransactionalEmailsApi();

        SendSmtpEmail email = new SendSmtpEmail();
        email.setTemplateId(templateId);
        email.setTo(Collections.singletonList(new SendSmtpEmailTo().email(userEmail)));

        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail("ramo.beso@hotmail.com");
        sender.setName("Kali");
        email.setSender(sender);

        try {
            apiInstance.sendTransacEmail(email);
        } catch (ApiException e) {
            System.err.println("API Exception: " + e.getMessage());
            System.err.println("Response Body: " + e.getResponseBody());
            e.printStackTrace();
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
