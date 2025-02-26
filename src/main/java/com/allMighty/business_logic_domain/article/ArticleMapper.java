package com.allMighty.business_logic_domain.article;

import com.allMighty.business_logic_domain.tag.TagDTO;
import com.allMighty.business_logic_domain.tag.TagMapper;
import com.allMighty.enitity.ArticleEntity;
import com.allMighty.enitity.TagEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.jooq.generated.tables.Article.ARTICLE;
import static com.example.jooq.generated.tables.Tag.TAG;

public class ArticleMapper {

  public static ArticleDTO toArticleDTO(ArticleEntity articleEntity) {
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setId(articleEntity.getId());
    articleDTO.setTitle(articleEntity.getTitle());
    articleDTO.setContent(articleEntity.getContent());
    articleDTO.setAuthor(articleEntity.getAuthor());
    articleDTO.setArchived(articleEntity.isArchived());

    if (CollectionUtils.isNotEmpty(articleEntity.getTags())) {
      Set<TagDTO> tagDTOs =
          articleEntity.getTags().stream()
                  .map(TagMapper::toTagDTO)
                  .collect(Collectors.toSet());
      articleDTO.setTags(tagDTOs);
    }
    return articleDTO;
  }

  public static void toArticleEntity(ArticleDTO articleDTO, ArticleEntity articleEntity) {
    articleEntity.setTitle(articleDTO.getTitle());
    articleEntity.setContent(articleDTO.getContent());
    articleEntity.setAuthor(articleDTO.getAuthor());
    articleEntity.setArchived(articleDTO.isArchived());
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

      Result<Record> tagsResult = record.get("tags", Result.class);
      Set<TagEntity> tags = new HashSet<>();
      if (tagsResult != null) {
        for (Record tagRecord : tagsResult) {
          TagEntity tag = new TagEntity();
          tag.setId(tagRecord.get(TAG.ID));
          tag.setName(tagRecord.get(TAG.NAME));
          tag.setVersion(tagRecord.get(TAG.VERSION));
          tags.add(tag);
        }
      }
      article.setTags(tags);


/*
      //TODO s esht entitet kjo duhet ber ne map m vete ne keto raste

      Result<Record> imagesResult = record.get("images", Result.class);
      Set<TagEntity> tags = new HashSet<>();
      if (tagsResult != null) {
        for (Record tagRecord : tagsResult) {
          TagEntity tag = new TagEntity();
          tag.setId(tagRecord.get(TAG.ID));
          tag.setName(tagRecord.get(TAG.NAME));
          tag.setVersion(tagRecord.get(TAG.VERSION));
          tags.add(tag);
        }
      }
      article.setTags(tags);*/

      return article;
    }
  }
}
