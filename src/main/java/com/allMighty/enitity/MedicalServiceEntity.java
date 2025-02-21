package com.allMighty.enitity;

import com.allMighty.enitity.analysis.MedicalAnalysisEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "medical_service")
public class MedicalServiceEntity extends AbstractEntity {

    boolean showInHomePage;
    private String title;
    private boolean archived;
    private boolean removed;


    @Column(columnDefinition = "TEXT")
    private String content;


    @ManyToMany
    @JoinTable(
            name = "medical_service_event",
            joinColumns = @JoinColumn(name = "medical_service_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_event_id")
    )
    private List<MedicalEventEntity> events = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "medical_service_analyse",
            joinColumns = @JoinColumn(name = "medical_service_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_analyse_id")
    )
    private List<MedicalAnalysisEntity> analysis = new ArrayList<>();


    //TODO manage image

}
