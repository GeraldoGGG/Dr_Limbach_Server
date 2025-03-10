package com.allMighty.enitity.analysis;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "analysis_detail")
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisDetailEntity extends AbstractEntity {

    private String string_value;
    private String key_value;

    @ManyToOne
    @JoinColumn(name = "analysis_id", nullable = false)
    private AnalysisEntity analysis;

}



