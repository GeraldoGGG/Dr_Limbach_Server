package com.allMighty.business_logic_domain.email;

import static com.example.jooq.generated.tables.Email.EMAIL;

import com.allMighty.enitity.EmailEntity;
import org.jooq.RecordMapper;
import org.jooq.Record;

public class EmailMapper {
  public static EmailDetailDTO toEmailDetailDTO(EmailEntity emailDetailEntity) {
    EmailDetailDTO emailDetailDTO = new EmailDetailDTO();
    emailDetailDTO.setEmail(emailDetailEntity.getEmailAddress());
    emailDetailDTO.setId(emailDetailEntity.getId());
    return emailDetailDTO;
  }

  static class EmailJooqMapper implements RecordMapper<Record, EmailEntity> {

    @Override
    public EmailEntity map(Record record) {
      EmailEntity emailEntity = new EmailEntity();
      emailEntity.setId(record.get(EMAIL.ID));
      emailEntity.setEmailAddress(record.get(EMAIL.EMAIL_ADDRESS));
      return emailEntity;
    }
  }
}
