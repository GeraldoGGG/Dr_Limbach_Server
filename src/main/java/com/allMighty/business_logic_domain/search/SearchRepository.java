package com.allMighty.business_logic_domain.search;

import static com.allMighty.business_logic_domain.search.SearchMapper.*;
import static com.example.jooq.generated.tables.Analysis.ANALYSIS;
import static com.example.jooq.generated.tables.Article.ARTICLE;
import static com.example.jooq.generated.tables.Category.CATEGORY;
import static com.example.jooq.generated.tables.Event.EVENT;
import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;
import static com.example.jooq.generated.tables.Package.PACKAGE;
import static org.jooq.impl.DSL.*;
import static org.jooq.impl.DSL.select;

import com.allMighty.business_logic_domain.search.model.SearchRequestDTO;
import com.allMighty.business_logic_domain.search.model.SearchResponseDTO;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SearchRepository {
  private final DSLContext dsl;

  private final SearchMapper.SearchJooqMapper searchJooqMapper =
      new SearchMapper.SearchJooqMapper();

  /*  public SearchResponseDTO search(SearchRequestDTO searchRequestDTO) {

    String searchWord = searchRequestDTO.searchWord();

    return dsl.select(
            multiset(
                    select(EVENT.ID, EVENT.TITLE)
                            .from(EVENT)
                            .where(field("similarity(title, ?)", Double.class, searchWord).gt(0.2))  // Explicit similarity threshold
                            .orderBy(field("similarity(title, ?)", Double.class, searchWord).desc())) // Order by similarity

                .as(EVENT_SEARCH_KEYWORD))
        .fetchOne(searchJooqMapper);
  }*/

  public SearchResponseDTO search(SearchRequestDTO searchRequestDTO) {

    String searchWord = searchRequestDTO.searchWord();

    return dsl.select(
            // search in events
            multiset(
                    select(EVENT.ID, EVENT.TITLE)
                        .from(EVENT)
                        .where(EVENT.TITLE.likeIgnoreCase("%" + searchWord + "%")))
                .as(EVENT_SEARCH_KEYWORD),
            // search in analysis

            multiset(
                    select(ANALYSIS.ID, ANALYSIS.MEDICAL_NAME)
                        .from(ANALYSIS)
                        .where(ANALYSIS.MEDICAL_NAME.likeIgnoreCase("%" + searchWord + "%")))
                .as(ANALYSIS_SEARCH_KEYWORD),

            // search in services
            multiset(
                    select(MEDICAL_SERVICE.ID, MEDICAL_SERVICE.TITLE)
                        .from(MEDICAL_SERVICE)
                        .where(MEDICAL_SERVICE.TITLE.likeIgnoreCase("%" + searchWord + "%")))
                .as(SERVICES_SEARCH_KEYWORD),

            // search in category
            multiset(
                    select(CATEGORY.ID, CATEGORY.NAME)
                        .from(CATEGORY)
                        .where(CATEGORY.NAME.likeIgnoreCase("%" + searchWord + "%")))
                .as(CATEGORY_SEARCH_KEYWORD),

            // search in articles
            multiset(
                    select(ARTICLE.ID, ARTICLE.TITLE)
                        .from(ARTICLE)
                        .where(ARTICLE.TITLE.likeIgnoreCase("%" + searchWord + "%")))
                .as(ARTICLES_SEARCH_KEYWORD),

            // search in packages
            multiset(
                    select(PACKAGE.ID, PACKAGE.NAME)
                        .from(PACKAGE)
                        .where(PACKAGE.NAME.likeIgnoreCase("%" + searchWord + "%")))
                .as(PACKAGE_SEARCH_KEYWORD))


        .fetchOne(searchJooqMapper);
  }
}
