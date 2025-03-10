package com.allMighty.business_logic_domain.analysis;

import static com.allMighty.business_logic_domain.analysis.mapper.AnalysisDetailMapper.DETAILS_KEYWORD;
import static com.allMighty.business_logic_domain.tag.TagMapper.TAGS_KEYWORD;
import static com.example.jooq.generated.tables.Analysis.ANALYSIS;
import static com.example.jooq.generated.tables.AnalysisDetail.ANALYSIS_DETAIL;
import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;
import static com.example.jooq.generated.tables.MedicalServiceAnalysis.MEDICAL_SERVICE_ANALYSIS;
import static com.example.jooq.generated.tables.Tag.TAG;
import static com.example.jooq.generated.tables.TagAnalysis.TAG_ANALYSIS;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

import com.allMighty.business_logic_domain.analysis.mapper.AnalysisMapper;
import com.allMighty.business_logic_domain.analysis.mapper.AnalysisMapper.AnalysisJooqMapper;
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
    return dsl.select(DSL.countDistinct(ANALYSIS.ID))
        .from(ANALYSIS)
        .leftJoin(TAG_ANALYSIS)
        .on(ANALYSIS.ID.eq(TAG_ANALYSIS.ANALYSIS_ID))
        .leftJoin(TAG)
        .on(TAG.ID.eq(TAG_ANALYSIS.TAG_ID))
        .where(conditions)
        .fetchSingleInto(Long.class);
  }

  public List<AnalysisEntity> getAllAnalyses(
      List<Condition> conditions, PageDescriptor pageDescriptor) {
    if (pageDescriptor == null) {
      pageDescriptor = PageDescriptor.maxDataDescriptor();
    }
    Long offset = pageDescriptor.getOffset();
    Long pageSize = pageDescriptor.getPageSize();

    return dsl.select(
            ANALYSIS.ID,
            ANALYSIS.VERSION,
            ANALYSIS.MEDICAL_NAME,
            ANALYSIS.SYNONYM,
            ANALYSIS.PRICE,
            ANALYSIS.ARCHIVED,
            ANALYSIS.REMOVED,
            multiset(
                    select(TAG.ID, TAG.NAME, TAG.VERSION)
                        .from(TAG)
                        .leftJoin(TAG_ANALYSIS)
                        .on(TAG.ID.eq(TAG_ANALYSIS.TAG_ID))
                        .where(TAG_ANALYSIS.ANALYSIS_ID.eq(ANALYSIS.ID)))
                .as(TAGS_KEYWORD),
            multiset(
                    select(
                            ANALYSIS_DETAIL.ID,
                            ANALYSIS_DETAIL.KEY_VALUE,
                            ANALYSIS_DETAIL.STRING_VALUE,
                            ANALYSIS_DETAIL.VERSION)
                        .from(ANALYSIS_DETAIL)
                        .where(ANALYSIS_DETAIL.ANALYSIS_ID.eq(ANALYSIS.ID)))
                .as(DETAILS_KEYWORD))
        .from(ANALYSIS)
        .leftJoin(MEDICAL_SERVICE_ANALYSIS)
        .on(ANALYSIS.ID.eq(MEDICAL_SERVICE_ANALYSIS.ANALYSIS_ID))
        .leftJoin(MEDICAL_SERVICE)
        .on(MEDICAL_SERVICE.ID.eq(MEDICAL_SERVICE_ANALYSIS.MEDICAL_SERVICE_ID))
        .leftJoin(TAG_ANALYSIS)
        .on(ANALYSIS.ID.eq(TAG_ANALYSIS.ANALYSIS_ID))
        .leftJoin(TAG)
        .on(TAG.ID.eq(TAG_ANALYSIS.TAG_ID))
        .where(conditions)
        .groupBy(
            ANALYSIS.ID,
            ANALYSIS.VERSION,
            ANALYSIS.MEDICAL_NAME,
            ANALYSIS.SYNONYM,
            ANALYSIS.PRICE,
            ANALYSIS.ARCHIVED,
            ANALYSIS.REMOVED)
        .offset(offset)
        .limit(pageSize)
        .fetch(analysisJooqMapper);
  }

  public Optional<AnalysisEntity> findById(Long id) {
    Condition eq = ANALYSIS.ID.eq(id);
    List<AnalysisEntity> analyses = getAllAnalyses(Collections.singletonList(eq), null);
    return analyses.stream().findFirst();
  }
}
