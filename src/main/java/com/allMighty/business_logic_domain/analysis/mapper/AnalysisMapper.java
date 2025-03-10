package com.allMighty.business_logic_domain.analysis.mapper;

import static com.allMighty.business_logic_domain.analysis.mapper.AnalysisDetailMapper.AnalysisDetailJooqMapper.mapDetails;
import static com.allMighty.business_logic_domain.analysis.mapper.AnalysisDetailMapper.toDetailEntities;
import static com.allMighty.business_logic_domain.tag.TagMapper.TagJooqMapper.mapTagEntities;
import static com.example.jooq.generated.tables.Analysis.ANALYSIS;

import com.allMighty.business_logic_domain.analysis.dto.AnalysisDTO;
import com.allMighty.business_logic_domain.analysis.dto.AnalysisDetailDTO;
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
    analysisDTO.setRemoved(analysisEntity.isRemoved());

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
    analysisEntity.setRemoved(analysisDTO.isRemoved());
    analysisEntity.setAnalysisDetailEntities(toDetailEntities(analysisDTO.getDetails(), analysisEntity));
  }

  public static class AnalysisJooqMapper implements RecordMapper<Record, AnalysisEntity> {

    @Override
    public AnalysisEntity map(Record record) {
      AnalysisEntity analysis = new AnalysisEntity();

      analysis.setId(record.get(ANALYSIS.ID));
      analysis.setVersion(record.get(ANALYSIS.VERSION));
      analysis.setMedicalName(record.get(ANALYSIS.MEDICAL_NAME));
      analysis.setSynonym(record.get(ANALYSIS.SYNONYM));
      analysis.setPrice(record.get(ANALYSIS.PRICE));
      analysis.setArchived(record.get(ANALYSIS.ARCHIVED));
      analysis.setRemoved(record.get(ANALYSIS.REMOVED));
      analysis.setTags(mapTagEntities(record));
      analysis.setAnalysisDetailEntities(mapDetails(record));

      return analysis;
    }
  }
}
