package com.allMighty.global_operation.filter;

import org.jooq.Field;

public interface IQueryField {
    String getRestAlias();
    Field<?> getJooqField();
}
