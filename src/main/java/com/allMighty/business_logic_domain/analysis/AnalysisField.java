package com.allMighty.business_logic_domain.analysis;

import static com.example.jooq.generated.tables.Analysis.ANALYSIS;
import static com.example.jooq.generated.tables.Category.CATEGORY;
import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;
import static com.example.jooq.generated.tables.Package.PACKAGE;
import static com.example.jooq.generated.tables.Tag.TAG;

import com.allMighty.global_operation.filter.FilterableField;
import lombok.Getter;
import org.jooq.Field;

@Getter
public enum AnalysisField implements FilterableField {
  ID("id",  ANALYSIS.ID),
  MEDICAL_NAME("medicalName", ANALYSIS.MEDICAL_NAME),
  TAG_ID("tag:id", TAG.ID),
  TAG_NAME("tag:name", TAG.NAME),
  SERVICE_ID("service:id", MEDICAL_SERVICE.ID),
  PACKAGE_ID("package:id", PACKAGE.ID),
  PACKAGE_NAME("package:name", PACKAGE.NAME),
  CATEGORY_ID("category:id", CATEGORY.ID),
  CATEGORY_NAME("category:name", CATEGORY.NAME),
  ARCHIVED("archived", ANALYSIS.ARCHIVED);

  private final String restAlias;
  private final Field<?> jooqField;

  AnalysisField(String restAlias, Field<?> jooqField) {
    this.restAlias = restAlias;
    this.jooqField = jooqField;
  }
}
