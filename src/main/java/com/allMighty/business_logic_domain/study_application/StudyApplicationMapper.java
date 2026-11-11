package com.allMighty.business_logic_domain.study_application;

import static com.example.jooq.generated.Tables.PCOS_STUDY_APPLICATION;
import static com.example.jooq.generated.Tables.STUDY_APPLICATION_CONDITION;
import static com.example.jooq.generated.Tables.STUDY_APPLICATION_MEDICATION;

import com.allMighty.enitity.StudyApplicationEntity;
import com.allMighty.enumeration.study.HealthCondition;
import com.allMighty.enumeration.study.Medication;
import com.allMighty.enumeration.study.StudyGroup;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;

public class StudyApplicationMapper {

  static final Field<Set<HealthCondition>> CONDITIONS =
      DSL.multiset(
              DSL.select(STUDY_APPLICATION_CONDITION.HEALTH_CONDITION)
                  .from(STUDY_APPLICATION_CONDITION)
                  .where(STUDY_APPLICATION_CONDITION.APPLICATION_ID.eq(PCOS_STUDY_APPLICATION.ID)))
          .as("conditions")
          .convertFrom(
              result ->
                  result.stream()
                      .map(r -> HealthCondition.valueOf(r.value1()))
                      .collect(Collectors.toSet()));

  static final Field<Set<Medication>> MEDICATIONS =
      DSL.multiset(
              DSL.select(STUDY_APPLICATION_MEDICATION.MEDICATION)
                  .from(STUDY_APPLICATION_MEDICATION)
                  .where(
                      STUDY_APPLICATION_MEDICATION.APPLICATION_ID.eq(PCOS_STUDY_APPLICATION.ID)))
          .as("medications")
          .convertFrom(
              result ->
                  result.stream()
                      .map(r -> Medication.valueOf(r.value1()))
                      .collect(Collectors.toSet()));

  public static StudyApplicationEntity toStudyApplicationEntity(
      StudyApplicationDTO dto, StudyApplicationEntity entity) {
    entity.setFirstName(dto.getFirstName().trim());
    entity.setLastName(dto.getLastName().trim());
    entity.setAge(dto.getAge());
    entity.setEmail(normalizeEmail(dto.getEmail()));
    entity.setStudyGroup(dto.getStudyGroup());
    entity.setConditions(
        dto.getConditions() == null ? new HashSet<>() : new HashSet<>(dto.getConditions()));
    entity.setMedications(
        dto.getMedications() == null ? new HashSet<>() : new HashSet<>(dto.getMedications()));
    return entity;
  }

  static String normalizeEmail(String email) {
    return email == null ? null : email.trim().toLowerCase();
  }

  static class StudyApplicationDtoJooqMapper implements RecordMapper<Record, StudyApplicationDTO> {

    @Override
    public StudyApplicationDTO map(Record record) {
      StudyApplicationDTO dto = new StudyApplicationDTO();

      dto.setId(record.get(PCOS_STUDY_APPLICATION.ID));
      dto.setFirstName(record.get(PCOS_STUDY_APPLICATION.FIRST_NAME));
      dto.setLastName(record.get(PCOS_STUDY_APPLICATION.LAST_NAME));
      dto.setAge(record.get(PCOS_STUDY_APPLICATION.AGE));
      dto.setEmail(record.get(PCOS_STUDY_APPLICATION.EMAIL));
      dto.setStudyGroup(record.get(PCOS_STUDY_APPLICATION.STUDY_GROUP, StudyGroup.class));
      dto.setCreatedAt(record.get(PCOS_STUDY_APPLICATION.CREATED_AT));
      dto.setConditions(record.get(CONDITIONS));
      dto.setMedications(record.get(MEDICATIONS));

      return dto;
    }
  }
}
