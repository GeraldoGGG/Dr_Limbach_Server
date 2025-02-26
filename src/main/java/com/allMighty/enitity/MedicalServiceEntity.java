package com.allMighty.enitity;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import com.allMighty.enitity.analysis.AnalysisEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            name = "medical_service_article",
            joinColumns = @JoinColumn(name = "medical_service_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private List<ArticleEntity> articles = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "medical_service_analysis",
            joinColumns = @JoinColumn(name = "medical_service_id"),
            inverseJoinColumns = @JoinColumn(name = "analysis_id")
    )
    private List<AnalysisEntity> analysis = new ArrayList<>();





    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "tag_medical_service",
            joinColumns = @JoinColumn(name = "medical_service_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagEntity> tags;



}
