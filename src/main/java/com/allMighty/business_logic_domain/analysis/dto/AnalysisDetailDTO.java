package com.allMighty.business_logic_domain.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnalysisDetailDTO {
    private Long id;
    private String stringValue;
    private String keyValue;
}
