package com.allMighty.business_logic_domain.analysis;

import static com.example.jooq.generated.tables.Article.ARTICLE;
import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;
import static com.example.jooq.generated.tables.Tag.TAG;

import com.allMighty.global_operation.filter.FilterableField;
import lombok.Getter;
import org.jooq.Field;

@Getter
public enum AnalysisField implements FilterableField {
  ID("id", ARTICLE.ID),
  TAG_ID("tag:id", TAG.ID),
  TAG_NAME("tag:name", TAG.NAME),
  SERVICE_ID("service:id", MEDICAL_SERVICE.ID);

  private final String restAlias;
  private final Field<?> jooqField;

  AnalysisField(String restAlias, Field<?> jooqField) {
    this.restAlias = restAlias;
    this.jooqField = jooqField;
  }
}
