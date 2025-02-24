package com.allMighty.enitity;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
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
            name = "medical_service_article",
            joinColumns = @JoinColumn(name = "medical_service_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_article_id")
    )
    private List<MedicalArticleEntity> articles = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "medical_service_analyse",
            joinColumns = @JoinColumn(name = "medical_service_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_analyse_id")
    )
    private List<MedicalAnalysisEntity> analysis = new ArrayList<>();



    // TODO need for handeling data
    public void addArticle(MedicalArticleEntity article) {
        this.articles.add(article);
    }

    public void removeArticle(MedicalArticleEntity article) {
        this.articles.remove(article);
    }

}
