package com.allMighty.business_logic_domain.article;

import static com.allMighty.business_logic_domain.article.ArticleMapper.ArticleJooqMapper.ARTICLE_CATEGORY_KEYWORD;
import static com.allMighty.business_logic_domain.tag.TagMapper.TAGS_KEYWORD;
import static com.example.jooq.generated.tables.Article.ARTICLE;
import static com.example.jooq.generated.tables.ArticleArticleCategory.ARTICLE_ARTICLE_CATEGORY;
import static com.example.jooq.generated.tables.ArticleCategory.ARTICLE_CATEGORY;
import static com.example.jooq.generated.tables.MedicalService.MEDICAL_SERVICE;
import static com.example.jooq.generated.tables.MedicalServiceArticle.MEDICAL_SERVICE_ARTICLE;
import static com.example.jooq.generated.tables.Tag.TAG;
import static com.example.jooq.generated.tables.TagArticle.TAG_ARTICLE;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

import com.allMighty.business_logic_domain.general.EntityIdDTO;
import com.allMighty.enitity.article.ArticleEntity;
import com.allMighty.global_operation.response.page.PageDescriptor;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {

  private final DSLContext dsl;

  private final ArticleMapper.ArticleJooqMapper articleJooqMapper =
      new ArticleMapper.ArticleJooqMapper();

  public Long count() {
    return count(new ArrayList<>());
  }

  public Long count(List<Condition> conditions) {
    var select = dsl.select(DSL.countDistinct(ARTICLE.ID));

    var from = getFrom(select);

    return from.where(conditions).fetchSingleInto(Long.class);
  }

  private static @NotNull SelectOnConditionStep<?> getFrom(SelectSelectStep<?> select) {
    return select
        .from(ARTICLE)

        // service
        .leftJoin(MEDICAL_SERVICE_ARTICLE)
        .on(ARTICLE.ID.eq(MEDICAL_SERVICE_ARTICLE.ARTICLE_ID))
        .leftJoin(MEDICAL_SERVICE)
        .on(MEDICAL_SERVICE.ID.eq(MEDICAL_SERVICE_ARTICLE.MEDICAL_SERVICE_ID))

        // tags
        .leftJoin(TAG_ARTICLE)
        .on(ARTICLE.ID.eq(TAG_ARTICLE.ARTICLE_ID))
        .leftJoin(TAG)
        .on(TAG.ID.eq(TAG_ARTICLE.TAG_ID))

        // category
        .leftJoin(ARTICLE_ARTICLE_CATEGORY)
        .on(ARTICLE.ID.eq(ARTICLE_ARTICLE_CATEGORY.ARTICLE_ID))
        .leftJoin(ARTICLE_CATEGORY)
        .on(ARTICLE_CATEGORY.ID.eq(ARTICLE_ARTICLE_CATEGORY.ARTICLE_CATEGORY_ID));
  }

  public List<ArticleEntity> getAllArticles(
      List<Condition> conditions, PageDescriptor pageDescriptor) {
    if (pageDescriptor == null) {
      pageDescriptor = PageDescriptor.maxDataDescriptor();
    }
    Long offset = pageDescriptor.getOffset();
    Long pageSize = pageDescriptor.getPageSize();

    var select =
        dsl.select(
            ARTICLE.ID,
            ARTICLE.TITLE,
            ARTICLE.AUTHOR,
            ARTICLE.CONTENT,
            ARTICLE.ARCHIVED,
            ARTICLE.CREATION_DATE,
            ARTICLE.SUMMARY,
            ARTICLE.SHOW_IN_HOME_PAGE,
            multiset(
                    select(TAG.ID, TAG.NAME)
                        .from(TAG)
                        .leftJoin(TAG_ARTICLE)
                        .on(TAG.ID.eq(TAG_ARTICLE.TAG_ID))
                        .where(TAG_ARTICLE.ARTICLE_ID.eq(ARTICLE.ID)))
                .as(TAGS_KEYWORD),
            multiset(
                    select(ARTICLE_CATEGORY.ID, ARTICLE_CATEGORY.NAME)
                        .from(ARTICLE_CATEGORY)
                        .leftJoin(ARTICLE_ARTICLE_CATEGORY)
                        .on(ARTICLE_CATEGORY.ID.eq(ARTICLE_ARTICLE_CATEGORY.ARTICLE_CATEGORY_ID))
                        .where(ARTICLE_ARTICLE_CATEGORY.ARTICLE_ID.eq(ARTICLE.ID)))
                .as(ARTICLE_CATEGORY_KEYWORD));

    var from = getFrom(select);

    return from.where(conditions)
        .groupBy(
            ARTICLE.ID,
            ARTICLE.TITLE,
            ARTICLE.AUTHOR,
            ARTICLE.CONTENT,
            ARTICLE.ARCHIVED,
            ARTICLE.REMOVED)
        .offset(offset)
        .limit(pageSize)
        .fetch(articleJooqMapper);
  }

  public Optional<ArticleEntity> findById(Long id) {
    Condition eq = ARTICLE.ID.eq(id);
    List<ArticleEntity> articles = getAllArticles(Collections.singletonList(eq), null);
    return articles.stream().findFirst();
  }

  public List<EntityIdDTO> getAllArticlesSimple() {

    return dsl.select(ARTICLE.ID, ARTICLE.TITLE.as("name"))
        .from(ARTICLE)
        .where(ARTICLE.ARCHIVED.eq(false))
        .fetchInto(EntityIdDTO.class);
  }
}
