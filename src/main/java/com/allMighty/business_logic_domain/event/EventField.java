package com.allMighty.business_logic_domain.event;

import com.allMighty.global_operation.filter.FilterableField;
import lombok.Getter;
import org.jooq.Field;

import static com.example.jooq.generated.tables.Event.EVENT;

@Getter
public enum EventField implements FilterableField {
  ID("id", EVENT.ID),
  TITLE("title", EVENT.TITLE),
  ARCHIVED("archived", EVENT.ARCHIVED);

  private final String restAlias;
  private final Field<?> jooqField;

  EventField(String restAlias, Field<?> jooqField) {
    this.restAlias = restAlias;
    this.jooqField = jooqField;
  }
}
