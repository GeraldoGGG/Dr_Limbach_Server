package com.allMighty.util;

import static com.allMighty.global_operation.filter.JooqConditionBuilder.buildConditions;
import static com.allMighty.util.SearchUtil.normalize;
import static com.example.jooq.generated.tables.Analysis.ANALYSIS;
import static com.example.jooq.generated.tables.AnalysisDetail.ANALYSIS_DETAIL;
import static com.example.jooq.generated.tables.Article.ARTICLE;
import static com.example.jooq.generated.tables.Category.CATEGORY;
import static com.example.jooq.generated.tables.Event.EVENT;
import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;
import static com.example.jooq.generated.tables.Package.PACKAGE;
import static java.util.Collections.emptyList;
import static org.jooq.impl.DSL.or;

import java.util.ArrayList;
import java.util.List;

import com.allMighty.business_logic_domain.fields.AnalysisField;
import com.allMighty.business_logic_domain.search.model.SearchRequestDTO;
import com.allMighty.global_operation.filter.FilterParser;
import lombok.Getter;
import lombok.Setter;
import org.jooq.Condition;
import org.jooq.Field;

@Getter
@Setter
public class SearchConditionGenerator {
  private SearchRequestDTO searchRequestDTO;
  private final FilterParser<AnalysisField> analysisFieldFilterParser =
      new FilterParser<>(AnalysisField.values());

  @SafeVarargs
  public final List<Condition> getSearchConditions(Field<String>... fields) {
    searchRequestDTO = (searchRequestDTO == null) ? new SearchRequestDTO() : searchRequestDTO;

    var conditions = new ArrayList<Condition>();
    for (Field<String> field : fields) {
      conditions.add(normalize(field).likeIgnoreCase(normalize(searchRequestDTO.getSearchWord())));
    }
    return conditions;
  }

  public Condition getEventSearchCondition() {
    return or(getSearchConditions(EVENT.TITLE, EVENT.ORGANIZATION));
  }

  public Condition getAnalysisSearchCondition() {
    return or(
        getSearchConditions(
            ANALYSIS.MEDICAL_NAME,
            ANALYSIS.SYNONYM,
            CATEGORY.NAME,
            PACKAGE.NAME,
            ANALYSIS_DETAIL.STRING_VALUE));
  }

  public Condition getArticleSearchCondition() {
    return or(getSearchConditions(ARTICLE.TITLE, ARTICLE.AUTHOR));
  }

  public Condition getSeriviceSearchCondition() {
    return or(getSearchConditions(MEDICAL_SERVICE.TITLE));
  }

  public List<Condition> getAnalysisFilterCondition() {
    var searchFilter = searchRequestDTO.getSearchFilter();
    if (searchFilter == null) {
      return emptyList();
    }
    return buildConditions(searchFilter.getAnalysisFilters(), analysisFieldFilterParser);
  }
}
