package com.allMighty.business_logic_domain.search;

import static com.allMighty.global_operation.response.ResponseFactory.createResponse;

import com.allMighty.business_logic_domain.search.model.SearchRequestDTO;
import com.allMighty.business_logic_domain.search.model.SearchResponseDTO;
import com.allMighty.client.UrlProperty;
import com.allMighty.global_operation.response.EntityResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlProperty.Search.PATH)
@RequiredArgsConstructor
public class SearchController {
  private final SearchService searchService;

  @GetMapping
  public ResponseEntity<EntityResponseDTO<SearchResponseDTO>> search(@RequestBody SearchRequestDTO searchRequest) {
    SearchResponseDTO searchResponseDTO = searchService.search(searchRequest);
    return ResponseEntity.ok(createResponse(searchResponseDTO));
  }
}
