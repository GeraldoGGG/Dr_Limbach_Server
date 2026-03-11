package com.allMighty.business_logic_domain.search.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysisCatalogItemDTO {
  private Long id;
  private String medicalName;
  private String synonym;
  private String categoryName;
}
