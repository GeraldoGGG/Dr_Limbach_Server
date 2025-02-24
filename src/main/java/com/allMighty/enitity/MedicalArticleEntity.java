package com.allMighty.enitity;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
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

}
