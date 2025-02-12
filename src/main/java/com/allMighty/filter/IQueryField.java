package com.allMighty.filter;

import org.jooq.Field;

public interface IQueryField {
    String getRestAlias();
    Field<?> getJooqField();
}
