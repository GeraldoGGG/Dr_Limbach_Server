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
    private String keyValue;
    private String stringValue;

    public AnalysisDetailDTO(String stringValue, String keyValue) {
        this.stringValue = stringValue;
        this.keyValue = keyValue;
    }
}
