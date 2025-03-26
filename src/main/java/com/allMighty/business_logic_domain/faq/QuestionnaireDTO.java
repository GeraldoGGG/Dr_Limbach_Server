package com.allMighty.business_logic_domain.faq;

import com.allMighty.enumeration.BusinessModule;
import com.allMighty.global_operation.dto.AbstractDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionnaireDTO extends AbstractDTO {
    private Long id;
    private Long version;
    private String question;
    private String answer;
    private BusinessModule businessModule;
}
