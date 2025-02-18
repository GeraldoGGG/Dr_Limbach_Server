package com.allMighty.enitity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "medical_blog")
public class MedicalBlogEntity extends AbstractEntity {

    private String title;
    private String content;
    private boolean archived;
    private boolean removed;

    @ManyToMany(mappedBy = "blogs")
    private List<MedicalServiceEntity> medicalServices = new ArrayList<>();
}
