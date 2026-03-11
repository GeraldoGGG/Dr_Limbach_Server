package com.allMighty.business_logic_domain.search.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatSuggestResponseDTO {
  private List<Long> analysisIds = new ArrayList<>();
  private String responseText;
}
