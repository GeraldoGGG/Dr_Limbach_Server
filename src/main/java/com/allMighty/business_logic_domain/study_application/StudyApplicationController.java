package com.allMighty.business_logic_domain.study_application;

import static com.allMighty.global_operation.response.ResponseFactory.createPage;
import static com.allMighty.global_operation.response.ResponseFactory.createResponse;

import com.allMighty.business_logic_domain.export.ExportService;
import com.allMighty.client.UrlProperty.StudyApplication;
import com.allMighty.global_operation.response.EntityResponseDTO;
import com.allMighty.global_operation.response.page.EntityPageResponseDTO;
import com.allMighty.global_operation.response.page.PageDescriptor;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(StudyApplication.PATH)
@RequiredArgsConstructor
@Validated
public class StudyApplicationController {
  private final StudyApplicationService studyApplicationService;
  private final ExportService exportService;

  @GetMapping
  public ResponseEntity<EntityPageResponseDTO<StudyApplicationDTO>> getStudyApplications(
      @RequestParam(name = "page", defaultValue = "1") Long page,
      @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize,
      @RequestParam(value = "filter", defaultValue = "") List<String> filters) {

    Long count = studyApplicationService.count(filters);
    PageDescriptor pageDescriptor = new PageDescriptor(page, pageSize);
    List<StudyApplicationDTO> studyApplicationDTOS =
        studyApplicationService.getAllStudyApplications(filters, pageDescriptor);

    return ResponseEntity.ok(createPage(count, studyApplicationDTOS, pageDescriptor));
  }

  @GetMapping(StudyApplication.EXPORT)
  public ResponseEntity<byte[]> exportStudyApplications(
      @RequestParam(value = "filter", defaultValue = "") List<String> filters) {
    List<StudyApplicationDTO> studyApplicationDTOS =
        studyApplicationService.getAllStudyApplications(filters);

    byte[] excelFile = exportService.generateStudyApplicationsExcel(studyApplicationDTOS);

    HttpHeaders headers = new HttpHeaders();
    headers.add(
        HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pcos-study-applications.xlsx");
    headers.setContentType(
        MediaType.parseMediaType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

    return ResponseEntity.ok().headers(headers).body(excelFile);
  }

  @PostMapping
  public ResponseEntity<EntityResponseDTO<StudyApplicationDTO>> createStudyApplication(
      @RequestBody @Valid StudyApplicationDTO studyApplicationDTO) {

    Long studyApplicationId = studyApplicationService.createStudyApplication(studyApplicationDTO);
    StudyApplicationDTO created = studyApplicationService.getStudyApplicationById(studyApplicationId);

    return ResponseEntity.ok(createResponse(created));
  }
}
