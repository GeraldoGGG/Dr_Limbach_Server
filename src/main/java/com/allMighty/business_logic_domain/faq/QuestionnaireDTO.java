package com.allMighty.business_logic_domain.faq;

import com.allMighty.enumeration.BusinessModule;
import com.allMighty.global_operation.dto.AbstractDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionnaireDTO extends AbstractDTO {
    private Long id;
    private Long version;
    @NotNull(message = "Question cannot be null")
    private String question;

    @NotNull(message = "Answer cannot be null")
    private String answer;

    @NotNull(message = "Business module cannot be null")
    private BusinessModule businessModule;
}
