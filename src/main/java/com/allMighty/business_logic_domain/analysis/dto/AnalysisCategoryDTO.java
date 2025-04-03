package com.allMighty.business_logic_domain.analysis.dto;


import com.allMighty.global_operation.dto.AbstractDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnalysisCategoryDTO extends AbstractDTO {
    private Long id;

    @NotEmpty(message = "name must not be empty")
    private String name;
    private List<Long> analysisIds;
    private Long version;
    private boolean archived;
    private boolean removed;
}
