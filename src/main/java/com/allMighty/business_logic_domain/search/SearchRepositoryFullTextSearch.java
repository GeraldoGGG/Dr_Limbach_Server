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

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
@RequiredArgsConstructor
@Repository
public class SearchRepositoryFullTextSearch {
    private final DSLContext dsl;
    private final SearchMapper.SearchJooqMapper searchJooqMapper = new SearchMapper.SearchJooqMapper();

    public SearchResponseDTO search(SearchRequestDTO searchRequestDTO) {
        String searchWord = searchRequestDTO.searchWord();

        return dsl.select(
                        // Search in events using full-text search and partial match (ILIKE)
                        multiset(
                                select(EVENT.ID, EVENT.TITLE, tsRank(EVENT.TITLE, searchWord).as("rank"))
                                        .from(EVENT)
                                        .where(fullTextSearchCondition(EVENT.TITLE, searchWord))
                                        .orderBy(tsRank(EVENT.TITLE, searchWord).desc())
                        ).as(EVENT_SEARCH_KEYWORD),

                        // Search in analysis using full-text search and partial match (ILIKE)
                        multiset(
                                select(ANALYSIS.ID, ANALYSIS.MEDICAL_NAME, tsRank(ANALYSIS.MEDICAL_NAME, searchWord).as("rank"))
                                        .from(ANALYSIS)
                                        .leftJoin(ANALYSIS_DETAIL)
                                        .on(ANALYSIS.ID.eq(ANALYSIS_DETAIL.ANALYSIS_ID))
                                        .leftJoin(CATEGORY)
                                        .on(CATEGORY.ID.eq(ANALYSIS.CATEGORY_ID))
                                        .leftJoin(PACKAGE_ANALYSIS)
                                        .on(ANALYSIS.ID.eq(PACKAGE_ANALYSIS.ANALYSIS_ID))
                                        .leftJoin(PACKAGE)
                                        .on(PACKAGE.ID.eq(PACKAGE_ANALYSIS.PACKAGE_ID))
                                        .where(getAnalysisSearchCondition(searchWord))
                                        .groupBy(ANALYSIS.ID, ANALYSIS.MEDICAL_NAME)
                                        .orderBy(tsRank(ANALYSIS.MEDICAL_NAME, searchWord).desc(), ANALYSIS.ID) // Order by rank and ID to ensure consistent results
                        ).as(ANALYSIS_SEARCH_KEYWORD),

                        // Search in services using full-text search and partial match (ILIKE)
                        multiset(
                                select(MEDICAL_SERVICE.ID, MEDICAL_SERVICE.TITLE, tsRank(MEDICAL_SERVICE.TITLE, searchWord).as("rank"))
                                        .from(MEDICAL_SERVICE)
                                        .where(fullTextSearchCondition(MEDICAL_SERVICE.TITLE, searchWord))
                                        .orderBy(tsRank(MEDICAL_SERVICE.TITLE, searchWord).desc())
                        ).as(SERVICES_SEARCH_KEYWORD),

                        // Search in articles using full-text search and partial match (ILIKE)
                        multiset(
                                select(ARTICLE.ID, ARTICLE.TITLE, tsRank(ARTICLE.TITLE, searchWord).as("rank"))
                                        .from(ARTICLE)
                                        .where(fullTextSearchCondition(ARTICLE.TITLE, searchWord))
                                        .orderBy(tsRank(ARTICLE.TITLE, searchWord).desc())
                        ).as(ARTICLES_SEARCH_KEYWORD))
                .fetchOne(searchJooqMapper);
    }

    private static Condition fullTextSearchCondition(Field<String> field, String searchWord) {
        return DSL.condition(
                "to_tsvector('simple', {0}) @@ plainto_tsquery('simple', {1})", field, searchWord
        ).or(DSL.condition("{0} ILIKE {1}", field, "%" + searchWord + "%"));
    }

    private static Condition getAnalysisSearchCondition(String searchWord) {
        return or(
                fullTextSearchCondition(ANALYSIS.MEDICAL_NAME, searchWord),
                fullTextSearchCondition(CATEGORY.NAME, searchWord),
                fullTextSearchCondition(PACKAGE.NAME, searchWord),
              //  fullTextSearchCondition(ANALYSIS_DETAIL.KEY_VALUE, searchWord),
                fullTextSearchCondition(ANALYSIS_DETAIL.STRING_VALUE, searchWord)
        );
    }

    private static Field<Double> tsRank(Field<String> field, String searchWord) {
        return DSL.field("ts_rank(to_tsvector('simple', {0}), plainto_tsquery('simple', {1}))", Double.class, field, searchWord);
    }
}
