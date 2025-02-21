package com.allMighty.enitity.analysis;

import com.allMighty.enitity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "medical_analysis_package")
public class AnalysisPackageEntity extends AbstractEntity {

    private Integer price;
    private Integer discountPercentage;
    private boolean promoted;

    private boolean archived;
    private boolean removed;

    @ManyToMany
    @JoinTable(
            name = "medical_package_analysis",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "analysis_id")
    )
    private List<MedicalAnalysisEntity> analyses = new ArrayList<>();

}
