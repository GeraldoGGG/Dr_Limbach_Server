package com.allMighty.business_logic_domain.analysis.mapper;

import static com.allMighty.business_logic_domain.analysis.mapper.AnalysisDetailMapper.AnalysisDetailJooqMapper.mapDetails;
import static com.allMighty.business_logic_domain.tag.TagMapper.TagJooqMapper.mapTagEntities;
import static com.example.jooq.generated.tables.Analysis.ANALYSIS;
import static com.example.jooq.generated.tables.Category.CATEGORY;

import com.allMighty.business_logic_domain.analysis.dto.AnalysisDTO;
import com.allMighty.business_logic_domain.analysis.dto.AnalysisDetailDTO;
import com.allMighty.business_logic_domain.general.EntityIdDTO;
import com.allMighty.business_logic_domain.tag.TagDTO;
import com.allMighty.business_logic_domain.tag.TagMapper;
import com.allMighty.enitity.analysis.AnalysisEntity;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class AnalysisMapper {

  public static AnalysisDTO toAnalysisDTO(AnalysisEntity analysisEntity) {
    AnalysisDTO analysisDTO = new AnalysisDTO();
    analysisDTO.setId(analysisEntity.getId());
    analysisDTO.setMedicalName(analysisEntity.getMedicalName());
    analysisDTO.setSynonym(analysisEntity.getSynonym());
    analysisDTO.setPrice(analysisEntity.getPrice());
    analysisDTO.setArchived(analysisEntity.isArchived());
    analysisDTO.setIsoVerified(analysisEntity.isIsoVerified());

    analysisDTO.setCategory(
        new EntityIdDTO(analysisEntity.getCategoryId(), analysisEntity.getCategoryName()));
    if (CollectionUtils.isNotEmpty(analysisEntity.getAnalysisDetailEntities())) {
      List<AnalysisDetailDTO> analysisDetails =
          analysisEntity.getAnalysisDetailEntities().stream()
              .map(AnalysisDetailMapper::toDetailDTO)
              .toList();

      analysisDTO.setDetails(analysisDetails);
    }

    if (CollectionUtils.isNotEmpty(analysisEntity.getTags())) {
      Set<TagDTO> tagDTOs =
          analysisEntity.getTags().stream().map(TagMapper::toTagDTO).collect(Collectors.toSet());

      analysisDTO.setTags(tagDTOs);
    }
    return analysisDTO;
  }

  public static void toAnalysisEntity(AnalysisDTO analysisDTO, AnalysisEntity analysisEntity) {
    analysisEntity.setMedicalName(analysisDTO.getMedicalName());
    analysisEntity.setSynonym(analysisDTO.getSynonym());
    analysisEntity.setPrice(analysisDTO.getPrice());
    analysisEntity.setArchived(analysisDTO.isArchived());
    analysisEntity.setIsoVerified(analysisDTO.isIsoVerified());
  }

  // TODO do not like this
  public static List<AnalysisEntity> mapAnalysisEntities(List<Long> analysisIds) {
    return analysisIds.stream()
        .map(
            analysisId -> {
              AnalysisEntity analysis = new AnalysisEntity();
              analysis.setId(analysisId);
              return analysis;
            })
        .collect(Collectors.toList());
  }

  public static class AnalysisJooqMapper implements RecordMapper<Record, AnalysisEntity> {

    @Override
    public AnalysisEntity map(Record record) {
      AnalysisEntity analysis = new AnalysisEntity();

      analysis.setId(record.get(ANALYSIS.ID));
      analysis.setMedicalName(record.get(ANALYSIS.MEDICAL_NAME));
      analysis.setSynonym(record.get(ANALYSIS.SYNONYM));
      analysis.setPrice(record.get(ANALYSIS.PRICE));
      analysis.setArchived(record.get(ANALYSIS.ARCHIVED));
      analysis.setIsoVerified(record.get(ANALYSIS.ISO_VERIFIED));

      // category
      analysis.setCategoryId(record.get(CATEGORY.ID));
      analysis.setCategoryName(record.get(CATEGORY.NAME));

      analysis.setTags(mapTagEntities(record));
      analysis.setAnalysisDetailEntities(mapDetails(record));

      return analysis;
    }
  }
}
