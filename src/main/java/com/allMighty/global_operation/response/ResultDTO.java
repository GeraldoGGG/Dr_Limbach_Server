package com.allMighty.global_operation.response;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ResultDTO<T> {
  boolean success;
  String errorMessage;
  PayloadValidationStatus validationStatus;
  T result;

  public PayloadValidationStatus getValidationStatus() {
    if (validationStatus == null) {
      validationStatus = new PayloadValidationStatus();
    }
    return validationStatus;
  }


  @NoArgsConstructor
  public static class EntityResultDTO extends ResultDTO<Long> {

    public EntityResultDTO(Long entityId) {
      result = entityId;
      setValidationStatus(new PayloadValidationStatus());
    }

    // ignores success
    @JsonIgnore
    @Override
    public boolean isSuccess() {
      return super.isSuccess();
    }
  }

  @NoArgsConstructor
  public static class EntityIdResultDTO extends ResultDTO<Long> {

    public EntityIdResultDTO(String errorMessage) {
      this.errorMessage = errorMessage;
    }

    public EntityIdResultDTO(Long entityId) {
      result = entityId;
    }

    @Override
    public boolean isSuccess() {
      return StringUtils.isBlank(errorMessage);
    }
  }

}