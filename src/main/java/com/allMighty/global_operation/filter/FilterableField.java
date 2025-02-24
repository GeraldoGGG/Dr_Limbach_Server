package com.allMighty.global_operation.filter;

import org.jooq.Field;

public interface FilterableField {
    String getRestAlias();
    Field<?> getJooqField();
}
