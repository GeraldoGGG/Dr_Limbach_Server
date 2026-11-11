package com.allMighty.enitity;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import com.allMighty.enumeration.study.HealthCondition;
import com.allMighty.enumeration.study.Medication;
import com.allMighty.enumeration.study.StudyGroup;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pcos_study_application")
public class StudyApplicationEntity extends AbstractEntity {

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(nullable = false)
  private Integer age;

  @Column(nullable = false)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "study_group", nullable = false)
  private StudyGroup studyGroup;

  @ElementCollection
  @CollectionTable(
      name = "study_application_condition",
      joinColumns = @JoinColumn(name = "application_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "health_condition", nullable = false)
  private Set<HealthCondition> conditions = new HashSet<>();

  @ElementCollection
  @CollectionTable(
      name = "study_application_medication",
      joinColumns = @JoinColumn(name = "application_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "medication", nullable = false)
  private Set<Medication> medications = new HashSet<>();

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  void onCreate() {
    if (createdAt == null) {
      createdAt = LocalDateTime.now();
    }
  }
}
