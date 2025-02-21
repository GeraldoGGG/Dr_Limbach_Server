package com.allMighty.enitity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "medical_article")
public class MedicalArticleEntity extends AbstractEntity {

    private String title;
    private String author;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean archived;
    private boolean removed;



    @ManyToMany(mappedBy = "article")
    private List<MedicalServiceEntity> medicalServices = new ArrayList<>();



    //TODO manage image ne dto   List<String> imageUrls = new ArrayList<>();

}
