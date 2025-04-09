package com.allMighty.business_logic_domain.medical_service;

import com.allMighty.business_logic_domain.image.ImageDTO;
import com.allMighty.business_logic_domain.tag.TagDTO;
import com.allMighty.global_operation.dto.AbstractDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalServiceDTO extends AbstractDTO {
  private Long Id;
  private boolean showInHomePage;
  private String title;
  private boolean archived;
  private String content;

  private List<Long> analysisIds;
  private List<Long> articleIds;

  private Set<TagDTO> tags;
  private List<ImageDTO> images = new ArrayList<>();
}
