package com.allMighty.enitity;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
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

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime eventDate;


    @Column(columnDefinition = "INTERVAL")
    private Duration eventDuration;


    private Integer price;

    private boolean archived;
    private boolean removed;

}
