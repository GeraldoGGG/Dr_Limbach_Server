package com.allMighty.enitity.analysis;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category")
public class AnalysisCategoryEntity extends AbstractEntity {
  private boolean archived;
  private boolean removed;

  private String name;

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "category",
      fetch = FetchType.EAGER)
  private List<AnalysisEntity> analyses = new ArrayList<>();

  public void addAnalysis(AnalysisEntity analysis) {
    analyses.add(analysis);
    analysis.setCategory(this);
  }

}
