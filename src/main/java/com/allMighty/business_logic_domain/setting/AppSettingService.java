package com.allMighty.business_logic_domain.setting;

import com.allMighty.enitity.AppSettingEntity;
import com.allMighty.enumeration.SettingKey;
import com.allMighty.global_operation.BaseService;
import com.allMighty.global_operation.exception_management.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppSettingService extends BaseService {
  private final AppSettingRepository appSettingRepository;

  public boolean isStudyApplicationEnabled() {
    return appSettingRepository
        .findValue(SettingKey.STUDY_APPLICATION_ENABLED)
        .map(Boolean::parseBoolean)
        .orElse(false);
  }

  @Transactional
  public StudyApplicationSettingDTO updateStudyApplicationSetting(
      StudyApplicationSettingDTO settingDTO) {
    Long id =
        appSettingRepository
            .findId(SettingKey.STUDY_APPLICATION_ENABLED)
            .orElseThrow(() -> new NotFoundException("Setting not found!"));

    AppSettingEntity appSettingEntity = em.find(AppSettingEntity.class, id);
    appSettingEntity.setSettingValue(Boolean.toString(settingDTO.getEnabled()));
    em.merge(appSettingEntity);

    return new StudyApplicationSettingDTO(settingDTO.getEnabled());
  }
}
