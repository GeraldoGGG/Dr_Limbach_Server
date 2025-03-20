package com.allMighty.business_logic_domain.email;

import com.allMighty.business_logic_domain.email_detail.EmailDetailDTO;
import com.allMighty.business_logic_domain.export.ExportService;
import com.allMighty.client.UrlProperty;
import com.allMighty.enitity.EmailEntity;
import com.allMighty.global_operation.response.EntityResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.allMighty.client.UrlProperty.Email.EXPORT;
import static com.allMighty.client.UrlProperty.Email.SUBSCRIBE;
import static com.allMighty.global_operation.response.ResponseFactory.createResponse;

@RestController
@RequestMapping(UrlProperty.Email.PATH)
@RequiredArgsConstructor
@Validated
public class EmailController {
    private final EmailService emailService;
    private final ExportService exportService;

    @PostMapping
    public ResponseEntity<String> sendContactEmail(@RequestBody EmailDetailDTO emailDetailDTO) {
        emailService.sendEmailWithTemplate(emailDetailDTO);
        return ResponseEntity.ok("Contact email sent successfully!");
    }

    @PostMapping(SUBSCRIBE)
    public ResponseEntity<EntityResponseDTO<EmailEntity>> saveSubscriberEmail(@RequestParam(name = "emailAddress") @Valid String emailAddress) {
        EmailEntity savedEmail = emailService.saveEmail(emailAddress);

        return ResponseEntity.ok(createResponse(savedEmail));
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
