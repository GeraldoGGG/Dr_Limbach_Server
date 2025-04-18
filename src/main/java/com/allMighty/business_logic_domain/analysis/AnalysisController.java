package com.allMighty.business_logic_domain.analysis;

import com.allMighty.business_logic_domain.analysis.dto.AnalysisDTO;
import com.allMighty.business_logic_domain.general.EntityIdDTO;
import com.allMighty.client.UrlProperty.Analysis;
import com.allMighty.global_operation.response.page.EntityPageResponseDTO;
import com.allMighty.global_operation.response.page.PageDescriptor;
import com.allMighty.global_operation.response.EntityResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.allMighty.client.UrlProperty.ID_PATH;
import static com.allMighty.client.UrlProperty.SIMPLE;
import static com.allMighty.global_operation.response.ResponseFactory.createPage;
import static com.allMighty.global_operation.response.ResponseFactory.createResponse;
@RestController
@RequestMapping(Analysis.PATH)
@RequiredArgsConstructor
@Validated
public class AnalysisController {

    private final AnalysisService analysisService;

    @GetMapping
    public ResponseEntity<EntityPageResponseDTO<AnalysisDTO>> getAllAnalyses(
            @RequestParam(name = "page", defaultValue = "1") Long page,
            @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize,
            @RequestParam(value = "filter", defaultValue = "") List<String> filters) {

        Long count = analysisService.count(filters);
        PageDescriptor pageDescriptor = new PageDescriptor(page, pageSize);
        List<AnalysisDTO> analysisDTOs = analysisService.getAnalyses(filters, pageDescriptor);
        return ResponseEntity.ok(createPage(count, analysisDTOs, pageDescriptor));
    }

    @GetMapping(SIMPLE)
    public ResponseEntity<EntityResponseDTO<List<EntityIdDTO>>> getAllAnalysesSimple() {
        List<EntityIdDTO> analysisDTOs = analysisService.getSimpleAnalyses();
        return ResponseEntity.ok(createResponse(analysisDTOs));
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<EntityResponseDTO<AnalysisDTO>> getAnalysisById(
            @PathVariable(name = "id") Long id) {

        AnalysisDTO analysisDTO = analysisService.getAnalysisById(id);
        return ResponseEntity.ok(createResponse(analysisDTO));
    }

    @PostMapping
    public ResponseEntity<EntityResponseDTO<AnalysisDTO>> createAnalysis(
            @RequestBody @Valid AnalysisDTO analysisDTO) {

        Long analysisId = analysisService.createAnalysis(analysisDTO);
        AnalysisDTO createdAnalysis = analysisService.getAnalysisById(analysisId);

        return ResponseEntity.ok(createResponse(createdAnalysis));
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<EntityResponseDTO<AnalysisDTO>> updateAnalysis(
            @PathVariable(name = "id") Long id, @RequestBody @Valid AnalysisDTO analysisDTO) {

        Long analysisId = analysisService.updateAnalysis(id, analysisDTO);
        AnalysisDTO updatedAnalysis = analysisService.getAnalysisById(analysisId);
        return ResponseEntity.ok(createResponse(updatedAnalysis));
    }
}
