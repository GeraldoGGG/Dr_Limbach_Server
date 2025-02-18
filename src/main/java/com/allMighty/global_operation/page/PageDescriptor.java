package com.allMighty.global_operation.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PageDescriptor {
  private Long pageNumber;
  private Long pageSize;

  public static PageDescriptor defaultPage() {
    return PageDescriptor.builder().pageNumber(1L).pageSize(10L).build();
  }

  public static PageDescriptor maxDataDescriptor() {
    return PageDescriptor.builder().pageNumber(1L).pageSize(Long.MAX_VALUE).build();
  }

  public Long getOffset() {
    return (pageNumber - 1) * pageSize;
  }

  public long getAvailablePages(Long totalQuerySize) {
    return (long) Math.ceil(totalQuerySize / (double) pageSize);
  }
}
