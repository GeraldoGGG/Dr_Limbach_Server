package com.allMighty.business_logic_domain.analysis.analysis_package;

import static com.allMighty.client.UrlProperty.ID_PATH;
import static com.allMighty.global_operation.response.ResponseFactory.createResponse;

import com.allMighty.business_logic_domain.analysis.dto.AnalysisPackageDTO;
import com.allMighty.client.UrlProperty;
import com.allMighty.global_operation.response.EntityResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlProperty.AnalysisPackage.PATH)
@RequiredArgsConstructor
@Validated
public class AnalysisPackageController {

  private final AnalysisPackageService analysisPackageService;

  @GetMapping
  public ResponseEntity<EntityResponseDTO<List<AnalysisPackageDTO>>> getAllAnalysisPackages() {
    List<AnalysisPackageDTO> packages = analysisPackageService.getAllAnalysisPackages();
    return ResponseEntity.ok(createResponse(packages));
  }

  @GetMapping(ID_PATH)
  public ResponseEntity<EntityResponseDTO<AnalysisPackageDTO>> getPackageById(
      @PathVariable(name = "id") Long id) {
    AnalysisPackageDTO packageDTO = analysisPackageService.getAnalysisPackageById(id);
    return ResponseEntity.ok(createResponse(packageDTO));
  }

  @PostMapping
  public ResponseEntity<EntityResponseDTO<AnalysisPackageDTO>> createAnalysisPackage(
      @RequestBody AnalysisPackageDTO analysisPackageDTO) {
    Long id = analysisPackageService.createAnalysisPackage(analysisPackageDTO);
    AnalysisPackageDTO createdPackage = analysisPackageService.getAnalysisPackageById(id);

    return ResponseEntity.ok(createResponse(createdPackage));
  }

  @PutMapping(ID_PATH)
  public ResponseEntity<EntityResponseDTO<AnalysisPackageDTO>> updateAnalysisPackage(
      @PathVariable Long id, @RequestBody AnalysisPackageDTO analysisPackageEntity) {
    Long packageId = analysisPackageService.updateAnalysisPackage(id, analysisPackageEntity);

    AnalysisPackageDTO updatedPackage = analysisPackageService.getAnalysisPackageById(packageId);
    return ResponseEntity.ok(createResponse(updatedPackage));
  }
}
