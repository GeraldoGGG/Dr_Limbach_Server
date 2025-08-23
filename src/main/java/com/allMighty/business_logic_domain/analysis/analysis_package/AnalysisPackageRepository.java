package com.allMighty.business_logic_domain.analysis.analysis_package;

import static com.allMighty.util.JooqMapperProperty.ANALYSIS_IDS_KEYWORD;
import static com.example.jooq.generated.tables.Package.PACKAGE;
import static com.example.jooq.generated.tables.PackageAnalysis.PACKAGE_ANALYSIS;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

import com.allMighty.enitity.analysis.AnalysisPackageEntity;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnalysisPackageRepository {
  private final DSLContext dsl;

  private final AnalysisPackageMapper.AnalysisPackageJooqMapper analysisPackageJooqMapper =
      new AnalysisPackageMapper.AnalysisPackageJooqMapper();

  public List<AnalysisPackageEntity> getAllAnalysisPackages(List<Condition> conditions) {

    return dsl.select(
            PACKAGE.ID,
            PACKAGE.NAME,
            PACKAGE.PRICE,
            PACKAGE.ARCHIVED,
            PACKAGE.SHOW_IN_HOME_PAGE,
            PACKAGE.DESCRIPTION,
            multiset(
                    select(PACKAGE_ANALYSIS.ANALYSIS_ID)
                        .from(PACKAGE_ANALYSIS)
                        .where(PACKAGE_ANALYSIS.PACKAGE_ID.eq(PACKAGE.ID)))
                .convertFrom(records -> records.getValues(PACKAGE_ANALYSIS.ANALYSIS_ID, Long.class))
                .as(ANALYSIS_IDS_KEYWORD))
        .from(PACKAGE)
        .where(conditions)
        .fetch(analysisPackageJooqMapper);
  }

  public Optional<AnalysisPackageEntity> getAnalysisPackageById(Long id) {
    Condition idCondition = PACKAGE.ID.eq(id);
    return getAllAnalysisPackages(Collections.singletonList(idCondition)).stream().findFirst();
  }
}
