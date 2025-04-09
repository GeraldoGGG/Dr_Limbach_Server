package com.allMighty.enitity.analysis;

import com.allMighty.enitity.TagEntity;
import com.allMighty.enitity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "analysis")
public class AnalysisEntity extends AbstractEntity {

    private String medicalName;
    private String synonym;
    private Integer price;
    private boolean isoVerified;

    private boolean archived;
    private boolean removed;


    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "analysis_id")
    private List<AnalysisDetailEntity> analysisDetailEntities = new ArrayList<>();



    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "tag_analysis",
            joinColumns = @JoinColumn(name = "analysis_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagEntity> tags;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private AnalysisCategoryEntity category;


    @Transient
    private Long categoryId;

    @Transient
    private String categoryName;

}
