package com.allMighty.business_logic_domain.article;

import com.allMighty.global_operation.filter.FilterableField;
import lombok.Getter;
import org.jooq.Field;

import static com.example.jooq.generated.tables.Article.ARTICLE;
import static com.example.jooq.generated.tables.Tag.TAG;

@Getter
public enum ArticleField implements FilterableField {
    ID("id", ARTICLE.ID),
    TAG_ID("tag:id", TAG.ID),
    TAG_NAME("tag:name", TAG.NAME);

    private final String restAlias;
    private final Field<?> jooqField;

    ArticleField(String restAlias, Field<?> jooqField) {
        this.restAlias = restAlias;
        this.jooqField = jooqField;
    }
}
