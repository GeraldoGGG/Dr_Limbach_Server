package com.allMighty.global_util.filter;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class FilterParser<T extends IQueryField> {

    private final T[] fieldValues;

    public List<FilterDescriptor<T, ?>> parseFilters(List<String> filters) {
        if (CollectionUtils.isEmpty(filters)) {
            return Collections.emptyList();
        }

        List<String> multiFilters = new ArrayList<>(filters.size());
        for (String filter : filters) {
            if (filter.startsWith("[") && filter.endsWith("]")) {
                String filterContent = filter.substring(1, filter.length() - 1);
                multiFilters.addAll(Arrays.asList(filterContent.split(", ")));
            } else {
                multiFilters.add(filter);
            }
        }
        List<FilterDescriptor<T, ?>> result = new ArrayList<>();
        for (String filter : multiFilters) {
            if (StringUtils.isBlank(filter)) {
                continue;
            }
            for (FilterOperator operator : FilterOperator.values()) {
                String[] tokens = filter.split(operator.getParamOperator());
                if (tokens.length > 1) {
                    String fieldAlias = tokens[0];
                    String serializedString = tokens[1];

                    T field = fromRestAlias(fieldAlias);
                    if (field != null) {
                        result.add(new FilterDescriptor<>(field, fieldAlias, operator, serializedString));
                    }
                    break;
                }
            }
        }

        return result;
    }


    private T fromRestAlias(String fieldAlias) {
        return Stream.of(fieldValues).filter(candidate -> Objects.equals(candidate.getRestAlias(), fieldAlias)).findFirst()
                .orElse(null);
    }

}
