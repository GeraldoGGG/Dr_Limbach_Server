package com.allMighty.global_operation.dto;

import java.util.Objects;

public abstract class AbstractDTO {

  public abstract Long getId();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (getId() == null && ((AbstractDTO) o).getId() == null) {
      return false;
    }
    AbstractDTO that = (AbstractDTO) o;
    return Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getClass(), getId());
  }
}