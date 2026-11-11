package com.allMighty.business_logic_domain.setting;

import com.allMighty.global_operation.converter.StrictBooleanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyApplicationSettingDTO {

  @NotNull(message = "Enabled cannot be null")
  @JsonDeserialize(using = StrictBooleanDeserializer.class)
  private Boolean enabled;
}
