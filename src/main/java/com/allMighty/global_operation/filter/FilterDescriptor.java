package com.allMighty.global_operation.filter;

import lombok.Getter;

@Getter
public class FilterDescriptor<T extends FilterableField, V> {

    private final T field;
    private final String fieldAlias;
    private final FilterOperator operator;
    private final V value;

    public FilterDescriptor(T field, FilterOperator operator, String fieldAlias, V value) {
        this(field, fieldAlias, operator, value);
    }

    public FilterDescriptor(T field, String fieldAlias, FilterOperator operator, V value) {
        this.field = field;
        this.fieldAlias = fieldAlias;
        this.operator = operator;
        this.value = value;
    }
}