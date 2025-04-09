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
      AnalysisDetailDTO dto = new AnalysisDetailDTO();
      dto.setId(entity.getId());
      dto.setStringValue(entity.getString_value());
      dto.setKeyValue(entity.getKey_value());
      return dto;
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
          details.add(detailEntity);
        }
      }
      return details;
    }
  }
}
