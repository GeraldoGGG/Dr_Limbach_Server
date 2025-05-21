package com.allMighty.business_logic_domain.analysis.analysis_category;

import static com.allMighty.util.JooqMapperProperty.ANALYSIS_IDS_KEYWORD;
import static com.example.jooq.generated.tables.Analysis.ANALYSIS;
import static com.example.jooq.generated.tables.Category.CATEGORY;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

import com.allMighty.enitity.analysis.AnalysisCategoryEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnalysisCategoryRepository {
  private final DSLContext dsl;

  private final AnalysisCategoryMapper.AnalysisCategoryJooqMapper analysisCategoryJooqMapper =
      new AnalysisCategoryMapper.AnalysisCategoryJooqMapper();

  public List<AnalysisCategoryEntity> getAllAnalysisCategories() {
    return getAllAnalysisCategories(new ArrayList<>());
  }

  public List<AnalysisCategoryEntity> getAllAnalysisCategories(Condition condition) {
    return getAllAnalysisCategories(Collections.singletonList(condition));
  }

  public List<AnalysisCategoryEntity> getAllAnalysisCategories(List<Condition> conditions) {
    List<AnalysisCategoryEntity> fetch = dsl.select(
                    CATEGORY.ID,
                    CATEGORY.NAME,
                    CATEGORY.ARCHIVED,
                    multiset(select(ANALYSIS.ID).from(ANALYSIS).where(ANALYSIS.CATEGORY_ID.eq(CATEGORY.ID)))
                            .convertFrom(records -> records.getValues(ANALYSIS.ID, Long.class))
                            .as(ANALYSIS_IDS_KEYWORD))
            .from(CATEGORY)
            .where(conditions)
            .fetch(analysisCategoryJooqMapper);
    return fetch;
  }

  public Optional<AnalysisCategoryEntity> getAnalysisCategoryById(Long id) {
    Condition idCondition = CATEGORY.ID.eq(id);
    return getAllAnalysisCategories(Collections.singletonList(idCondition)).stream().findFirst();
  }
}
