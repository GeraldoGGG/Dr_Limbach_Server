package com.allMighty.business_logic_domain.search.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResponseDTO {
  private List<SearchUnitResponseDTO> eventsFound = new ArrayList<>();
  private List<SearchUnitResponseDTO> analysesFound = new ArrayList<>();
  private List<SearchUnitResponseDTO> servicesFound = new ArrayList<>();
  private List<SearchUnitResponseDTO> articlesFound = new ArrayList<>();
  private List<SearchUnitResponseDTO> analysisPackageFound = new ArrayList<>();
  private List<SearchUnitResponseDTO> analysisCategoryFound = new ArrayList<>();
}
