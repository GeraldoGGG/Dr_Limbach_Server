package com.allMighty.business_logic_domain.analysis.analysis_package;

import com.allMighty.global_operation.filter.FilterableField;
import lombok.Getter;
import org.jooq.Field;

import static com.example.jooq.generated.tables.Package.PACKAGE;

@Getter
public enum PackageFields implements FilterableField {
    ID("id", PACKAGE.ID),
    SHOW_IN_HOME_PAGE("showInHomePage", PACKAGE.SHOW_IN_HOME_PAGE),
    ARCHIVED("archived", PACKAGE.ARCHIVED);

    private final String restAlias;
    private final Field<?> jooqField;

    PackageFields(String restAlias, Field<?> jooqField) {
        this.restAlias = restAlias;
        this.jooqField = jooqField;
    }
}
