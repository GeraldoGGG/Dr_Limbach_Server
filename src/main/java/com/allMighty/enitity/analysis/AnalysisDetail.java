package com.allMighty.enitity.analysis;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "analysis_details")
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisDetail extends AbstractEntity {

    private String string_value;
    private String key_value;

    @Column(name = "analysis_id", insertable = false, updatable = false)
    private Long analysisId;

}



