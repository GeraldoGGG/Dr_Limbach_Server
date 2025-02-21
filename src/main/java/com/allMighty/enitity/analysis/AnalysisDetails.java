package com.allMighty.enitity.analysis;

import com.allMighty.enitity.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "analysis_detials")
public class AnalysisDetails extends AbstractEntity {

    private String string_value;
    private String key_value;

    @Column(name = "analysis_id", insertable = false, updatable = false)
    private Long analysisId;

}



