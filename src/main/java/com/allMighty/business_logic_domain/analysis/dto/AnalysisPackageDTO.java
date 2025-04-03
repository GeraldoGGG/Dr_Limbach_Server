package com.allMighty.business_logic_domain.analysis.dto;

import java.util.List;

import com.allMighty.enumeration.BusinessModule;
import com.allMighty.global_operation.dto.AbstractDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysisPackageDTO  extends AbstractDTO {
  private Long id;

  private String name;
  private Integer price;
  private String version;
  private Long categoryId;

  private List<Long> analysisIds;
  @Enumerated(EnumType.STRING)
  private BusinessModule businessModule;

  private boolean archived;
  private boolean removed;
}
