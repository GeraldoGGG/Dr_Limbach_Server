package com.allMighty.business_logic_domain.article;


import com.allMighty.enitity.article.ArticleCategoryEntity;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.jooq.generated.tables.ArticleCategory.ARTICLE_CATEGORY;

@Repository
@RequiredArgsConstructor
public class ArticleCategoryRepository {

    private final DSLContext dslContext;

    @Transactional
    public Set<ArticleCategoryEntity> updateCategoryEntities(Set<ArticleCategoryDTO> categories, EntityManager em) {
        if (CollectionUtils.isEmpty(categories)) {
            return Collections.emptySet();
        }

        List<ArticleCategoryEntity> allCategories = getAllCategories()
                .stream()
                .filter(c -> StringUtils.isNotEmpty(c.getName()))
                .toList();

        Map<String, ArticleCategoryEntity> nameMap = allCategories.stream()
                .collect(Collectors.toMap(ArticleCategoryEntity::getName, Function.identity()));

        Map<Long, ArticleCategoryEntity> idMap = allCategories.stream()
                .collect(Collectors.toMap(ArticleCategoryEntity::getId, Function.identity()));

        Set<ArticleCategoryEntity> categoryEntities = new HashSet<>();

        for (ArticleCategoryDTO dto : categories) {
            Long id = dto.getId();

            if (id != null && idMap.containsKey(id)) {
                ArticleCategoryEntity found = em.find(ArticleCategoryEntity.class, id);
                categoryEntities.add(found);
                continue;
            }

            String name = dto.getName();
            if (StringUtils.isEmpty(name)) {
                throw new BadRequestException("Category name cannot be empty!");
            }

            if (nameMap.containsKey(name)) {
                ArticleCategoryEntity found = em.find(ArticleCategoryEntity.class, nameMap.get(name).getId());
                categoryEntities.add(found);
                continue;
            }

            // New category
            ArticleCategoryEntity newCategory = new ArticleCategoryEntity();
            newCategory.setName(name);
            em.persist(newCategory);
            categoryEntities.add(newCategory);
        }

        return categoryEntities;
    }

    public List<ArticleCategoryEntity> getAllCategories() {
        return dslContext.select(ARTICLE_CATEGORY.ID, ARTICLE_CATEGORY.NAME)
                .from(ARTICLE_CATEGORY)
                .fetch(record -> {
                    ArticleCategoryEntity entity = new ArticleCategoryEntity();
                    entity.setId(record.get(ARTICLE_CATEGORY.ID));
                    entity.setName(record.get(ARTICLE_CATEGORY.NAME));
                    return entity;
                });

    }
}
