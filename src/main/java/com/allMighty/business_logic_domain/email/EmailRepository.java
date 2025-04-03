package com.allMighty.business_logic_domain.email;

import static com.example.jooq.generated.tables.Email.EMAIL;

import com.allMighty.enitity.EmailEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmailRepository {
  private final DSLContext dslContext;
  private final EmailMapper.EmailJooqMapper emailJooqMapper = new EmailMapper.EmailJooqMapper();

  public List<EmailEntity> getSubscribersEmails() {
    return dslContext.select(EMAIL.ID, EMAIL.EMAIL_ADDRESS)
            .from(EMAIL)
            .fetch(emailJooqMapper);
  }
}
