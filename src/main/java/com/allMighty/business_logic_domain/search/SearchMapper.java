package com.allMighty.business_logic_domain.search;

import static com.example.jooq.generated.tables.Analysis.ANALYSIS;
import static com.example.jooq.generated.tables.Article.ARTICLE;
import static com.example.jooq.generated.tables.Category.CATEGORY;
import static com.example.jooq.generated.tables.Event.EVENT;
import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;
import static com.example.jooq.generated.tables.Package.PACKAGE;

import com.allMighty.business_logic_domain.search.model.SearchAnalysisResponseDTO;
import com.allMighty.business_logic_domain.search.model.SearchRequestDTO;
import com.allMighty.business_logic_domain.search.model.SearchResponseDTO;
import com.allMighty.business_logic_domain.search.model.SearchUnitResponseDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
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

  @Setter
  static class SearchJooqMapper implements RecordMapper<Record, SearchResponseDTO> {
    private SearchRequestDTO searchRequestDTO;

    @Override
    public SearchResponseDTO map(Record searchRecord) {
      SearchResponseDTO dto = new SearchResponseDTO();
      searchRequestDTO = (searchRequestDTO == null) ? new SearchRequestDTO() : searchRequestDTO;

      // EVENTS
      if (searchRequestDTO.includeEvents()) {
        Result<Record> eventsResult = searchRecord.get(EVENT_SEARCH_KEYWORD, Result.class);
        dto.setEventsFound(mapEvents(eventsResult));
      }

      // ANALYSIS
      if (searchRequestDTO.includeAnalysis()) {
        Result<Record> analysisResult = searchRecord.get(ANALYSIS_SEARCH_KEYWORD, Result.class);
        dto.setAnalysesFound(mapAnalysis(analysisResult));
      }

      // SERVICES
      if (searchRequestDTO.includeServices()) {
        Result<Record> servicesResult = searchRecord.get(SERVICES_SEARCH_KEYWORD, Result.class);
        dto.setServicesFound(mapServices(servicesResult));
      }

      // ARTICLES
      if (searchRequestDTO.includeArticles()) {
        Result<Record> articlesResult = searchRecord.get(ARTICLES_SEARCH_KEYWORD, Result.class);
        dto.setArticlesFound(mapArticles(articlesResult));
      }

      return dto;
    }

    // Mappers
    private static List<SearchUnitResponseDTO> mapEvents(Result<Record> result) {
      return mapBasicResult(result, EVENT.ID, EVENT.TITLE);
    }

    private static List<SearchAnalysisResponseDTO> mapAnalysis(Result<Record> result) {
      return mapAnalysisResult(result);
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

    private static List<SearchAnalysisResponseDTO> mapAnalysisResult(Result<Record> result) {

      List<SearchAnalysisResponseDTO> list = new ArrayList<>();
      if (result == null) return list;

      for (Record record : result) {
        SearchAnalysisResponseDTO dto = new SearchAnalysisResponseDTO();
        dto.setId(record.get(ANALYSIS.ID));
        dto.setTitle(record.get(ANALYSIS.MEDICAL_NAME));
        dto.setCategoryName(record.get(CATEGORY.NAME));
        list.add(dto);
      }

      return list;
    }
  }
}
