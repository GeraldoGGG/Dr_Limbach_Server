package com.allMighty.enitity;

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


    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String description;


    @ManyToMany
    @JoinTable(
            name = "medical_service_blog",
            joinColumns = @JoinColumn(name = "medical_service_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_blog_id")
    )
    private List<MedicalBlogEntity> blogs = new ArrayList<>();



    @ManyToMany
    @JoinTable(
            name = "medical_service_analyse",
            joinColumns = @JoinColumn(name = "medical_service_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_analyse_id")
    )
    private List<MedicalAnalysisEntity> analysis = new ArrayList<>();

}
