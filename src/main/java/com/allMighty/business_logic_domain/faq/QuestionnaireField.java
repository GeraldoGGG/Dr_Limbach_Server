package com.allMighty.business_logic_domain.faq;

import com.allMighty.global_operation.filter.FilterableField;
import lombok.Getter;
import org.jooq.Field;

import static com.example.jooq.generated.Tables.QUESTIONNAIRE;

@Getter
public enum QuestionnaireField implements FilterableField {
    ID("id", QUESTIONNAIRE.ID),
    BUSINESS_MODULE("businessModule", QUESTIONNAIRE.BUSINESS_MODULE);

    private final String restAlias;
    private final Field<?> jooqField;

    QuestionnaireField(String restAlias, Field<?> jooqField) {
        this.restAlias = restAlias;
        this.jooqField = jooqField;
    }
}

