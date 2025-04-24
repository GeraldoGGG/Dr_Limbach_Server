package com.allMighty.business_logic_domain.search;

import static com.example.jooq.generated.tables.Analysis.ANALYSIS;
import static com.example.jooq.generated.tables.Article.ARTICLE;
import static com.example.jooq.generated.tables.Category.CATEGORY;
import static com.example.jooq.generated.tables.Event.EVENT;
import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;
import static com.example.jooq.generated.tables.Package.PACKAGE;

import com.allMighty.business_logic_domain.search.model.SearchResponseDTO;
import com.allMighty.business_logic_domain.search.model.SearchUnitResponseDTO;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;

public class SearchMapper {
  public static final String EVENT_SEARCH_KEYWORD = "events";
  public static final String ANALYSIS_SEARCH_KEYWORD = "analysis";
  public static final String SERVICES_SEARCH_KEYWORD = "services";
  public static final String ARTICLES_SEARCH_KEYWORD = "articles";
  public static final String PACKAGE_SEARCH_KEYWORD = "packages";
  public static final String CATEGORY_SEARCH_KEYWORD = "categories";

  static class SearchJooqMapper implements RecordMapper<Record, SearchResponseDTO> {

    @Override
    public SearchResponseDTO map(Record searchRecord) {
      SearchResponseDTO dto = new SearchResponseDTO();

      // Events
      Result<Record> eventsResult = searchRecord.get(EVENT_SEARCH_KEYWORD, Result.class);
      dto.setEventsFound(mapEvents(eventsResult));

      // Analysis
      Result<Record> analysisResult = searchRecord.get(ANALYSIS_SEARCH_KEYWORD, Result.class);
      dto.setAnalysesFound(mapAnalysis(analysisResult));

      // Services
      Result<Record> servicesResult = searchRecord.get(SERVICES_SEARCH_KEYWORD, Result.class);
      dto.setServicesFound(mapServices(servicesResult));

      // Articles
      Result<Record> articlesResult = searchRecord.get(ARTICLES_SEARCH_KEYWORD, Result.class);
      dto.setArticlesFound(mapArticles(articlesResult));

      return dto;
    }

    // Mappers
    private static List<SearchUnitResponseDTO> mapEvents(Result<Record> result) {
      return mapBasicResult(result, EVENT.ID, EVENT.TITLE);
    }

    private static List<SearchUnitResponseDTO> mapAnalysis(Result<Record> result) {
      return mapBasicResult(result, ANALYSIS.ID, ANALYSIS.MEDICAL_NAME);
    }

    private static List<SearchUnitResponseDTO> mapServices(Result<Record> result) {
      return mapBasicResult(result, MEDICAL_SERVICE.ID, MEDICAL_SERVICE.TITLE);
    }

    private static List<SearchUnitResponseDTO> mapCategories(Result<Record> result) {
      return mapBasicResult(result, CATEGORY.ID, CATEGORY.NAME);
    }

    private static List<SearchUnitResponseDTO> mapArticles(Result<Record> result) {
      return mapBasicResult(result, ARTICLE.ID, ARTICLE.TITLE);
    }

    private static List<SearchUnitResponseDTO> mapPackages(Result<Record> result) {
      return mapBasicResult(result, PACKAGE.ID, PACKAGE.NAME);
    }

    private static List<SearchUnitResponseDTO> mapBasicResult(
        Result<Record> result, Field idField, Field titleField) {

      List<SearchUnitResponseDTO> list = new ArrayList<>();
      if (result == null) return list;

      for (Record record : result) {
        SearchUnitResponseDTO dto = new SearchUnitResponseDTO();
        dto.setId(record.get(idField, Long.class));
        dto.setTitle(String.valueOf(record.get(titleField, String.class)));
        list.add(dto);
      }

      return list;
    }
  }
}
