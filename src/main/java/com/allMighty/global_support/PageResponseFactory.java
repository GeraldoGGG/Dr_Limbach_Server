package com.allMighty.global_support;

import java.util.List;

public class PageResponseFactory {
    public static <T> EntityPageResponseDTO<T> createPage(Long count, List<T> items, PageDescriptor pageDescriptor) {
        EntityPageResponseDTO<T> response = new EntityPageResponseDTO<>();
        response.setItems(items);
        response.setSize((long) (null != items ? items.size() : 0));
        response.setTotalSize(null != count ? count : 0);
        response.setAvailablePages(pageDescriptor.getAvailablePages(null != count ? count : 0));
        response.setPage(pageDescriptor.getPageNumber());

        return response;
    }
}