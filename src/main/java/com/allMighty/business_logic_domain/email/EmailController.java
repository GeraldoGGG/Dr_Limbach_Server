package com.allMighty.business_logic_domain.email;

import com.allMighty.business_logic_domain.email_detail.EmailDetailDTO;
import com.allMighty.business_logic_domain.export.ExportService;
import com.allMighty.client.UrlProperty;
import com.allMighty.enitity.EmailEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.allMighty.client.UrlProperty.Email.EXPORT;
import static com.allMighty.client.UrlProperty.Email.SUBSCRIBE;

@RestController
@RequestMapping(UrlProperty.Email.PATH)
@RequiredArgsConstructor
@Validated
public class EmailController {
    private final EmailService emailService;
    private final ExportService exportService;

    @PostMapping
    public ResponseEntity<String> sendContactEmail(@RequestBody EmailDetailDTO emailDetailDTO) {
        try {
            emailService.sendEmailWithTemplate(emailDetailDTO);
            return ResponseEntity.ok("Contact email sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send contact email: " + e.getMessage());
        }
    }

    @PostMapping(SUBSCRIBE)
    public ResponseEntity<String> saveSubscriberEmail(@RequestParam(name = "emailAddress") @Valid String emailAddress) {
        try {
            emailService.saveEmail(emailAddress);
            return ResponseEntity.ok("Subscriber email saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save subscriber email: " + e.getMessage());
        }
    }

    @GetMapping(EXPORT)
    public ResponseEntity<byte[]> getAllEmails() {
        List<EmailEntity> ermailList = emailService.getSubscribersEmails();

        byte[] excelFile = exportService.generateSubscribersEmailExcel(ermailList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=filename=subscribers.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelFile);
    }
}
