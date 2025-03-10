package com.allMighty.business_logic_domain.analysis.mapper;

import static com.example.jooq.generated.tables.AnalysisDetail.ANALYSIS_DETAIL;

import com.allMighty.business_logic_domain.analysis.dto.AnalysisDetailDTO;
import com.allMighty.enitity.analysis.AnalysisDetailEntity;
import com.allMighty.enitity.analysis.AnalysisEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Record;
import org.jooq.Result;

public class AnalysisDetailMapper {

  public static final String DETAILS_KEYWORD = "analysisDetails";

  public static AnalysisDetailDTO toDetailDTO(AnalysisDetailEntity entity) {
    return new AnalysisDetailDTO(
        entity.getId(), entity.getString_value(), entity.getKey_value(), entity.getVersion());
  }

  public static List<AnalysisDetailEntity> toDetailEntities(
      List<AnalysisDetailDTO> dtoList, AnalysisEntity entity) {
    if (CollectionUtils.isEmpty(dtoList)) {
      return Collections.emptyList();
    }

    return dtoList.stream()
        .map(
            dto -> {
              AnalysisDetailEntity detail = new AnalysisDetailEntity();
              detail.setId(dto.getId());
              detail.setAnalysis(entity);
              detail.setString_value(dto.getStringValue());
              detail.setKey_value(dto.getKeyValue());
              detail.setVersion(dto.getVersion());
              return detail;
            })
        .collect(Collectors.toList());
  }

  public static class AnalysisDetailJooqMapper {
    public static List<AnalysisDetailEntity> mapDetails(Record detailRecord) {
      Result<Record> detailResult = detailRecord.get(DETAILS_KEYWORD, Result.class);
      List<AnalysisDetailEntity> details = new ArrayList<>();
      if (detailResult != null) {
        for (Record record : detailResult) {
          AnalysisDetailEntity detailEntity = new AnalysisDetailEntity();
          detailEntity.setId(record.get(ANALYSIS_DETAIL.ID));
          detailEntity.setKey_value(record.get(ANALYSIS_DETAIL.KEY_VALUE));
          detailEntity.setString_value(record.get(ANALYSIS_DETAIL.STRING_VALUE));
          detailEntity.setVersion(record.get(ANALYSIS_DETAIL.VERSION));
          details.add(detailEntity);
        }
      }
      return details;
    }
  }
}
