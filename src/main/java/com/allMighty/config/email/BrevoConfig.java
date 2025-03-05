package com.allMighty.config.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sendinblue.ApiClient;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;

@Configuration
public class BrevoConfig {

    @Value("${sendinblue.api.key}")
    private String apiKey;

    @Bean
    public TransactionalEmailsApi configTransactionalEmailsApi() {
        ApiClient apiClient = new ApiClient();
        ApiKeyAuth apiKeyAuth = (ApiKeyAuth) apiClient.getAuthentication("api-key");
        apiKeyAuth.setApiKey(apiKey);
        System.err.println(apiKey);
        return new TransactionalEmailsApi(apiClient);
    }
}
