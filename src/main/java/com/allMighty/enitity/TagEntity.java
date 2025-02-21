package com.allMighty.enitity;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import com.allMighty.enitity.analysis.MedicalAnalysisEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagEntity extends AbstractEntity {

    //unique
    private String name;


    @ManyToMany
    @JoinTable(
            name = "medical_article_entity_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_article_id")
    )
    private Set<MedicalArticleEntity> medicalArticles;

    @ManyToMany
    @JoinTable(
            name = "medical_service_entity_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_service_id")
    )
    private Set<MedicalServiceEntity> medicalServices;

    @ManyToMany
    @JoinTable(
            name = "medical_analysis_entity_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_analysis_id")
    )
    private Set<MedicalAnalysisEntity> medicalAnalyses;
}
