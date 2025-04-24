package com.allMighty.business_logic_domain.medical_service;

import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;
import static com.example.jooq.generated.tables.Package.PACKAGE;
import static com.example.jooq.generated.tables.Tag.TAG;

import com.allMighty.global_operation.filter.FilterableField;
import lombok.Getter;
import org.jooq.Field;

@Getter
public enum MedicalServiceField implements FilterableField {
  ID("id", MEDICAL_SERVICE.ID),
  TITLE("title", MEDICAL_SERVICE.TITLE),
  ARCHIVED("archived", MEDICAL_SERVICE.ARCHIVED),
  SHOW_IN_HOME_PAGE("showInHomePage", MEDICAL_SERVICE.SHOW_IN_HOME_PAGE),
  TAG_ID("tag:id", TAG.ID),
  TAG_NAME("tag:name", TAG.NAME);

  private final String restAlias;
  private final Field<?> jooqField;

  MedicalServiceField(String restAlias, Field<?> jooqField) {
    this.restAlias = restAlias;
    this.jooqField = jooqField;
  }
}
