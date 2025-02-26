package com.allMighty.business_logic_domain.article;

import static com.allMighty.business_logic_domain.article.ArticleMapper.*;
import static com.allMighty.global_operation.filter.JooqConditionBuilder.buildConditions;

import com.allMighty.business_logic_domain.image.ImageDTO;
import com.allMighty.business_logic_domain.image.ImageRepository;
import com.allMighty.business_logic_domain.tag.TagRepository;
import com.allMighty.enitity.ArticleEntity;
import com.allMighty.enitity.TagEntity;
import com.allMighty.global_operation.BaseService;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import com.allMighty.global_operation.filter.FilterParser;
import com.allMighty.global_operation.response.page.PageDescriptor;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleService extends BaseService {

  private final FilterParser<ArticleField> filterParser = new FilterParser<>(ArticleField.values());

  private final ArticleRepository articleRepository;
  private final TagRepository tagRepository;
  private final ImageRepository imageRepository;

  public List<ArticleDTO> getArticles(List<String> filters, PageDescriptor pageDescriptor) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    return articleRepository.getAllArticles(conditions, pageDescriptor).stream()
        .map(ArticleMapper::toArticleDTO)
        .collect(Collectors.toList());
  }

  public ArticleDTO getArticleById(Long id) {
    ArticleDTO articleDTO =
        articleRepository
            .findById(id)
            .map(ArticleMapper::toArticleDTO)
            .orElseThrow(() -> new BadRequestException("Article not found!"));
    // articleDTO.setImages(imageService.getImagesByEntityReference(id, EntityType.ARTICLE));
    return articleDTO;
  }

  public Long count(List<String> filters) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    return articleRepository.count(conditions);
  }

  @Transactional
  public Long updateArticle(Long id, ArticleDTO articleDTO) {
    ArticleEntity articleEntity =
        articleRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("Article not found!"));
    toArticleEntity(articleDTO, articleEntity);

    Set<TagEntity> tagEntities = tagRepository.handleTagEntities(articleDTO.getTags(), em);
    articleEntity.setTags(tagEntities);

    ArticleEntity saved = em.merge(articleEntity);
    return saved.getId();
  }

  @Transactional
  public Long createArticle(ArticleDTO articleDTO) {
    ArticleEntity articleEntity = new ArticleEntity();
    toArticleEntity(articleDTO, articleEntity);

    Set<TagEntity> tagEntities = tagRepository.handleTagEntities(articleDTO.getTags(), em);
    articleEntity.setTags(tagEntities);

    ArticleEntity saved = em.merge(articleEntity);

   /* List<ImageDTO> images = articleDTO.getImages();
    imageRepository.handleImageEntities(images, saved, em);*/

    return saved.getId();
  }
}
