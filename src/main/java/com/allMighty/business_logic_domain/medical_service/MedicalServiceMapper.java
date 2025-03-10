package com.allMighty.business_logic_domain.medical_service;

import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;

import com.allMighty.business_logic_domain.tag.TagDTO;
import com.allMighty.business_logic_domain.tag.TagMapper;
import com.allMighty.enitity.ArticleEntity;
import com.allMighty.enitity.MedicalServiceEntity;
import com.allMighty.enitity.analysis.AnalysisEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class MedicalServiceMapper {

  static final String ARTICLES_IDS_KEYWORD = "articleIds";
  static final String ANALYSIS_IDS_KEYWORD = "analysisId";

  // Converts MedicalServiceEntity to MedicalServiceDTO
  public static MedicalServiceDTO toMedicalServiceDTO(MedicalServiceEntity medicalServiceEntity) {
    MedicalServiceDTO medicalServiceDTO = new MedicalServiceDTO();
    medicalServiceDTO.setId(medicalServiceEntity.getId());
    medicalServiceDTO.setShowInHomePage(medicalServiceEntity.isShowInHomePage());
    medicalServiceDTO.setTitle(medicalServiceEntity.getTitle());
    medicalServiceDTO.setArchived(medicalServiceEntity.isArchived());
    medicalServiceDTO.setRemoved(medicalServiceEntity.isRemoved());
    medicalServiceDTO.setContent(medicalServiceEntity.getContent());

    // Map Articles
    if (CollectionUtils.isNotEmpty(medicalServiceEntity.getArticles())) {
      List<Long> articleIds =
          medicalServiceEntity.getArticles().stream()
              .map(ArticleEntity::getId)
              .collect(Collectors.toList());
      medicalServiceDTO.setArticleIds(articleIds);
    }

    // Map Analyses
    if (CollectionUtils.isNotEmpty(medicalServiceEntity.getAnalysis())) {
      List<Long> analysisIds =
          medicalServiceEntity.getAnalysis().stream()
              .map(AnalysisEntity::getId)
              .collect(Collectors.toList());
      medicalServiceDTO.setAnalysisIds(analysisIds);
    }

    // Map Tags
    if (CollectionUtils.isNotEmpty(medicalServiceEntity.getTags())) {
      Set<TagDTO> tagDTOs =
          medicalServiceEntity.getTags().stream()
              .map(TagMapper::toTagDTO)
              .collect(Collectors.toSet());
      medicalServiceDTO.setTags(tagDTOs);
    }

    return medicalServiceDTO;
  }

  // Converts MedicalServiceDTO to MedicalServiceEntity (updates an existing entity)
  public static void toMedicalServiceEntity(
      MedicalServiceDTO medicalServiceDTO, MedicalServiceEntity medicalServiceEntity) {
    medicalServiceEntity.setShowInHomePage(medicalServiceDTO.isShowInHomePage());
    medicalServiceEntity.setTitle(medicalServiceDTO.getTitle());
    medicalServiceEntity.setArchived(medicalServiceDTO.isArchived());
    medicalServiceEntity.setRemoved(medicalServiceDTO.isRemoved());
    medicalServiceEntity.setContent(medicalServiceDTO.getContent());
  }

  static class MedicalServiceJooqMapper implements RecordMapper<Record, MedicalServiceEntity> {

    @Override
    public MedicalServiceEntity map(Record record) {

      MedicalServiceEntity entity = new MedicalServiceEntity();
      entity.setId(record.get(MEDICAL_SERVICE.ID));
      entity.setVersion(record.get(MEDICAL_SERVICE.VERSION));
      entity.setTitle(record.get(MEDICAL_SERVICE.TITLE));
      entity.setContent(record.get(MEDICAL_SERVICE.CONTENT));
      entity.setArchived(record.get(MEDICAL_SERVICE.ARCHIVED));
      entity.setRemoved(record.get(MEDICAL_SERVICE.REMOVED));
      entity.setShowInHomePage(record.get(MEDICAL_SERVICE.SHOW_IN_HOME_PAGE));

      // Map the article IDs as a list
      List<Long> articleIds = record.get(ARTICLES_IDS_KEYWORD, ArrayList.class);
      List<ArticleEntity> articles = mapArticleEntitiesForService(articleIds);

      entity.setArticles(articles);

      // Map the analysis IDs as a list
      List<Long> analysisIds = record.get(ANALYSIS_IDS_KEYWORD, ArrayList.class);
      List<AnalysisEntity> analyses = mapAnalysisEntitiesForService(analysisIds);
      entity.setAnalysis(analyses);

      return entity;
    }

    private static @NotNull List<AnalysisEntity> mapAnalysisEntitiesForService(
        List<Long> analysisIds) {
      List<AnalysisEntity> analyses =
          analysisIds.stream()
              .map(
                  analysisId -> {
                    AnalysisEntity analysis = new AnalysisEntity();
                    analysis.setId(analysisId);
                    return analysis;
                  })
              .toList();
      return analyses;
    }

    private static @NotNull List<ArticleEntity> mapArticleEntitiesForService(
        List<Long> articleIds) {
      List<ArticleEntity> articles =
          articleIds.stream()
              .map(
                  articleId -> {
                    ArticleEntity article = new ArticleEntity();
                    article.setId(articleId);
                    return article;
                  })
              .toList();
      return articles;
    }
  }
}
