package com.allMighty.business_logic_domain.search;

import com.allMighty.business_logic_domain.search.model.AnalysisCatalogItemDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class   ChatApiClient {

  private final ObjectMapper objectMapper;
  private final HttpClient httpClient = HttpClient.newHttpClient();

  @Value("${chat.api.enabled:false}")
  private boolean chatApiEnabled;

  @Value("${chat.api.url:https://api.openai.com/v1/chat/completions}")
  private String chatApiUrl;

  @Value("${chat.api.key:}")
  private String chatApiKey;

  @Value("${chat.api.model:gpt-4o-mini}")
  private String chatApiModel;

  @Value("${chat.api.timeout-ms:25000}")
  private long chatApiTimeoutMs;

  public ChatSuggestionResult suggestAnalysis(
      String userMessage, List<AnalysisCatalogItemDTO> catalog, int maxSuggestions)
      throws IOException, InterruptedException {

    if (!chatApiEnabled || isBlank(chatApiKey)) {
      return null;
    }

    String systemPrompt = buildSystemPrompt(maxSuggestions);
    String userPrompt = buildUserPrompt(userMessage, catalog);

    Map<String, Object> payload = new LinkedHashMap<>();
    payload.put("model", chatApiModel);
    payload.put("temperature", 0.2);
    payload.put(
        "messages",
        List.of(
            Map.of("role", "system", "content", systemPrompt),
            Map.of("role", "user", "content", userPrompt)));

    String requestBody = objectMapper.writeValueAsString(payload);

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(chatApiUrl))
            .timeout(Duration.ofMillis(chatApiTimeoutMs))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + chatApiKey)
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode() < 200 || response.statusCode() >= 300) {
      throw new IOException("Chat API failed with status code " + response.statusCode());
    }

    JsonNode root = objectMapper.readTree(response.body());
    JsonNode firstChoice = root.path("choices").isArray() ? root.path("choices").path(0) : null;
    String rawModelContent =
        firstChoice == null ? "" : firstChoice.path("message").path("content").asText("");
    String jsonContent = stripJsonFences(rawModelContent);

    JsonNode modelJson = objectMapper.readTree(jsonContent);
    List<Long> analysisIds = parseAnalysisIds(modelJson.path("analysisIds"));
    String responseText = modelJson.path("responseText").asText("");

    return new ChatSuggestionResult(analysisIds, responseText);
  }

  private static List<Long> parseAnalysisIds(JsonNode analysisIdsNode) {
    List<Long> analysisIds = new ArrayList<>();
    if (!analysisIdsNode.isArray()) {
      return analysisIds;
    }

    Set<Long> unique = new LinkedHashSet<>();
    for (JsonNode idNode : analysisIdsNode) {
      long id = idNode.asLong(-1);
      if (id > 0) {
        unique.add(id);
      }
    }
    analysisIds.addAll(unique);
    return analysisIds;
  }

  private static String buildSystemPrompt(int maxSuggestions) {
    return """
Je nje asistent mjekesor per laboratorin Dr Limbach.
Pergjigju gjithmone ne gjuhen shqipe.
Qellimi yt eshte te sugjerosh analiza laboratorike sipas simptomave te perdoruesit.
Ti DUHET te pergjigjesh VETEM me JSON valid ne kete format:
{
  "analysisIds": [1,2,3],
  "responseText": "nje pergjigje e shkurter, miqesore dhe e kuptueshme per njeriun"
}
Rregulla:
- Mos shpik analiza qe nuk jane ne katalog.
- analysisIds duhet te permbaje vetem ID nga katalogu.
- Kthe maksimumi """
        + maxSuggestions
        + """
 analiza.
- Nese nuk gjen perputhje, kthe:
{"analysisIds":[],"responseText":"Nuk gjeta analiza te sakta nga pershkrimi juaj. Mund te jepni me shume detaje?"}
- Mos shto markdown, mos shto tekst jashte JSON.
""";
  }

  private static String buildUserPrompt(String userMessage, List<AnalysisCatalogItemDTO> catalog) {
    StringBuilder builder = new StringBuilder();
    builder.append("Simptomat e perdoruesit:\n").append(userMessage).append("\n\n");
    builder.append("Katalogu i analizave (perdor vetem keto ID):\n");

    for (AnalysisCatalogItemDTO item : catalog) {
      builder
          .append("ID: ")
          .append(item.getId())
          .append(" | Emri: ")
          .append(defaultString(item.getMedicalName()))
          .append(" | Sinonim: ")
          .append(defaultString(item.getSynonym()))
          .append(" | Kategoria: ")
          .append(defaultString(item.getCategoryName()))
          .append('\n');
    }
    return builder.toString();
  }

  private static String stripJsonFences(String raw) {
    if (raw == null) {
      return "";
    }

    String trimmed = raw.trim();
    if (trimmed.startsWith("```json")) {
      trimmed = trimmed.substring(7).trim();
    } else if (trimmed.startsWith("```")) {
      trimmed = trimmed.substring(3).trim();
    }
    if (trimmed.endsWith("```")) {
      trimmed = trimmed.substring(0, trimmed.length() - 3).trim();
    }
    return trimmed;
  }

  private static String defaultString(String value) {
    return value == null ? "" : value;
  }

  private static boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }

  public record ChatSuggestionResult(List<Long> analysisIds, String responseText) {}
}
