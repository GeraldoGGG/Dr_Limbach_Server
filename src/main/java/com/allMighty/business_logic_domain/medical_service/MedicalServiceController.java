package com.allMighty.business_logic_domain.medical_service;

import com.allMighty.client.UrlProperty.MedicalService;
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
import static com.allMighty.global_operation.response.ResponseFactory.createPage;
import static com.allMighty.global_operation.response.ResponseFactory.createResponse;

@RestController
@RequestMapping(MedicalService.PATH)
@RequiredArgsConstructor
@Validated
public class MedicalServiceController {

    private final MedicalServiceService medicalServiceService;

    @GetMapping
    public ResponseEntity<EntityPageResponseDTO<MedicalServiceDTO>> getAllMedicalServices(
            @RequestParam(name = "page", defaultValue = "1") Long page,
            @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize,
            @RequestParam(value = "filter", defaultValue = "") List<String> filters) {

        Long count = medicalServiceService.count(filters);
        PageDescriptor pageDescriptor = new PageDescriptor(page, pageSize);
        List<MedicalServiceDTO> medicalServiceDTOs = medicalServiceService.getMedicalServices(filters, pageDescriptor);
        return ResponseEntity.ok(createPage(count, medicalServiceDTOs, pageDescriptor));
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<EntityResponseDTO<MedicalServiceDTO>> getMedicalServiceById(
            @PathVariable(name = "id") Long id) {

        MedicalServiceDTO medicalServiceDTO = medicalServiceService.getMedicalServiceById(id);
        return ResponseEntity.ok(createResponse(medicalServiceDTO));
    }

    @PostMapping
    public ResponseEntity<EntityResponseDTO<MedicalServiceDTO>> createMedicalService(
            @RequestBody @Valid MedicalServiceDTO medicalServiceDTO) {

        Long medicalServiceId = medicalServiceService.createMedicalService(medicalServiceDTO);
        MedicalServiceDTO createdMedicalService = medicalServiceService.getMedicalServiceById(medicalServiceId);

        return ResponseEntity.ok(createResponse(createdMedicalService));
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<EntityResponseDTO<MedicalServiceDTO>> updateMedicalService(
            @PathVariable(name = "id") Long id, @RequestBody @Valid MedicalServiceDTO medicalServiceDTO) {

        Long medicalServiceId = medicalServiceService.updateMedicalService(id, medicalServiceDTO);
        MedicalServiceDTO updatedMedicalService = medicalServiceService.getMedicalServiceById(medicalServiceId);
        return ResponseEntity.ok(createResponse(updatedMedicalService));
    }
}
