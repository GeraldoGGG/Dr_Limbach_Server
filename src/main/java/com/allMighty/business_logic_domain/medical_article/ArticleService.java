package com.allMighty.business_logic_domain.medical_article;

import static com.allMighty.business_logic_domain.medical_article.ArticleField.ID;
import static com.allMighty.business_logic_domain.medical_article.ArticleMapper.*;
import static com.allMighty.global_operation.filter.FilterOperator.DELIMITER;
import static com.allMighty.global_operation.filter.FilterOperator.EQ;
import static com.allMighty.global_operation.filter.JooqConditionBuilder.buildConditions;

import com.allMighty.enitity.MedicalArticleEntity;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import com.allMighty.global_operation.filter.FilterParser;
import com.allMighty.global_operation.response.page.PageDescriptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleService {

  private final FilterParser<ArticleField> filterParser = new FilterParser<>(ArticleField.values());

  private final ArticleRepository articleRepository;
  //TODO handle   private final TagRepository tagRepository;

  public List<ArticleDTO> getArticles(List<String> filters, PageDescriptor pageDescriptor) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    return articleRepository.getAllArticleDTO(conditions, pageDescriptor);
  }

  public ArticleDTO getArticleById(Long id) {
    String idFilter = ID.getRestAlias() + DELIMITER + EQ + DELIMITER + id;
    List<Condition> conditions = buildConditions(idFilter, filterParser);
    return articleRepository.getAllArticleDTO(conditions).stream()
        .findFirst()
        .orElseThrow(() -> new BadRequestException("Article not found!"));
  }

  public Long count(List<String> filters) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    return articleRepository.count(conditions);
  }

  @Transactional
  public ArticleDTO updateArticle(Long id, ArticleDTO articleDTO) {
    MedicalArticleEntity medicalArticleEntity =
        articleRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("Article not found!"));

    mapDataToMedicalArticleEntity(articleDTO, medicalArticleEntity);
    MedicalArticleEntity saved = articleRepository.save(medicalArticleEntity);

    return toArticleDTO(saved);
  }


  @Transactional
  public ArticleDTO createArticle(ArticleDTO articleDTO) {
    MedicalArticleEntity medicalArticleEntity = new MedicalArticleEntity();

    mapDataToMedicalArticleEntity(articleDTO, medicalArticleEntity);
    MedicalArticleEntity saved = articleRepository.save(medicalArticleEntity);

    return toArticleDTO(saved);
  }
}
