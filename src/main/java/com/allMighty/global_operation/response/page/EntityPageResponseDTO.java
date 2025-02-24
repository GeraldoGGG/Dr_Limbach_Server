package com.allMighty.global_operation.response.page;

import com.allMighty.global_operation.response.ResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EntityPageResponseDTO<T> extends ResponseDTO<T> {
  private Long page;
  private Long availablePages;
  private Long size;
  private Long totalSize;

  private List<T> items;
}