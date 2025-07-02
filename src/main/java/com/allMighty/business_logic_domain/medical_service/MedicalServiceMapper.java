package com.allMighty.business_logic_domain.medical_service;

import static com.allMighty.business_logic_domain.analysis.mapper.AnalysisMapper.mapAnalysisEntities;
import static com.allMighty.business_logic_domain.article.ArticleMapper.mapArticleEntities;
import static com.allMighty.business_logic_domain.tag.TagMapper.TagJooqMapper.mapTagEntities;
import static com.allMighty.util.JooqMapperProperty.ANALYSIS_IDS_KEYWORD;
import static com.allMighty.util.JooqMapperProperty.ARTICLES_IDS_KEYWORD;
import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;

import com.allMighty.business_logic_domain.tag.TagDTO;
import com.allMighty.business_logic_domain.tag.TagMapper;
import com.allMighty.enitity.article.ArticleEntity;
import com.allMighty.enitity.MedicalServiceEntity;
import com.allMighty.enitity.analysis.AnalysisEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class MedicalServiceMapper {

  // Converts MedicalServiceEntity to MedicalServiceDTO
  public static MedicalServiceDTO toMedicalServiceDTO(MedicalServiceEntity medicalServiceEntity) {
    MedicalServiceDTO medicalServiceDTO = new MedicalServiceDTO();
    medicalServiceDTO.setId(medicalServiceEntity.getId());
    medicalServiceDTO.setShowInHomePage(medicalServiceEntity.isShowInHomePage());
    medicalServiceDTO.setTitle(medicalServiceEntity.getTitle());
    medicalServiceDTO.setArchived(medicalServiceEntity.isArchived());
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

  public static void toMedicalServiceEntity(
      MedicalServiceDTO medicalServiceDTO, MedicalServiceEntity medicalServiceEntity) {
    medicalServiceEntity.setShowInHomePage(medicalServiceDTO.isShowInHomePage());
    medicalServiceEntity.setTitle(medicalServiceDTO.getTitle());
    medicalServiceEntity.setArchived(medicalServiceDTO.isArchived());
    medicalServiceEntity.setContent(medicalServiceDTO.getContent());
  }

  static class MedicalServiceJooqMapper implements RecordMapper<Record, MedicalServiceEntity> {

    @Override
    public MedicalServiceEntity map(Record record) {

      MedicalServiceEntity entity = new MedicalServiceEntity();
      entity.setId(record.get(MEDICAL_SERVICE.ID));
      entity.setTitle(record.get(MEDICAL_SERVICE.TITLE));
      entity.setContent(record.get(MEDICAL_SERVICE.CONTENT));
      entity.setArchived(record.get(MEDICAL_SERVICE.ARCHIVED));
      entity.setShowInHomePage(record.get(MEDICAL_SERVICE.SHOW_IN_HOME_PAGE));
      // Map the article IDs as a list
      List<Long> articleIds = record.get(ARTICLES_IDS_KEYWORD, ArrayList.class);
      List<ArticleEntity> articles = mapArticleEntities(articleIds);

      entity.setArticles(articles);

      //tags
      entity.setTags(mapTagEntities(record));

      // Map the analysis IDs as a list
      List<Long> analysisIds = record.get(ANALYSIS_IDS_KEYWORD, ArrayList.class);
      List<AnalysisEntity> analyses = mapAnalysisEntities(analysisIds);
      entity.setAnalysis(analyses);

      return entity;
    }
  }
}
