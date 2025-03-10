package com.allMighty.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

  @Value("${springdoc.info.name}")
  private String title;

  @Value("${springdoc.info.version}")
  private String version;

  @Value("${springdoc.info.description}")
  private String description;

  @Value("${springdoc.contact.name}")
  private String contactName;

  @Value("${springdoc.contact.url}")
  private String contactUrl;

  @Value("{springdoc.contact.email}")
  private String contactEmail;

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info().title(title).version(version).description(description).contact(contact()));
  }

  private Contact contact() {
    return new Contact().name(contactName).email(contactEmail).url(contactUrl);
  }
}
