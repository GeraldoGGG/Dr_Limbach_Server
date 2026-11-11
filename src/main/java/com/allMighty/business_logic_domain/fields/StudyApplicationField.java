package com.allMighty.business_logic_domain.fields;

import static com.example.jooq.generated.Tables.PCOS_STUDY_APPLICATION;
import static com.example.jooq.generated.Tables.STUDY_APPLICATION_CONDITION;
import static com.example.jooq.generated.Tables.STUDY_APPLICATION_MEDICATION;

import com.allMighty.global_operation.filter.FilterableField;
import lombok.Getter;
import org.jooq.Field;

@Getter
public enum StudyApplicationField implements FilterableField {
  ID("id", PCOS_STUDY_APPLICATION.ID),
  FIRST_NAME("firstName", PCOS_STUDY_APPLICATION.FIRST_NAME),
  LAST_NAME("lastName", PCOS_STUDY_APPLICATION.LAST_NAME),
  AGE("age", PCOS_STUDY_APPLICATION.AGE),
  EMAIL("email", PCOS_STUDY_APPLICATION.EMAIL),
  STUDY_GROUP("studyGroup", PCOS_STUDY_APPLICATION.STUDY_GROUP),
  CREATED_AT("createdAt", PCOS_STUDY_APPLICATION.CREATED_AT),

  CONDITION("condition", STUDY_APPLICATION_CONDITION.HEALTH_CONDITION),
  MEDICATION("medication", STUDY_APPLICATION_MEDICATION.MEDICATION);

  private final String restAlias;
  private final Field<?> jooqField;

  StudyApplicationField(String restAlias, Field<?> jooqField) {
    this.restAlias = restAlias;
    this.jooqField = jooqField;
  }
}
