package com.allMighty.global_util.page;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EntityPageResponseDTO<T>  {
  private Long page;
  private Long availablePages;
  private Long size;
  private Long totalSize;

  private List<T> items;
}