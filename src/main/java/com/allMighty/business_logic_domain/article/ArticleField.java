package com.allMighty.business_logic_domain.article;

import static com.example.jooq.generated.tables.Article.ARTICLE;
import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;
import static com.example.jooq.generated.tables.Package.PACKAGE;
import static com.example.jooq.generated.tables.Tag.TAG;

import com.allMighty.global_operation.filter.FilterableField;
import lombok.Getter;
import org.jooq.Field;

@Getter
public enum ArticleField implements FilterableField {
  ID("id", ARTICLE.ID),
  SHOW_IN_HOME_PAGE("showInHomePage", ARTICLE.SHOW_IN_HOME_PAGE),
  ARCHIVED("archived", ARTICLE.ARCHIVED),

  TAG_ID("tag:id", TAG.ID),
  TAG_NAME("tag:name", TAG.NAME),
  SERVICE_ID("service:id", MEDICAL_SERVICE.ID);

  private final String restAlias;
  private final Field<?> jooqField;

  ArticleField(String restAlias, Field<?> jooqField) {
    this.restAlias = restAlias;
    this.jooqField = jooqField;
  }
}
