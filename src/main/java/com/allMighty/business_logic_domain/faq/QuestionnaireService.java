package com.allMighty.business_logic_domain.faq;

import static com.allMighty.business_logic_domain.faq.QuestionnaireMapper.toQuestionnaireEntity;
import static com.allMighty.global_operation.filter.JooqConditionBuilder.buildConditions;

import com.allMighty.enitity.QuestionnaireEntity;
import com.allMighty.global_operation.BaseService;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import com.allMighty.global_operation.filter.FilterParser;
import com.allMighty.global_operation.response.page.PageDescriptor;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionnaireService extends BaseService {
  private final FilterParser<QuestionnaireField> filterParser =
      new FilterParser<>(QuestionnaireField.values());
  private final QuestionnaireRepository questionnaireRepository;

  public List<QuestionnaireDTO> getAllQuestionnaire(
      List<String> filters, PageDescriptor pageDescriptor) {
    List<Condition> conditions = buildConditions(filters, filterParser);

    return questionnaireRepository.getAllQuestionnaire(conditions, pageDescriptor).stream()
        .map(QuestionnaireMapper::toQuestionnaireDTO)
        .toList();
  }

  public Long count(List<String> filters) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    return questionnaireRepository.count(conditions);
  }

  @Transactional
  public Long createQuestionnaire(QuestionnaireDTO questionnaireDTO) {
    QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
    toQuestionnaireEntity(questionnaireDTO, questionnaireEntity);
    QuestionnaireEntity saved = em.merge(questionnaireEntity);

    return saved.getId();
  }

  public QuestionnaireDTO getQuestionnaireById(Long id) {
    return questionnaireRepository
        .findById(id)
        .map(QuestionnaireMapper::toQuestionnaireDTO)
        .orElseThrow(() -> new BadRequestException("Questionnaire not found!"));
  }

  public void delete(Long id) {
    QuestionnaireEntity questionnaireEntity = em.find(QuestionnaireEntity.class, id);
    if (questionnaireEntity != null) {
      em.remove(questionnaireEntity);
    } else {
      throw new BadRequestException("Questionnaire not found!");
    }
  }

  public Long updateQuestionnaire(Long id, QuestionnaireDTO questionnaireDTO) {
    QuestionnaireEntity questionnaireEntity = em.find(QuestionnaireEntity.class, id);
    if (questionnaireEntity == null) {
      throw new BadRequestException("Questionnaire not found!");
    }
    questionnaireEntity.setAnswer(questionnaireEntity.getAnswer());
    questionnaireEntity.setQuestion(questionnaireDTO.getQuestion());
    questionnaireEntity.setBusinessModule(questionnaireDTO.getBusinessModule());

    em.merge(questionnaireEntity);
    return id;
  }
}
