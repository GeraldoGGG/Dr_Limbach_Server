package com.allMighty.enitity.analysis;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "medical_analysis")
public class MedicalAnalysisEntity extends AbstractEntity {

    private String medicalName;
    private String synonym;
    private Integer price;


    private boolean archived;
    private boolean removed;


    //TODO strong realtionship will need cascade
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id")
    private List<AnalysisDetails> analysisDetails = new ArrayList<>();


}
