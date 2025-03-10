package com.allMighty.business_logic_domain.medical_service;

import static com.example.jooq.generated.tables.Article.ARTICLE;
import static com.example.jooq.generated.tables.Tag.TAG;

import com.allMighty.global_operation.filter.FilterableField;
import lombok.Getter;
import org.jooq.Field;

@Getter
public enum MedicalServiceField implements FilterableField {
  ID("id", ARTICLE.ID),
  TAG_ID("tag:id", TAG.ID),
  TAG_NAME("tag:name", TAG.NAME);

  private final String restAlias;
  private final Field<?> jooqField;

  MedicalServiceField(String restAlias, Field<?> jooqField) {
    this.restAlias = restAlias;
    this.jooqField = jooqField;
  }
}
