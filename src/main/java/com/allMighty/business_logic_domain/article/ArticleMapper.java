package com.allMighty.business_logic_domain.article;

import static com.allMighty.business_logic_domain.tag.TagMapper.TagJooqMapper.mapTagEntities;
import static com.example.jooq.generated.tables.Article.ARTICLE;

import com.allMighty.business_logic_domain.tag.TagDTO;
import com.allMighty.business_logic_domain.tag.TagMapper;
import com.allMighty.enitity.ArticleEntity;
import com.allMighty.enitity.TagEntity;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Record;
import org.jooq.RecordMapper;

// TODO this are mechanical mappers it would be wise to use some library maybe mapstruct
public class ArticleMapper {

  public static ArticleDTO toArticleDTO(ArticleEntity articleEntity) {
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setId(articleEntity.getId());
    articleDTO.setTitle(articleEntity.getTitle());
    articleDTO.setContent(articleEntity.getContent());
    articleDTO.setAuthor(articleEntity.getAuthor());
    articleDTO.setArchived(articleEntity.isArchived());
    articleDTO.setVersion(articleEntity.getVersion());
    articleDTO.setCreationDate(articleEntity.getCreationDate());
    articleDTO.setSummary(articleEntity.getSummary());

    if (CollectionUtils.isNotEmpty(articleEntity.getTags())) {
      Set<TagDTO> tagDTOs =
          articleEntity.getTags().stream().map(TagMapper::toTagDTO).collect(Collectors.toSet());
      articleDTO.setTags(tagDTOs);
    }
    return articleDTO;
  }

  public static void toArticleEntity(ArticleDTO articleDTO, ArticleEntity articleEntity) {
    articleEntity.setTitle(articleDTO.getTitle());
    articleEntity.setContent(articleDTO.getContent());
    articleEntity.setAuthor(articleDTO.getAuthor());
    articleEntity.setArchived(articleDTO.isArchived());
    articleEntity.setVersion(articleDTO.getVersion());
    articleEntity.setCreationDate(articleDTO.getCreationDate());
    articleEntity.setSummary(articleDTO.getSummary());
  }

  public static List<ArticleEntity> mapArticleEntities(List<Long> articleIds) {
    List<ArticleEntity> articles =
        articleIds.stream()
            .map(
                articleId -> {
                  ArticleEntity article = new ArticleEntity();
                  article.setId(articleId);
                  return article;
                })
            .toList();
    return articles;
  }

  static class ArticleJooqMapper implements RecordMapper<Record, ArticleEntity> {

    @Override
    public ArticleEntity map(Record record) {
      ArticleEntity article = new ArticleEntity();

      article.setId(record.get(ARTICLE.ID));
      article.setVersion(record.get(ARTICLE.VERSION));
      article.setTitle(record.get(ARTICLE.TITLE));
      article.setAuthor(record.get(ARTICLE.AUTHOR));
      article.setContent(record.get(ARTICLE.CONTENT));
      article.setArchived(record.get(ARTICLE.ARCHIVED));
      article.setRemoved(record.get(ARTICLE.REMOVED));
      article.setCreationDate(record.get(ARTICLE.CREATION_DATE));
      article.setSummary(record.get(ARTICLE.SUMMARY));
      Set<TagEntity> tags = mapTagEntities(record);
      article.setTags(tags);
      return article;
    }
  }
}
