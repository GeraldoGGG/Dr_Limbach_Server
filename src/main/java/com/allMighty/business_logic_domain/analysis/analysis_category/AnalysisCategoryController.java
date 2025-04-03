package com.allMighty.business_logic_domain.analysis.analysis_category;

import static com.allMighty.client.UrlProperty.ID_PATH;
import static com.allMighty.global_operation.response.ResponseFactory.createResponse;

import com.allMighty.business_logic_domain.analysis.dto.AnalysisCategoryDTO;
import com.allMighty.client.UrlProperty;
import com.allMighty.global_operation.response.EntityResponseDTO;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlProperty.AnalysisCategory.PATH)
@RequiredArgsConstructor
@Validated
public class AnalysisCategoryController {

  private final AnalysisCategoryService analysisCategoryService;

  @GetMapping
  public ResponseEntity<EntityResponseDTO<List<AnalysisCategoryDTO>>> getAllAnalysisCategories() {
    List<AnalysisCategoryDTO> categories = analysisCategoryService.getAllAnalysisCategories();
    return ResponseEntity.ok(createResponse(categories));
  }

  @GetMapping(ID_PATH)
  public ResponseEntity<EntityResponseDTO<AnalysisCategoryDTO>> getCategoryById(
      @PathVariable(name = "id") Long id) {
    AnalysisCategoryDTO categoryDTO = analysisCategoryService.getAnalysisCategoryById(id);
    return ResponseEntity.ok(createResponse(categoryDTO));
  }

  @PostMapping
  public ResponseEntity<EntityResponseDTO<AnalysisCategoryDTO>> createAnalysisCategory(
      @RequestBody @Valid AnalysisCategoryDTO analysisCategoryDTO) {
    Long id = analysisCategoryService.createAnalysisCategory(analysisCategoryDTO);
    AnalysisCategoryDTO createdCategory = analysisCategoryService.getAnalysisCategoryById(id);
    return ResponseEntity.ok(createResponse(createdCategory));
  }

  @PutMapping(ID_PATH)
  public ResponseEntity<EntityResponseDTO<AnalysisCategoryDTO>> updateAnalysisCategory(
      @PathVariable Long id, @RequestBody @Valid AnalysisCategoryDTO analysisCategoryDTO) {
    analysisCategoryService.updateAnalysisCategory(id, analysisCategoryDTO);
    AnalysisCategoryDTO updatedCategory = analysisCategoryService.getAnalysisCategoryById(id);
    return ResponseEntity.ok(createResponse(updatedCategory));
  }
}
