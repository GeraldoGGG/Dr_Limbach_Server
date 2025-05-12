package com.allMighty.business_logic_domain.search;

import static com.allMighty.business_logic_domain.search.SearchMapper.*;
import static com.example.jooq.generated.tables.Analysis.ANALYSIS;
import static com.example.jooq.generated.tables.AnalysisDetail.ANALYSIS_DETAIL;
import static com.example.jooq.generated.tables.Article.ARTICLE;
import static com.example.jooq.generated.tables.Category.CATEGORY;
import static com.example.jooq.generated.tables.Event.EVENT;
import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;
import static com.example.jooq.generated.tables.Package.PACKAGE;
import static com.example.jooq.generated.tables.PackageAnalysis.PACKAGE_ANALYSIS;
import static org.jooq.impl.DSL.*;
import static org.jooq.impl.DSL.select;

import com.allMighty.business_logic_domain.search.model.SearchRequestDTO;
import com.allMighty.business_logic_domain.search.model.SearchResponseDTO;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Param;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SearchRepository {
  private final DSLContext dsl;

  private final SearchMapper.SearchJooqMapper searchJooqMapper =
      new SearchMapper.SearchJooqMapper();

  public SearchResponseDTO search(SearchRequestDTO searchRequestDTO) {

    String searchWord = searchRequestDTO.searchWord();

    return dsl.select(
            // search in events
            multiset(
                    select(EVENT.ID, EVENT.TITLE)
                        .from(EVENT)
                        .where(getEventSearchCondition(searchWord)))
                .as(EVENT_SEARCH_KEYWORD),
            // search in analysis

            multiset(
                    selectDistinct(ANALYSIS.ID, ANALYSIS.MEDICAL_NAME)
                        .from(ANALYSIS)
                        .leftJoin(ANALYSIS_DETAIL)
                        .on(ANALYSIS.ID.eq(ANALYSIS_DETAIL.ANALYSIS_ID))
                        .leftJoin(CATEGORY)
                        .on(CATEGORY.ID.eq(ANALYSIS.CATEGORY_ID))
                        .leftJoin(PACKAGE_ANALYSIS)
                        .on(ANALYSIS.ID.eq(PACKAGE_ANALYSIS.ANALYSIS_ID))
                        .leftJoin(PACKAGE)
                        .on(PACKAGE.ID.eq(PACKAGE_ANALYSIS.PACKAGE_ID))
                        .where(getAnalysisSearchCondition(searchWord)))
                .as(ANALYSIS_SEARCH_KEYWORD),

            // search in services
            multiset(
                    select(MEDICAL_SERVICE.ID, MEDICAL_SERVICE.TITLE)
                        .from(MEDICAL_SERVICE)
                        .where(getSeriviceSearchCondition(searchWord)))
                .as(SERVICES_SEARCH_KEYWORD),

            // search in articles
            multiset(
                    select(ARTICLE.ID, ARTICLE.TITLE)
                        .from(ARTICLE)
                        .where(getArticleSearchCondition(searchWord)))
                .as(ARTICLES_SEARCH_KEYWORD))
        .fetchOne(searchJooqMapper);
  }

  private static Condition getAnalysisSearchCondition(String searchWord) {
    var conditions = new ArrayList<Condition>();
    // analysis
    conditions.add(ANALYSIS.MEDICAL_NAME.likeIgnoreCase(getSearch(searchWord)));
    conditions.add(ANALYSIS.SYNONYM.likeIgnoreCase(getSearch(searchWord)));
    conditions.add(CATEGORY.NAME.likeIgnoreCase(getSearch(searchWord)));
    conditions.add(PACKAGE.NAME.likeIgnoreCase(getSearch(searchWord)));
    // conditions.add(ANALYSIS_DETAIL.KEY_VALUE.likeIgnoreCase(getSearch(searchWord)));
    conditions.add(ANALYSIS_DETAIL.STRING_VALUE.likeIgnoreCase(getSearch(searchWord)));

    return or(conditions);
  }

  private static Condition getEventSearchCondition(String searchWord) {
    var conditions = new ArrayList<Condition>();
    conditions.add(EVENT.TITLE.likeIgnoreCase(getSearch(searchWord)));
    conditions.add(EVENT.ORGANIZATION.likeIgnoreCase(getSearch(searchWord)));
    return or(conditions);
  }

  private static Condition getArticleSearchCondition(String searchWord) {
    var conditions = new ArrayList<Condition>();
    conditions.add(ARTICLE.TITLE.likeIgnoreCase(getSearch(searchWord)));
    conditions.add(ARTICLE.AUTHOR.likeIgnoreCase(getSearch(searchWord)));
    return or(conditions);
  }

  private static Condition getSeriviceSearchCondition(String searchWord) {
    var conditions = new ArrayList<Condition>();
    conditions.add(MEDICAL_SERVICE.TITLE.likeIgnoreCase(getSearch(searchWord)));
    return or(conditions);
  }

  private static @NotNull Param<String> getSearch(String searchWord) {
    return inline("%" + searchWord + "%");
  }
}
