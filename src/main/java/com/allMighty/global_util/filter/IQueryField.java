package com.allMighty.global_util.filter;

import org.jooq.Field;

public interface IQueryField {
    String getRestAlias();
    Field<?> getJooqField();
}
