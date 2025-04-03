package com.allMighty.business_logic_domain.faq;

import com.allMighty.enitity.QuestionnaireEntity;
import com.allMighty.global_operation.response.page.PageDescriptor;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.jooq.impl.DSL;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.jooq.generated.Tables.QUESTIONNAIRE;

@Repository
@RequiredArgsConstructor
public class QuestionnaireRepository {
    private final DSLContext dslContext;
    private final QuestionnaireMapper.QuestionnaireJooqMapper questionnaireJooqMapper = new QuestionnaireMapper.QuestionnaireJooqMapper();


    public List<QuestionnaireEntity> getAllQuestionnaire(List<Condition> conditions, PageDescriptor pageDescriptor) {

        if (pageDescriptor == null) {
            pageDescriptor = PageDescriptor.maxDataDescriptor();
        }

        Long offset = pageDescriptor.getOffset();
        Long pageSize = pageDescriptor.getPageSize();

        return dslContext.select(
                QUESTIONNAIRE.ID,
                QUESTIONNAIRE.VERSION,
                QUESTIONNAIRE.BUSINESS_MODULE,
                QUESTIONNAIRE.QUESTION,
                QUESTIONNAIRE.ANSWER
        )
                .from(QUESTIONNAIRE)
                .where(conditions)
                .offset(offset)
                .limit(pageSize)
                .fetch(questionnaireJooqMapper);

    }

    public Long count(List<Condition> conditions){
        return dslContext.select(DSL.countDistinct(QUESTIONNAIRE.ID))
                .from(QUESTIONNAIRE)
                .where(conditions)
                .fetchSingleInto(Long.class);
    }

    public Optional<QuestionnaireEntity> findById(Long id) {
        Condition eq = QUESTIONNAIRE.ID.eq(id);
        List<QuestionnaireEntity> questionnaireEntities = getAllQuestionnaire(Collections.singletonList(eq), null);
        return questionnaireEntities.stream().findFirst();
    }
}
