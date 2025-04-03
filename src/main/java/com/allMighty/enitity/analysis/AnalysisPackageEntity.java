package com.allMighty.enitity.analysis;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import com.allMighty.enumeration.BusinessModule;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "package")
public class AnalysisPackageEntity extends AbstractEntity {

  private String name;
  private Integer price;
  private boolean archived;
  private boolean removed;

  @Enumerated(EnumType.STRING)
  private BusinessModule businessModule;

  @ManyToMany
  @JoinTable(
      name = "package_analysis",
      joinColumns = @JoinColumn(name = "package_id"),
      inverseJoinColumns = @JoinColumn(name = "analysis_id"))
  private List<AnalysisEntity> analyses = new ArrayList<>();
}
