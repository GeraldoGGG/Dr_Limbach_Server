package com.allMighty.business_logic_domain.study_application;

import static com.allMighty.business_logic_domain.study_application.StudyApplicationMapper.CONDITIONS;
import static com.allMighty.business_logic_domain.study_application.StudyApplicationMapper.MEDICATIONS;
import static com.example.jooq.generated.Tables.PCOS_STUDY_APPLICATION;
import static com.example.jooq.generated.Tables.STUDY_APPLICATION_CONDITION;
import static com.example.jooq.generated.Tables.STUDY_APPLICATION_MEDICATION;

import com.allMighty.global_operation.response.page.PageDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectSelectStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudyApplicationRepository {
  private final DSLContext dsl;
  private final StudyApplicationMapper.StudyApplicationDtoJooqMapper studyApplicationDtoJooqMapper =
      new StudyApplicationMapper.StudyApplicationDtoJooqMapper();

  public Long count() {
    return count(new ArrayList<>());
  }

  public Long count(List<Condition> conditions) {
    SelectSelectStep<?> select = dsl.select(DSL.countDistinct(PCOS_STUDY_APPLICATION.ID));
    SelectOnConditionStep<?> fromStatement = getFromStatement(select);
    return fromStatement.where(conditions).fetchSingleInto(Long.class);
  }

  public List<StudyApplicationDTO> getAllStudyApplications(
      List<Condition> conditions, PageDescriptor pageDescriptor) {

    pageDescriptor = Optional.ofNullable(pageDescriptor).orElse(PageDescriptor.maxDataDescriptor());

    Long offset = pageDescriptor.getOffset();
    Long pageSize = pageDescriptor.getPageSize();

    var selectStatement = getSelect();

    var from = getFromStatement(selectStatement);

    return from.where(conditions)
        .groupBy(
            PCOS_STUDY_APPLICATION.ID,
            PCOS_STUDY_APPLICATION.FIRST_NAME,
            PCOS_STUDY_APPLICATION.LAST_NAME,
            PCOS_STUDY_APPLICATION.AGE,
            PCOS_STUDY_APPLICATION.EMAIL,
            PCOS_STUDY_APPLICATION.STUDY_GROUP,
            PCOS_STUDY_APPLICATION.CREATED_AT)
        .orderBy(PCOS_STUDY_APPLICATION.CREATED_AT.desc(), PCOS_STUDY_APPLICATION.ID.desc())
        .offset(offset)
        .limit(pageSize)
        .fetch(studyApplicationDtoJooqMapper);
  }

  private SelectSelectStep<?> getSelect() {
    return dsl.select(
        PCOS_STUDY_APPLICATION.ID,
        PCOS_STUDY_APPLICATION.FIRST_NAME,
        PCOS_STUDY_APPLICATION.LAST_NAME,
        PCOS_STUDY_APPLICATION.AGE,
        PCOS_STUDY_APPLICATION.EMAIL,
        PCOS_STUDY_APPLICATION.STUDY_GROUP,
        PCOS_STUDY_APPLICATION.CREATED_AT,
        CONDITIONS,
        MEDICATIONS);
  }

  private static SelectOnConditionStep<?> getFromStatement(SelectSelectStep<?> select) {
    return select
        .from(PCOS_STUDY_APPLICATION)

        .leftJoin(STUDY_APPLICATION_CONDITION)
        .on(PCOS_STUDY_APPLICATION.ID.eq(STUDY_APPLICATION_CONDITION.APPLICATION_ID))

        .leftJoin(STUDY_APPLICATION_MEDICATION)
        .on(PCOS_STUDY_APPLICATION.ID.eq(STUDY_APPLICATION_MEDICATION.APPLICATION_ID));
  }

  public Optional<StudyApplicationDTO> findById(Long id) {
    Condition eq = PCOS_STUDY_APPLICATION.ID.eq(id);
    return getAllStudyApplications(Collections.singletonList(eq), null).stream().findFirst();
  }

  public boolean existsByEmail(String email) {
    return dsl.fetchExists(
        dsl.selectOne()
            .from(PCOS_STUDY_APPLICATION)
            .where(DSL.lower(PCOS_STUDY_APPLICATION.EMAIL).eq(DSL.lower(DSL.val(email)))));
  }
}
