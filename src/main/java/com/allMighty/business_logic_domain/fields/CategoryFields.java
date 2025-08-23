package com.allMighty.business_logic_domain.fields;

import com.allMighty.global_operation.filter.FilterableField;
import lombok.Getter;
import org.jooq.Field;

import static com.example.jooq.generated.tables.Category.CATEGORY;

@Getter
public enum CategoryFields implements FilterableField{
    ID("id", CATEGORY.ID),
    NAME("name", CATEGORY.NAME),
    ARCHIVED("archived", CATEGORY.ARCHIVED);

    private final String restAlias;
    private final Field<?> jooqField;

    CategoryFields(String restAlias, Field<?> jooqField) {
        this.restAlias = restAlias;
        this.jooqField = jooqField;
    }
}
