package com.allMighty.business_logic_domain.search;

import com.allMighty.business_logic_domain.search.ChatApiClient.ChatSuggestionResult;
import com.allMighty.business_logic_domain.search.model.AnalysisCatalogItemDTO;
import com.allMighty.business_logic_domain.search.model.ChatSuggestRequestDTO;
import com.allMighty.business_logic_domain.search.model.ChatSuggestResponseDTO;
import com.allMighty.business_logic_domain.search.model.SearchRequestDTO;
import com.allMighty.business_logic_domain.search.model.SearchResponseDTO;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchService {

  private final SearchRepository searchRepository;
  private final ChatApiClient chatApiClient;

  @Value("${chat.api.max-catalog-items:350}")
  private int maxCatalogItems;

  @Value("${chat.api.max-suggestions:8}")
  private int maxSuggestions;

  public SearchResponseDTO search(SearchRequestDTO searchRequestDTO) {
    return searchRepository.search(searchRequestDTO);
  }

  public ChatSuggestResponseDTO suggestAnalyses(ChatSuggestRequestDTO requestDTO) {
    if (requestDTO == null || isBlank(requestDTO.getMessage())) {
      throw new BadRequestException("Message cannot be empty");
    }

    List<AnalysisCatalogItemDTO> catalog = searchRepository.getAnalysisCatalog(maxCatalogItems);

    List<Long> analysisIds = new ArrayList<>();
    String responseText = "";

    try {
      ChatSuggestionResult chatSuggestion =
          chatApiClient.suggestAnalysis(requestDTO.getMessage(), catalog, maxSuggestions);
      if (chatSuggestion != null) {
        analysisIds.addAll(chatSuggestion.analysisIds());
        responseText = defaultString(chatSuggestion.responseText());
      }
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
    } catch (IOException ignored) {
      // Fallback handled below
    } catch (Exception ignored) {
      // Fallback handled below
    }

    if (analysisIds.isEmpty()) {
      analysisIds = searchRepository.suggestAnalysisIdsByKeyword(requestDTO.getMessage(), maxSuggestions);
      if (isBlank(responseText)) {
        responseText =
            analysisIds.isEmpty()
                ? "Nuk gjeta analiza te sakta nga pershkrimi juaj. Mund te shtoni me shume detaje?"
                : "Bazuar ne pershkrimin tuaj, keto analiza mund te jene te pershtatshme.";
      }
    }

    List<Long> existingIds = searchRepository.findExistingAnalysisIds(analysisIds);
    Set<Long> existingSet = new LinkedHashSet<>(existingIds);
    List<Long> sanitizedIds = new ArrayList<>();
    for (Long id : analysisIds) {
      if (id != null && existingSet.contains(id) && !sanitizedIds.contains(id)) {
        sanitizedIds.add(id);
      }
    }

    ChatSuggestResponseDTO responseDTO = new ChatSuggestResponseDTO();
    responseDTO.setAnalysisIds(sanitizedIds);
    responseDTO.setResponseText(defaultString(responseText));
    return responseDTO;
  }

  private static boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }

  private static String defaultString(String value) {
    return value == null ? "" : value;
  }
}
