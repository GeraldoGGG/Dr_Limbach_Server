package com.allMighty.business_logic_domain.medical_service;

import static com.allMighty.business_logic_domain.tag.TagMapper.TAGS_KEYWORD;
import static com.allMighty.util.JooqMapperProperty.ANALYSIS_IDS_KEYWORD;
import static com.allMighty.util.JooqMapperProperty.ARTICLES_IDS_KEYWORD;
import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;
import static com.example.jooq.generated.tables.MedicalServiceAnalysis.MEDICAL_SERVICE_ANALYSIS;
import static com.example.jooq.generated.tables.MedicalServiceArticle.MEDICAL_SERVICE_ARTICLE;
import static com.example.jooq.generated.tables.Tag.TAG;
import static com.example.jooq.generated.tables.TagMedicalService.TAG_MEDICAL_SERVICE;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

import com.allMighty.enitity.MedicalServiceEntity;
import com.allMighty.global_operation.response.page.PageDescriptor;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MedicalServiceRepository {

  private final DSLContext dsl;
  private final MedicalServiceMapper.MedicalServiceJooqMapper medicalServiceJooqMapper =
      new MedicalServiceMapper.MedicalServiceJooqMapper();

  public Long count() {
    return count(new ArrayList<>());
  }

  public Long count(List<Condition> conditions) {
    return dsl.select(DSL.countDistinct(MEDICAL_SERVICE.ID))
        .from(MEDICAL_SERVICE)
        .leftJoin(TAG_MEDICAL_SERVICE)
        .on(MEDICAL_SERVICE.ID.eq(TAG_MEDICAL_SERVICE.MEDICAL_SERVICE_ID))
        .leftJoin(TAG)
        .on(TAG.ID.eq(TAG_MEDICAL_SERVICE.TAG_ID))
        .where(conditions)
        .fetchSingleInto(Long.class);
  }

  public List<MedicalServiceEntity> getAllMedicalServices(
      List<Condition> conditions, PageDescriptor pageDescriptor) {
    if (pageDescriptor == null) {
      pageDescriptor = PageDescriptor.maxDataDescriptor();
    }
    Long offset = pageDescriptor.getOffset();
    Long pageSize = pageDescriptor.getPageSize();

    return dsl.select(
            MEDICAL_SERVICE.ID,
            MEDICAL_SERVICE.TITLE,
            MEDICAL_SERVICE.CONTENT,
            MEDICAL_SERVICE.ARCHIVED,
            MEDICAL_SERVICE.SHOW_IN_HOME_PAGE,
            multiset(
                    select(MEDICAL_SERVICE_ARTICLE.ARTICLE_ID)
                        .from(MEDICAL_SERVICE_ARTICLE)
                        .where(MEDICAL_SERVICE_ARTICLE.MEDICAL_SERVICE_ID.eq(MEDICAL_SERVICE.ID)))
                .convertFrom(
                    records -> records.getValues(MEDICAL_SERVICE_ARTICLE.ARTICLE_ID, Long.class))
                .as(ARTICLES_IDS_KEYWORD),
            multiset(
                    select(MEDICAL_SERVICE_ANALYSIS.ANALYSIS_ID)
                        .from(MEDICAL_SERVICE_ANALYSIS)
                        .where(MEDICAL_SERVICE_ANALYSIS.MEDICAL_SERVICE_ID.eq(MEDICAL_SERVICE.ID)))
                .convertFrom(
                    records -> records.getValues(MEDICAL_SERVICE_ANALYSIS.ANALYSIS_ID, Long.class))
                .as(ANALYSIS_IDS_KEYWORD),
            multiset(
                    select(TAG.ID, TAG.NAME)
                        .from(TAG)
                        .leftJoin(TAG_MEDICAL_SERVICE)
                        .on(TAG.ID.eq(TAG_MEDICAL_SERVICE.TAG_ID))
                        .where(TAG_MEDICAL_SERVICE.MEDICAL_SERVICE_ID.eq(MEDICAL_SERVICE.ID)))
                .as(TAGS_KEYWORD))
        .from(MEDICAL_SERVICE)
        .leftJoin(TAG_MEDICAL_SERVICE)
        .on(MEDICAL_SERVICE.ID.eq(TAG_MEDICAL_SERVICE.MEDICAL_SERVICE_ID))
        .leftJoin(TAG)
        .on(TAG.ID.eq(TAG_MEDICAL_SERVICE.TAG_ID))
        .where(conditions)
        .groupBy(
            MEDICAL_SERVICE.ID,
            MEDICAL_SERVICE.TITLE,
            MEDICAL_SERVICE.CONTENT,
            MEDICAL_SERVICE.ARCHIVED,
            MEDICAL_SERVICE.REMOVED)
        .offset(offset)
        .limit(pageSize)
        .fetch(medicalServiceJooqMapper);
  }

  public Optional<MedicalServiceEntity> findById(Long id) {
    Condition eq = MEDICAL_SERVICE.ID.eq(id);
    List<MedicalServiceEntity> medicalServices =
        getAllMedicalServices(Collections.singletonList(eq), null);
    return medicalServices.stream().findFirst();
  }
}
