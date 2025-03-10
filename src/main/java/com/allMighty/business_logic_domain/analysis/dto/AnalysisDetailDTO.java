package com.allMighty.business_logic_domain.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisDetailDTO {
    private Long id;
    private String stringValue;
    private String keyValue;
    private Long version;
}
