package com.allMighty.global_operation.response;

import com.allMighty.global_operation.response.page.EntityPageResponseDTO;
import com.allMighty.global_operation.response.page.PageDescriptor;

import java.util.List;

public class ResponseFactory {
    public static <T> EntityPageResponseDTO<T> createPage(Long count, List<T> items, PageDescriptor pageDescriptor) {
        EntityPageResponseDTO<T> response = new EntityPageResponseDTO<>();
        response.setItems(items);
        response.setSize((long) (null != items ? items.size() : 0));
        response.setTotalSize(null != count ? count : 0);
        response.setAvailablePages(pageDescriptor.getAvailablePages(null != count ? count : 0));
        response.setPage(pageDescriptor.getPageNumber());

        return response;
    }



    public static <T> EntityResponseDTO<T> createResponse(T item) {
        EntityResponseDTO<T> response = new EntityResponseDTO<>();
        response.setResult(item);
        return response;
    }
}