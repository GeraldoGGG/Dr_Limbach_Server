package com.allMighty.business_logic_domain.email;

import com.allMighty.config.email.BrevoConfig;
import com.allMighty.enitity.EmailEntity;
import com.allMighty.global_operation.BaseService;
import com.allMighty.global_operation.exception_management.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sendinblue.ApiException;
import sibApi.TransactionalEmailsApi;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.allMighty.business_logic_domain.email.EmailMapper.toEmailDetailDTO;

@Service
@RequiredArgsConstructor
public class EmailService extends BaseService {
  @Value("${email.sender}")
  private String emailSender;

  @Value("${email.receiver}")
  private String receiver;

  private final EmailRepository emailRepository;
  private final BrevoConfig brevoConfig;

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
    TransactionalEmailsApi apiInstance = brevoConfig.configTransactionalEmailsApi();
    String userName = emailDetailDTO.getName();

    SendSmtpEmail email = new SendSmtpEmail();
    email.setTemplateId(1L);
    email.setTo(Collections.singletonList(new SendSmtpEmailTo().email(receiver)));

    Map<String, Object> params = new HashMap<>();
    params.put("message", emailDetailDTO.getMessage());
    params.put("phoneNumber", emailDetailDTO.getPhoneNumber());
    params.put("emailAddress", emailDetailDTO.getEmail());

    email.setParams(params);
    email.setSubject("Client contact");

    SendSmtpEmailSender sender = new SendSmtpEmailSender();
    sender.setEmail(emailSender);
    sender.setName(userName);
    email.setSender(sender);
    try {
      apiInstance.sendTransacEmail(email);
    } catch (ApiException e) {
      throw new InternalServerException("Failed to send email", e);
    }
  }
}
