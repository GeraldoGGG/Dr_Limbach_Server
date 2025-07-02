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
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import com.allMighty.util.SearchConditionGenerator;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SelectField;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SearchRepository {
  private final DSLContext dsl;

  private final SearchMapper.SearchJooqMapper searchJooqMapper =
      new SearchMapper.SearchJooqMapper();

  private final SearchConditionGenerator conditionGenerator = new SearchConditionGenerator();

  public SearchResponseDTO search(SearchRequestDTO searchRequestDTO) {

    if (searchRequestDTO == null || searchRequestDTO.getSearchWord() == null) {
      throw new BadRequestException("Search word cannot be null");
    }

    conditionGenerator.setSearchRequestDTO(searchRequestDTO);
    searchJooqMapper.setSearchRequestDTO(searchRequestDTO);

    List<SelectField<?>> selectFields = new ArrayList<>();

    // EVENTS
    if (searchRequestDTO.includeEvents()) {
      selectFields.add(
          multiset(
                  select(EVENT.ID, EVENT.TITLE)
                      .from(EVENT)
                      .where(conditionGenerator.getEventSearchCondition()))
              .as(EVENT_SEARCH_KEYWORD));
    }

    // ANALYSIS
    if (searchRequestDTO.includeAnalysis()) {
      selectFields.add(
          multiset(
                  selectDistinct(ANALYSIS.ID, ANALYSIS.MEDICAL_NAME, CATEGORY.NAME)
                      .from(ANALYSIS)
                      .leftJoin(ANALYSIS_DETAIL)
                      .on(ANALYSIS.ID.eq(ANALYSIS_DETAIL.ANALYSIS_ID))
                      .leftJoin(CATEGORY)
                      .on(CATEGORY.ID.eq(ANALYSIS.CATEGORY_ID))
                      .leftJoin(PACKAGE_ANALYSIS)
                      .on(ANALYSIS.ID.eq(PACKAGE_ANALYSIS.ANALYSIS_ID))
                      .leftJoin(PACKAGE)
                      .on(PACKAGE.ID.eq(PACKAGE_ANALYSIS.PACKAGE_ID))
                      .where(conditionGenerator.getAnalysisSearchCondition()))
              .as(ANALYSIS_SEARCH_KEYWORD));
    }

    // SERVICES
    if (searchRequestDTO.includeServices()) {
      selectFields.add(
          multiset(
                  select(MEDICAL_SERVICE.ID, MEDICAL_SERVICE.TITLE)
                      .from(MEDICAL_SERVICE)
                      .where(conditionGenerator.getSeriviceSearchCondition()))
              .as(SERVICES_SEARCH_KEYWORD));
    }

    // ARTICLES
    if (searchRequestDTO.includeArticles()) {
      selectFields.add(
          multiset(
                  select(ARTICLE.ID, ARTICLE.TITLE)
                      .from(ARTICLE)
                      .where(conditionGenerator.getArticleSearchCondition()))
              .as(ARTICLES_SEARCH_KEYWORD));
    }

    return dsl.select(selectFields).fetchOne(searchJooqMapper);
  }
}
