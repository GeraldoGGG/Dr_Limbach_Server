package com.allMighty.business_logic_domain.analysis.analysis_package;

import static com.allMighty.business_logic_domain.analysis.mapper.AnalysisMapper.mapAnalysisEntities;
import static com.allMighty.util.JooqMapperProperty.ANALYSIS_IDS_KEYWORD;
import static com.example.jooq.generated.tables.Package.PACKAGE;

import com.allMighty.business_logic_domain.analysis.dto.AnalysisPackageDTO;
import com.allMighty.enitity.abstractEntity.AbstractEntity;
import com.allMighty.enitity.analysis.AnalysisEntity;
import com.allMighty.enitity.analysis.AnalysisPackageEntity;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class AnalysisPackageMapper {

  public static AnalysisPackageDTO toAnalysisPackageDTO(
      AnalysisPackageEntity analysisPackageEntity) {
    AnalysisPackageDTO analysisPackageDTO = new AnalysisPackageDTO();

    analysisPackageDTO.setId(analysisPackageEntity.getId());
    analysisPackageDTO.setName(analysisPackageEntity.getName());
    analysisPackageDTO.setPrice(analysisPackageEntity.getPrice());
    analysisPackageDTO.setShowInHomePage(analysisPackageEntity.isShowInHomePage());
    analysisPackageDTO.setArchived(analysisPackageEntity.isArchived());
    if (CollectionUtils.isNotEmpty(analysisPackageEntity.getAnalyses())) {
      List<Long> analysisIds = analysisPackageEntity.getAnalyses().stream().map(AbstractEntity::getId).toList();
      analysisPackageDTO.setAnalysisIds(analysisIds);
    }

    return analysisPackageDTO;
  }

  public static void toAnalysisPackageEntity(
      AnalysisPackageDTO packageDTO, AnalysisPackageEntity packageEntity) {
    packageEntity.setName(packageDTO.getName());
    packageEntity.setPrice(packageDTO.getPrice());
    packageEntity.setArchived(packageDTO.isArchived());
    packageEntity.setShowInHomePage(packageDTO.isShowInHomePage());
  }

  static class AnalysisPackageJooqMapper implements RecordMapper<Record, AnalysisPackageEntity> {

    @Override
    public AnalysisPackageEntity map(Record record) {

      AnalysisPackageEntity entity = new AnalysisPackageEntity();
      entity.setId(record.get(PACKAGE.ID));
      entity.setName(record.get(PACKAGE.NAME));
      entity.setPrice(record.get(PACKAGE.PRICE));
      entity.setArchived(record.get(PACKAGE.ARCHIVED));
      entity.setShowInHomePage(record.get(PACKAGE.SHOW_IN_HOME_PAGE));

      List<Long> analysisIds = record.get(ANALYSIS_IDS_KEYWORD, ArrayList.class);
      List<AnalysisEntity> analyses = mapAnalysisEntities(analysisIds);
      entity.setAnalyses(analyses);

      return entity;
    }
  }
}
