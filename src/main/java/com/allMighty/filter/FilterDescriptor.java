package com.allMighty.filter;

import lombok.Getter;

@Getter
public class FilterDescriptor<T extends IQueryField, V> {

    private T field;
    private String fieldAlias;
    private FilterOperator operator;
    private V value;

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