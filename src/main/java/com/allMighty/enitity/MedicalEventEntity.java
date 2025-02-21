package com.allMighty.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "medical_event")
public class MedicalEventEntity extends AbstractEntity {

    private String title;

    @Column(length = 5000)
    private String description;


    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime eventDate;

    @Column(columnDefinition = "INTERVAL")
    private Duration eventDuration;


    private Integer price;

    private boolean archived;
    private boolean removed;




    //nuk ka nevoj me pas lidhje ne krahun tjtr MedicalServiceEntity
    //let te qendorj me e paster pastaj bejm jooq join dhe nje light wait per detajet e medicalservice entity





    //TODO manage image



}
