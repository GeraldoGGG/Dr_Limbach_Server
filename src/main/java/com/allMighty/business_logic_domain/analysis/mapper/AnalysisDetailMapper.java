package com.allMighty.business_logic_domain.analysis.mapper;

import static com.example.jooq.generated.tables.AnalysisDetails.ANALYSIS_DETAILS;

import com.allMighty.business_logic_domain.analysis.dto.AnalysisDetailDTO;
import com.allMighty.enitity.analysis.AnalysisDetail;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Record;
import org.jooq.Result;

public class AnalysisDetailMapper {

  public static final String DETAILS_KEYWORD = "analysisDetails";

  public static AnalysisDetailDTO toDetailDTO(AnalysisDetail entity) {
    return new AnalysisDetailDTO(entity.getId(), entity.getString_value(), entity.getKey_value());
  }

  public static List<AnalysisDetail> toDetailEntities(List<AnalysisDetailDTO> dtoList) {
    if (CollectionUtils.isEmpty(dtoList)) {
      return Collections.emptyList();
    }

    return dtoList.stream()
            .map(dto -> {
              AnalysisDetail entity = new AnalysisDetail();
              entity.setId(dto.getId());
              entity.setString_value(dto.getStringValue());
              entity.setKey_value(dto.getKeyValue());
              return entity;
            })
            .collect(Collectors.toList());
  }


  public static class AnalysisDetailJooqMapper {
    public static List<AnalysisDetail> mapDetails(Record detailRecord) {
      Result<Record> detailResult = detailRecord.get(DETAILS_KEYWORD, Result.class);
      List<AnalysisDetail> details = new ArrayList<>();
      if (detailResult != null) {
        for (Record record : detailResult) {
          AnalysisDetail detailEntity = new AnalysisDetail();
          detailEntity.setId(record.get(ANALYSIS_DETAILS.ID));
          detailEntity.setKey_value(record.get(ANALYSIS_DETAILS.KEY_VALUE));
          detailEntity.setString_value(record.get(ANALYSIS_DETAILS.STRING_VALUE));
          details.add(detailEntity);
        }
      }
      return details;
    }
  }
}
