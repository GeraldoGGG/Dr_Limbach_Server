package com.allMighty.enitity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "medical_analysis")
public class MedicalAnalysisEntity extends AbstractEntity{

    private String medicalName;
    private String synonym; //TODO  to be checked
    private Integer price;


    private boolean archived;
    private boolean removed;

    //TODO opsionet per tu diskutuar

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id")
    private List<FieldValue> fieldValues = new ArrayList<>();



    @ManyToMany(mappedBy = "analysis")
    private List<MedicalServiceEntity> services = new ArrayList<>();

}
