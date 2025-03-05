package com.allMighty.business_logic_domain.email;

import com.allMighty.client.UrlProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlProperty.Email.PATH)
@RequiredArgsConstructor
@Validated
public class EmailController {
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<String> sendWelcomeEmail(@RequestParam (name = "userEmail") String userEmail,
                                                   @RequestParam (name = "name") String name) {
        try {
            emailService.sendEmailWithTemplate(userEmail, name, 1L);
            return ResponseEntity.ok("Welcome email sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send welcome email: " + e.getMessage());
        }
    }
}
