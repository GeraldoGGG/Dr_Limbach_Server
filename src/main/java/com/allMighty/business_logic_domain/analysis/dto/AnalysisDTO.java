package com.allMighty.business_logic_domain.analysis.dto;

import com.allMighty.business_logic_domain.image.ImageDTO;
import com.allMighty.business_logic_domain.tag.TagDTO;
import com.allMighty.global_operation.dto.AbstractDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysisDTO extends AbstractDTO {
  private Long id;

  private String medicalName;
  private String synonym;
  private Integer price;

  private boolean archived;
  private boolean removed;
  private Long version;

  private List<AnalysisDetailDTO> details = new ArrayList<>();

  private Set<TagDTO> tags = new HashSet<>();

  private List<ImageDTO> images = new ArrayList<>();
}
