package com.allMighty.business_logic_domain.analysis;

import static com.allMighty.business_logic_domain.analysis.mapper.AnalysisDetailMapper.DETAILS_KEYWORD;
import static com.allMighty.business_logic_domain.tag.TagMapper.TAGS_KEYWORD;
import static com.example.jooq.generated.tables.Analysis.ANALYSIS;
import static com.example.jooq.generated.tables.AnalysisDetail.ANALYSIS_DETAIL;
import static com.example.jooq.generated.tables.Category.CATEGORY;
import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;
import static com.example.jooq.generated.tables.MedicalServiceAnalysis.MEDICAL_SERVICE_ANALYSIS;
import static com.example.jooq.generated.tables.Package.PACKAGE;
import static com.example.jooq.generated.tables.PackageAnalysis.PACKAGE_ANALYSIS;
import static com.example.jooq.generated.tables.Tag.TAG;
import static com.example.jooq.generated.tables.TagAnalysis.TAG_ANALYSIS;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

import com.allMighty.business_logic_domain.analysis.mapper.AnalysisMapper;
import com.allMighty.business_logic_domain.analysis.mapper.AnalysisMapper.AnalysisJooqMapper;
import com.allMighty.business_logic_domain.general.EntityIdDTO;
import com.allMighty.enitity.analysis.AnalysisEntity;
import com.allMighty.global_operation.response.page.PageDescriptor;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnalysisRepository {

  private final DSLContext dsl;
  private final AnalysisMapper.AnalysisJooqMapper analysisJooqMapper = new AnalysisJooqMapper();

  public Long count() {
    return count(new ArrayList<>());
  }

  public Long count(List<Condition> conditions) {
    SelectSelectStep<?> select = dsl.select(DSL.countDistinct(ANALYSIS.ID));
    SelectOnConditionStep<?> fromStatement = getFromStatement(select);
    return fromStatement.where(conditions).fetchSingleInto(Long.class);
  }

  public List<AnalysisEntity> getAllAnalyses(
      List<Condition> conditions, PageDescriptor pageDescriptor) {

    pageDescriptor = Optional.ofNullable(pageDescriptor).orElse(PageDescriptor.maxDataDescriptor());

    Long offset = pageDescriptor.getOffset();
    Long pageSize = pageDescriptor.getPageSize();

    var selectStatement = getSelect();

    var from = getFromStatement(selectStatement);

    return from.where(conditions)
        .groupBy(
            ANALYSIS.ID,
            ANALYSIS.MEDICAL_NAME,
            ANALYSIS.SYNONYM,
            ANALYSIS.PRICE,
            ANALYSIS.ARCHIVED,
            ANALYSIS.REMOVED,
            CATEGORY.ID,
            CATEGORY.NAME)
        .offset(offset)
        .limit(pageSize)
        .fetch(analysisJooqMapper);
  }

  private SelectSelectStep<?> getSelect() {
    return dsl.select(
        ANALYSIS.ID,
        ANALYSIS.MEDICAL_NAME,
        ANALYSIS.SYNONYM,
        ANALYSIS.PRICE,
        ANALYSIS.ARCHIVED,
        ANALYSIS.ISO_VERIFIED,
        CATEGORY.ID,
        CATEGORY.NAME,
        multiset(
                select(TAG.ID, TAG.NAME)
                    .from(TAG)
                    .leftJoin(TAG_ANALYSIS)
                    .on(TAG.ID.eq(TAG_ANALYSIS.TAG_ID))
                    .where(TAG_ANALYSIS.ANALYSIS_ID.eq(ANALYSIS.ID)))
            .as(TAGS_KEYWORD),
        multiset(
                select(
                        ANALYSIS_DETAIL.ID,
                        ANALYSIS_DETAIL.KEY_VALUE,
                        ANALYSIS_DETAIL.STRING_VALUE)
                    .from(ANALYSIS_DETAIL)
                    .where(ANALYSIS_DETAIL.ANALYSIS_ID.eq(ANALYSIS.ID)))
            .as(DETAILS_KEYWORD));
  }

  private static SelectOnConditionStep<?> getFromStatement(SelectSelectStep<?> select) {
    return select
        .from(ANALYSIS)

        // service
        .leftJoin(MEDICAL_SERVICE_ANALYSIS)
        .on(ANALYSIS.ID.eq(MEDICAL_SERVICE_ANALYSIS.ANALYSIS_ID))
        .leftJoin(MEDICAL_SERVICE)
        .on(MEDICAL_SERVICE.ID.eq(MEDICAL_SERVICE_ANALYSIS.MEDICAL_SERVICE_ID))

        // tags
        .leftJoin(TAG_ANALYSIS)
        .on(ANALYSIS.ID.eq(TAG_ANALYSIS.ANALYSIS_ID))
        .leftJoin(TAG)
        .on(TAG.ID.eq(TAG_ANALYSIS.TAG_ID))

        // package
        .leftJoin(PACKAGE_ANALYSIS)
        .on(ANALYSIS.ID.eq(PACKAGE_ANALYSIS.ANALYSIS_ID))
        .leftJoin(PACKAGE)
        .on(PACKAGE.ID.eq(PACKAGE_ANALYSIS.PACKAGE_ID))

        // CATEGORY
        .leftJoin(CATEGORY)
        .on(ANALYSIS.CATEGORY_ID.eq(CATEGORY.ID));
  }

  public Optional<AnalysisEntity> findById(Long id) {
    Condition eq = ANALYSIS.ID.eq(id);
    List<AnalysisEntity> analyses = getAllAnalyses(Collections.singletonList(eq), null);
    return analyses.stream().findFirst();
  }

  public List<EntityIdDTO> getSimpleAnalyses() {
    return dsl.select(ANALYSIS.ID, ANALYSIS.MEDICAL_NAME.as("name"))
        .from(ANALYSIS)
        .where(ANALYSIS.ARCHIVED.eq(false))
        .fetchInto(EntityIdDTO.class);
  }
}
