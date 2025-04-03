package com.allMighty.business_logic_domain.faq;

import com.allMighty.client.UrlProperty.Questionnaire;
import com.allMighty.global_operation.response.EntityResponseDTO;
import com.allMighty.global_operation.response.page.EntityPageResponseDTO;
import com.allMighty.global_operation.response.page.PageDescriptor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.allMighty.global_operation.response.ResponseFactory.createPage;
import static com.allMighty.global_operation.response.ResponseFactory.createResponse;

@RestController
@RequestMapping(Questionnaire.PATH)
@RequiredArgsConstructor
@Validated
public class QuestionnaireController {
  private final QuestionnaireService questionnaireService;

  @GetMapping
  public ResponseEntity<EntityPageResponseDTO<QuestionnaireDTO>> getQuestionnaire(
      @RequestParam(name = "page", defaultValue = "1") Long page,
      @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize,
      @RequestParam(value = "filter", defaultValue = "") List<String> filters) {

    Long count = questionnaireService.count(filters);
    PageDescriptor pageDescriptor = new PageDescriptor(page, pageSize);
    List<QuestionnaireDTO> questionnaireDTOS =
        questionnaireService.getAllQuestionnaire(filters, pageDescriptor);

    return ResponseEntity.ok(createPage(count, questionnaireDTOS, pageDescriptor));
  }

  @PostMapping
  public ResponseEntity<EntityResponseDTO<QuestionnaireDTO>> createQuestionnaire(
      @RequestBody @Valid QuestionnaireDTO questionnaireDTO) {

    Long questionnaireId = questionnaireService.createQuestionnaire(questionnaireDTO);
    QuestionnaireDTO createdQuestionnaire =
        questionnaireService.getQuestionnaireById(questionnaireId);

    return ResponseEntity.ok(createResponse(createdQuestionnaire));
  }

  //TODO duhet me bot nje update per pytje pergjigje si dhe nje delete (ne kete rast do bejm delete real nga db
}
