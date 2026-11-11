package com.allMighty.business_logic_domain.study_application;

import static com.allMighty.business_logic_domain.study_application.StudyApplicationMapper.normalizeEmail;
import static com.allMighty.business_logic_domain.study_application.StudyApplicationMapper.toStudyApplicationEntity;
import static com.allMighty.global_operation.filter.JooqConditionBuilder.buildConditions;

import com.allMighty.business_logic_domain.fields.StudyApplicationField;
import com.allMighty.business_logic_domain.setting.AppSettingService;
import com.allMighty.enitity.StudyApplicationEntity;
import com.allMighty.global_operation.BaseService;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import com.allMighty.global_operation.exception_management.exception.ConflictException;
import com.allMighty.global_operation.exception_management.exception.ForbiddenException;
import com.allMighty.global_operation.filter.FilterParser;
import com.allMighty.global_operation.response.page.PageDescriptor;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyApplicationService extends BaseService {
  private final FilterParser<StudyApplicationField> filterParser =
      new FilterParser<>(StudyApplicationField.values());
  private final StudyApplicationRepository studyApplicationRepository;
  private final AppSettingService appSettingService;

  public List<StudyApplicationDTO> getAllStudyApplications(
      List<String> filters, PageDescriptor pageDescriptor) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    return studyApplicationRepository.getAllStudyApplications(conditions, pageDescriptor);
  }

  public Long count(List<String> filters) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    return studyApplicationRepository.count(conditions);
  }

  public List<StudyApplicationDTO> getAllStudyApplications(List<String> filters) {
    return getAllStudyApplications(filters, PageDescriptor.maxDataDescriptor());
  }

  @Transactional
  public Long createStudyApplication(StudyApplicationDTO studyApplicationDTO) {
    if (!appSettingService.isStudyApplicationEnabled()) {
      throw new ForbiddenException("Study applications are currently closed!");
    }

    if (studyApplicationRepository.existsByEmail(normalizeEmail(studyApplicationDTO.getEmail()))) {
      throw new ConflictException("An application with this email already exists!");
    }

    StudyApplicationEntity studyApplicationEntity =
        toStudyApplicationEntity(studyApplicationDTO, new StudyApplicationEntity());
    em.persist(studyApplicationEntity);

    return studyApplicationEntity.getId();
  }

  public StudyApplicationDTO getStudyApplicationById(Long id) {
    return studyApplicationRepository
        .findById(id)
        .orElseThrow(() -> new BadRequestException("Study application not found!"));
  }
}
