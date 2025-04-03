package com.allMighty.business_logic_domain.analysis.analysis_category;

import static com.allMighty.business_logic_domain.analysis.mapper.AnalysisMapper.mapAnalysisEntities;
import static com.allMighty.util.JooqMapperProperty.ANALYSIS_IDS_KEYWORD;
import static com.example.jooq.generated.tables.Category.CATEGORY;

import com.allMighty.business_logic_domain.analysis.dto.AnalysisCategoryDTO;
import com.allMighty.enitity.abstractEntity.AbstractEntity;
import com.allMighty.enitity.analysis.AnalysisCategoryEntity;
import com.allMighty.enitity.analysis.AnalysisEntity;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class AnalysisCategoryMapper {

  public static AnalysisCategoryDTO toAnalysisCategoryDTO(
      AnalysisCategoryEntity analysisCategoryEntity) {
    AnalysisCategoryDTO categoryDTO = new AnalysisCategoryDTO();

    categoryDTO.setId(analysisCategoryEntity.getId());
    categoryDTO.setName(analysisCategoryEntity.getName());
    categoryDTO.setArchived(analysisCategoryEntity.isArchived());
    categoryDTO.setRemoved(analysisCategoryEntity.isRemoved());
    categoryDTO.setVersion(analysisCategoryEntity.getVersion());
    if (CollectionUtils.isNotEmpty(analysisCategoryEntity.getAnalyses())) {
      List<Long> analysisIds =
          analysisCategoryEntity.getAnalyses().stream().map(AbstractEntity::getId).toList();
      categoryDTO.setAnalysisIds(analysisIds);
    }

    return categoryDTO;
  }

  public static void toAnalysisCategoryEntity(
      AnalysisCategoryDTO categoryDTO, AnalysisCategoryEntity categoryEntity) {
    categoryEntity.setName(categoryDTO.getName());
    categoryEntity.setArchived(categoryDTO.isArchived());
    categoryEntity.setRemoved(categoryDTO.isRemoved());
    categoryEntity.setVersion(categoryDTO.getVersion());
  }

  static class AnalysisCategoryJooqMapper implements RecordMapper<Record, AnalysisCategoryEntity> {

    @Override
    public AnalysisCategoryEntity map(Record record) {

      AnalysisCategoryEntity entity = new AnalysisCategoryEntity();
      entity.setId(record.get(CATEGORY.ID));
      entity.setName(record.get(CATEGORY.NAME));
      entity.setArchived(record.get(CATEGORY.ARCHIVED));
      entity.setRemoved(record.get(CATEGORY.REMOVED));
      entity.setVersion(record.get(CATEGORY.VERSION));

      List<Long> analysisIds = record.get(ANALYSIS_IDS_KEYWORD, ArrayList.class);
      List<AnalysisEntity> analyses = mapAnalysisEntities(analysisIds);
      entity.setAnalyses(analyses);

      return entity;
    }
  }
}
