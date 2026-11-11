package com.allMighty.business_logic_domain.setting;

import static com.allMighty.global_operation.response.ResponseFactory.createResponse;

import com.allMighty.client.UrlProperty.Setting;
import com.allMighty.global_operation.response.EntityResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Setting.PATH)
@RequiredArgsConstructor
@Validated
public class AppSettingController {
  private final AppSettingService appSettingService;

  @GetMapping(Setting.STUDY_APPLICATION)
  public ResponseEntity<EntityResponseDTO<StudyApplicationSettingDTO>> getStudyApplicationSetting() {
    StudyApplicationSettingDTO setting =
        new StudyApplicationSettingDTO(appSettingService.isStudyApplicationEnabled());
    return ResponseEntity.ok(createResponse(setting));
  }

  @PutMapping(Setting.STUDY_APPLICATION)
  public ResponseEntity<EntityResponseDTO<StudyApplicationSettingDTO>> updateStudyApplicationSetting(
      @RequestBody @Valid StudyApplicationSettingDTO settingDTO) {
    StudyApplicationSettingDTO updated = appSettingService.updateStudyApplicationSetting(settingDTO);
    return ResponseEntity.ok(createResponse(updated));
  }
}
