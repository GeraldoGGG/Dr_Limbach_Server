package com.allMighty.business_logic_domain.article;

import static com.allMighty.business_logic_domain.article.ArticleMapper.*;
import static com.allMighty.global_operation.filter.JooqConditionBuilder.buildConditions;

import com.allMighty.business_logic_domain.fields.ArticleField;
import com.allMighty.business_logic_domain.general.EntityIdDTO;
import com.allMighty.business_logic_domain.image.ImageDTO;
import com.allMighty.business_logic_domain.image.ImageService;
import com.allMighty.business_logic_domain.tag.TagRepository;
import com.allMighty.enitity.TagEntity;
import com.allMighty.enitity.article.ArticleCategoryEntity;
import com.allMighty.enitity.article.ArticleEntity;
import com.allMighty.enumeration.EntityType;
import com.allMighty.global_operation.BaseService;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import com.allMighty.global_operation.filter.FilterParser;
import com.allMighty.global_operation.response.page.PageDescriptor;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleService extends BaseService {

  private final FilterParser<ArticleField> filterParser = new FilterParser<>(ArticleField.values());

  private final ArticleRepository articleRepository;
  private final TagRepository tagRepository;
  private final ArticleCategoryRepository articleCategoryRepository;
  private final ImageService imageService;

  public List<ArticleDTO> getArticles(List<String> filters, PageDescriptor pageDescriptor) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    List<ArticleDTO> articleDTOS =
        articleRepository.getAllArticles(conditions, pageDescriptor).stream()
            .map(ArticleMapper::toArticleDTO)
            .toList();

    addImages(articleDTOS);

    return articleDTOS;
  }

  public ArticleDTO getArticleById(Long id) {
    ArticleDTO articleDTO =
        articleRepository
            .findById(id)
            .map(ArticleMapper::toArticleDTO)
            .orElseThrow(() -> new BadRequestException("Article not found!"));

    addImages(Collections.singletonList(articleDTO));

    return articleDTO;
  }

  public Long count() {
    return articleRepository.count();
  }

  public Long count(List<String> filters) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    return articleRepository.count(conditions);
  }

  @Transactional
  public Long updateArticle(Long id, ArticleDTO articleDTO) {
    ArticleEntity articleEntity = em.find(ArticleEntity.class, id);
    if (articleEntity == null) {
      throw new BadRequestException("Article not found!");
    }

    // map
    toArticleEntity(articleDTO, articleEntity);

    // tags
    Set<TagEntity> tagEntities = tagRepository.updateTagEntities(articleDTO.getTags(), em);
    articleEntity.setTags(tagEntities);

    // categories
    Set<ArticleCategoryEntity> categories =
        articleCategoryRepository.updateCategoryEntities(articleDTO.getCategories(), em);
    articleEntity.setCategories(categories);

    // save
    ArticleEntity saved = em.merge(articleEntity);

    // images
    List<ImageDTO> imageDTOs = articleDTO.getImages();
    boolean hasImageData =
        imageDTOs.stream().map(ImageDTO::getImageData).anyMatch(StringUtils::isNotBlank);
    if (hasImageData) {
      imageService.deleteImages(Collections.singletonList(id), EntityType.ARTICLE);
      imageService.createImages(imageDTOs, EntityType.ARTICLE, id);
    }

    return saved.getId();
  }

  @Transactional
  public Long createArticle(ArticleDTO articleDTO) {
    ArticleEntity articleEntity = new ArticleEntity();
    toArticleEntity(articleDTO, articleEntity);
    articleEntity.setCreationDate(LocalDateTime.now());

    // tags
    Set<TagEntity> tagEntities = tagRepository.updateTagEntities(articleDTO.getTags(), em);
    articleEntity.setTags(tagEntities);

    // categories
    Set<ArticleCategoryEntity> categories =
        articleCategoryRepository.updateCategoryEntities(articleDTO.getCategories(), em);
    articleEntity.setCategories(categories);

    // save
    ArticleEntity saved = em.merge(articleEntity);

    // images
    imageService.createImages(articleDTO.getImages(), EntityType.ARTICLE, saved.getId());

    return saved.getId();
  }

  private void addImages(List<ArticleDTO> articleDTOS) {
    List<Long> referenceIds = articleDTOS.stream().map(ArticleDTO::getId).toList();
    Map<Long, List<ImageDTO>> images = imageService.getImages(referenceIds, EntityType.ARTICLE);
    articleDTOS.forEach(dto -> dto.setImages(images.get(dto.getId())));
  }

  public List<EntityIdDTO> getSimpleArticles() {
    return articleRepository.getAllArticlesSimple();
  }

  public List<ArticleCategoryDTO> getAllArticleCategories() {
    return articleCategoryRepository.getAllCategories().stream()
        .map(ArticleMapper::toArticleCategoryDTO)
        .collect(Collectors.toList());
  }
}
