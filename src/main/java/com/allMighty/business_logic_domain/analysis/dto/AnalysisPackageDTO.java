package com.allMighty.business_logic_domain.analysis.dto;

import com.allMighty.global_operation.dto.AbstractDTO;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysisPackageDTO extends AbstractDTO {
  private Long id;

  private String name;
  private Integer price;
  private Long categoryId;
  private List<Long> analysisIds;
  private boolean showInHomePage;
  private String description;

  private boolean archived;
}
