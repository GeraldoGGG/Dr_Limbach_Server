package com.allMighty.business_logic_domain.article;

import static com.example.jooq.generated.tables.Article.ARTICLE;
import static com.example.jooq.generated.tables.TagArticle.TAG_ARTICLE;
import static com.example.jooq.generated.tables.Image.IMAGE;
import static com.example.jooq.generated.tables.Tag.TAG;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

import com.allMighty.business_logic_domain.tag.TagRepository;
import com.allMighty.enitity.ArticleEntity;
import com.allMighty.enitity.TagEntity;
import com.allMighty.enumeration.EntityType;
import com.allMighty.global_operation.response.page.PageDescriptor;

import java.util.*;

import com.example.jooq.generated.tables.Image;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {

  private final DSLContext dsl;
  private final TagRepository tagRepository;

  private ArticleMapper.ArticleJooqMapper articleJooqMapper = new ArticleMapper.ArticleJooqMapper();

  public Long count(List<Condition> conditions) {
    return dsl.select(DSL.countDistinct(ARTICLE.ID))
        .from(ARTICLE)
        .leftJoin(TAG_ARTICLE)
        .on(ARTICLE.ID.eq(TAG_ARTICLE.ARTICLE_ID))
        .leftJoin(TAG)
        .on(TAG.ID.eq(TAG_ARTICLE.TAG_ID))
        .where(conditions)
        .fetchSingleInto(Long.class);
  }

  public List<ArticleEntity> getAllArticles(List<Condition> conditions) {
    return getAllArticles(conditions, null);
  }

  public List<ArticleEntity> getAllArticles(
      List<Condition> conditions, PageDescriptor pageDescriptor) {
    if (pageDescriptor == null) {
      pageDescriptor = PageDescriptor.maxDataDescriptor();
    }
    Long offset = pageDescriptor.getOffset();
    Long pageSize = pageDescriptor.getPageSize();

    return dsl.select(
            ARTICLE.ID,
            ARTICLE.VERSION,
            ARTICLE.TITLE,
            ARTICLE.AUTHOR,
            ARTICLE.CONTENT,
            ARTICLE.ARCHIVED,
            ARTICLE.REMOVED,
            multiset(
                    select(TAG.ID, TAG.NAME, TAG.VERSION)
                        .from(TAG)
                        .leftJoin(TAG_ARTICLE)
                        .on(TAG.ID.eq(TAG_ARTICLE.TAG_ID))
                        .where(TAG_ARTICLE.ARTICLE_ID.eq(ARTICLE.ID)))
                .as("tags"),
            multiset(
                    select(IMAGE.ID)
                        .from(IMAGE)
                        .where(
                            IMAGE
                                .ENTITY_TYPE
                                .eq(EntityType.ARTICLE.name())
                                .and(IMAGE.ENTITY_REFERENCE_ID.eq(ARTICLE.ID))))
                .as("images"))
        .from(ARTICLE)
        .leftJoin(TAG_ARTICLE)
        .on(ARTICLE.ID.eq(TAG_ARTICLE.ARTICLE_ID))
        .leftJoin(TAG)
        .on(TAG.ID.eq(TAG_ARTICLE.TAG_ID))
        .where(conditions)
        .groupBy(
            ARTICLE.ID,
            ARTICLE.VERSION,
            ARTICLE.TITLE,
            ARTICLE.AUTHOR,
            ARTICLE.CONTENT,
            ARTICLE.ARCHIVED,
            ARTICLE.REMOVED)
        .offset(offset)
        .limit(pageSize)
        .fetch(articleJooqMapper);
  }

  // TODO manage iamges nee

  public Optional<ArticleEntity> findById(Long id) {
    Condition eq = ARTICLE.ID.eq(id);
    List<ArticleEntity> articles = getAllArticles(Collections.singletonList(eq), null);
    return articles.stream().findFirst();
  }
}
