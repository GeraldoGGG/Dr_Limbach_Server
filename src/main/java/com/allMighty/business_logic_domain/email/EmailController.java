package com.allMighty.business_logic_domain.email;

import com.allMighty.business_logic_domain.export.ExportService;
import com.allMighty.client.UrlProperty;
import com.allMighty.global_operation.response.EntityResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    public ResponseEntity<EntityResponseDTO<EmailDetailDTO>> saveSubscriberEmail(
            @RequestBody @Valid EmailDetailDTO emailDetailDTO) {
        EmailDetailDTO savedEmail = emailService.saveEmail(emailDetailDTO);

        return ResponseEntity.ok(createResponse(savedEmail));
    }

    @GetMapping(EXPORT)
    public ResponseEntity<byte[]> exportEmails() {
        List<EmailDetailDTO> emailList = emailService.getSubscribersEmails();

        byte[] excelFile = exportService.generateSubscribersEmailExcel(emailList);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=subscribers.xlsx");
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelFile);
    }


    @GetMapping
    public ResponseEntity<EntityResponseDTO<List<EmailDetailDTO>>> getAllEmails() {
        List<EmailDetailDTO> ermailList = emailService.getSubscribersEmails();
        return ResponseEntity.ok(createResponse(ermailList));
    }
}
