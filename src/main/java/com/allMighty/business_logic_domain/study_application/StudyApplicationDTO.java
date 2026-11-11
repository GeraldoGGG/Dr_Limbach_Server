package com.allMighty.business_logic_domain.study_application;

import com.allMighty.enumeration.study.HealthCondition;
import com.allMighty.enumeration.study.Medication;
import com.allMighty.enumeration.study.StudyGroup;
import com.allMighty.global_operation.dto.AbstractDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyApplicationDTO extends AbstractDTO {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  @NotBlank(message = "First name cannot be blank")
  private String firstName;

  @NotBlank(message = "Last name cannot be blank")
  private String lastName;

  @NotNull(message = "Age cannot be null")
  @Min(value = 18, message = "Age must be 18 or above")
  private Integer age;

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email is not valid")
  private String email;

  @NotNull(message = "Study group cannot be null")
  private StudyGroup studyGroup;

  private Set<@NotNull HealthCondition> conditions;

  private Set<@NotNull Medication> medications;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private LocalDateTime createdAt;
}
