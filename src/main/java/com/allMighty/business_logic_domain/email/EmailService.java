package com.allMighty.business_logic_domain.email;

import com.allMighty.enitity.EmailEntity;
import com.allMighty.global_operation.BaseService;
import com.allMighty.global_operation.exception_management.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.allMighty.business_logic_domain.email.EmailMapper.toEmailDetailDTO;

@Service
@RequiredArgsConstructor
public class EmailService extends BaseService {
    @Value("${email.sender}")
    private String emailSender;

    @Value("${email.receiver}")
    private String receiver;

    private final JavaMailSender mailSender;

    private final EmailRepository emailRepository;

    public List<EmailDetailDTO> getSubscribersEmails() {
        return emailRepository.getSubscribersEmails().stream()
                .map(EmailMapper::toEmailDetailDTO)
                .toList();
    }

    @Transactional
    public EmailDetailDTO saveEmail(EmailDetailDTO emailDetailDTO) {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setEmailAddress(emailDetailDTO.getEmail());
        emailEntity = em.merge(emailEntity);

        if (emailEntity.getId() == null) {
            throw new InternalServerException("Failed to save email");
        }
        return toEmailDetailDTO(emailEntity);
    }

    public void sendEmailWithTemplate(EmailDetailDTO emailDetailDTO) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(receiver);
            message.setSubject("Client Contact");

            StringBuilder body = new StringBuilder();
            body.append("Name: ").append(emailDetailDTO.getName()).append("\n");
            body.append("Email: ").append(emailDetailDTO.getEmail()).append("\n");
            body.append("Phone Number: ").append(emailDetailDTO.getPhoneNumber()).append("\n\n");
            body.append("Message:\n").append(emailDetailDTO.getMessage());

            message.setText(body.toString());
            message.setFrom(emailSender);

            mailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
