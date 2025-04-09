package com.allMighty.business_logic_domain.faq;

import com.allMighty.enitity.QuestionnaireEntity;
import com.allMighty.enumeration.BusinessModule;
import org.jooq.Record;
import org.jooq.RecordMapper;

import static com.example.jooq.generated.Tables.QUESTIONNAIRE;

public class QuestionnaireMapper {


    public static QuestionnaireDTO toQuestionnaireDTO(QuestionnaireEntity questionnaireEntity) {
        QuestionnaireDTO questionnaireDTO = new QuestionnaireDTO();
        questionnaireDTO.setId(questionnaireEntity.getId());
        questionnaireDTO.setBusinessModule(questionnaireEntity.getBusinessModule());
        questionnaireDTO.setQuestion(questionnaireEntity.getQuestion());
        questionnaireDTO.setAnswer(questionnaireEntity.getAnswer());
        return questionnaireDTO;
    }

    public static QuestionnaireEntity toQuestionnaireEntity(QuestionnaireDTO questionnaireDTO,
                                                            QuestionnaireEntity questionnaireEntity) {
        questionnaireEntity.setBusinessModule(questionnaireDTO.getBusinessModule());
        questionnaireEntity.setQuestion(questionnaireDTO.getQuestion());
        questionnaireEntity.setAnswer(questionnaireDTO.getAnswer());
        return questionnaireEntity;
    }

    static class QuestionnaireJooqMapper implements RecordMapper<Record, QuestionnaireEntity> {

        @Override
        public QuestionnaireEntity map(Record record) {
            QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();

            questionnaireEntity.setId(record.get(QUESTIONNAIRE.ID));
            questionnaireEntity.setBusinessModule(record.get(QUESTIONNAIRE.BUSINESS_MODULE, BusinessModule.class));
            questionnaireEntity.setQuestion(record.get(QUESTIONNAIRE.QUESTION));
            questionnaireEntity.setAnswer(record.get(QUESTIONNAIRE.ANSWER));

            return questionnaireEntity;
        }
    }
}
