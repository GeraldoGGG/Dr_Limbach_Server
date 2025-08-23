package com.allMighty.business_logic_domain.article;

import static com.allMighty.business_logic_domain.tag.TagMapper.TagJooqMapper.mapTagEntities;
import static com.example.jooq.generated.tables.Article.ARTICLE;
import static com.example.jooq.generated.tables.ArticleCategory.ARTICLE_CATEGORY;

import com.allMighty.business_logic_domain.tag.TagMapper;
import com.allMighty.enitity.article.ArticleCategoryEntity;
import com.allMighty.enitity.article.ArticleEntity;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;

// TODO this are mechanical mappers it would be wise to use some library maybe mapstruct
public class ArticleMapper {

  public static ArticleDTO toArticleDTO(ArticleEntity articleEntity) {
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setId(articleEntity.getId());
    articleDTO.setTitle(articleEntity.getTitle());
    articleDTO.setContent(articleEntity.getContent());
    articleDTO.setAuthor(articleEntity.getAuthor());
    articleDTO.setArchived(articleEntity.isArchived());
    articleDTO.setCreationDate(articleEntity.getCreationDate());
    articleDTO.setSummary(articleEntity.getSummary());
    articleDTO.setShowInHomePage(articleEntity.isShowInHomePage());
    if (CollectionUtils.isNotEmpty(articleEntity.getTags())) {
      articleDTO.setTags(
          articleEntity.getTags().stream().map(TagMapper::toTagDTO).collect(Collectors.toSet()));
    }

    if (CollectionUtils.isNotEmpty(articleEntity.getCategories())) {
      articleDTO.setCategories(
          articleEntity.getCategories().stream()
              .map(ArticleMapper::toArticleCategoryDTO)
              .collect(Collectors.toSet()));
    }
    return articleDTO;
  }

  public static ArticleCategoryDTO toArticleCategoryDTO(ArticleCategoryEntity category) {
    ArticleCategoryDTO categoryDTO = new ArticleCategoryDTO();
    categoryDTO.setId(category.getId());
    categoryDTO.setName(category.getName());
    return categoryDTO;
  }

  public static void toArticleEntity(ArticleDTO articleDTO, ArticleEntity articleEntity) {
    articleEntity.setTitle(articleDTO.getTitle());
    articleEntity.setContent(articleDTO.getContent());
    articleEntity.setAuthor(articleDTO.getAuthor());
    articleEntity.setArchived(articleDTO.isArchived());
    articleEntity.setSummary(articleDTO.getSummary());
    articleEntity.setShowInHomePage(articleDTO.isShowInHomePage());
  }

  // TODO do not like this
  public static List<ArticleEntity> mapArticleEntities(List<Long> articleIds) {
    return articleIds.stream()
        .map(articleId -> {
              ArticleEntity article = new ArticleEntity();
              article.setId(articleId);
              return article;
            })
        .toList();
  }

  static class ArticleJooqMapper implements RecordMapper<Record, ArticleEntity> {
    public static final String ARTICLE_CATEGORY_KEYWORD = "article_category";

    @Override
    public ArticleEntity map(Record record) {
      ArticleEntity article = new ArticleEntity();

      article.setId(record.get(ARTICLE.ID));
      article.setTitle(record.get(ARTICLE.TITLE));
      article.setAuthor(record.get(ARTICLE.AUTHOR));
      article.setContent(record.get(ARTICLE.CONTENT));
      article.setArchived(record.get(ARTICLE.ARCHIVED));
      article.setCreationDate(record.get(ARTICLE.CREATION_DATE));
      article.setSummary(record.get(ARTICLE.SUMMARY));
      article.setShowInHomePage(record.get(ARTICLE.SHOW_IN_HOME_PAGE));
      article.setTags(mapTagEntities(record));
      article.setCategories(mapArticleCategoryEntities(record));
      return article;
    }

    private Set<ArticleCategoryEntity> mapArticleCategoryEntities(Record record) {
      Result<Record> tagsResult = record.get(ARTICLE_CATEGORY_KEYWORD, Result.class);
      Set<ArticleCategoryEntity> articleCategoryEntities = new HashSet<>();
      if (tagsResult != null) {
        for (Record tagRecord : tagsResult) {
          ArticleCategoryEntity articleCategoryEntity = new ArticleCategoryEntity();
          articleCategoryEntity.setId(tagRecord.get(ARTICLE_CATEGORY.ID));
          articleCategoryEntity.setName(tagRecord.get(ARTICLE_CATEGORY.NAME));
          articleCategoryEntities.add(articleCategoryEntity);
        }
      }
      return articleCategoryEntities;
    }
  }
}
