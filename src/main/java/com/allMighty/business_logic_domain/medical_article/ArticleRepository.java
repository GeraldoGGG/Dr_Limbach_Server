package com.allMighty.business_logic_domain.medical_article;

import static com.example.jooq.generated.tables.MedicalArticle.MEDICAL_ARTICLE;
import static com.example.jooq.generated.tables.MedicalArticleEntityTags.MEDICAL_ARTICLE_ENTITY_TAGS;
import static com.example.jooq.generated.tables.Tag.TAG;

import com.allMighty.enitity.MedicalArticleEntity;
import com.allMighty.global_operation.response.page.PageDescriptor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {

  @PersistenceContext private EntityManager entityManager;

  private final DSLContext dsl;

  public Long count(List<Condition> conditions) {
    return dsl.select(DSL.countDistinct(MEDICAL_ARTICLE.ID))
        .from(MEDICAL_ARTICLE)
        .where(conditions)
        .fetchSingleInto(Long.class);
  }

  public List<ArticleDTO> getAllArticleDTO(List<Condition> conditions) {
    return getAllArticleDTO(conditions, null);
  }

  public List<ArticleDTO> getAllArticleDTO(
      List<Condition> conditions, PageDescriptor pageDescriptor) {

    // @formatter:off
    SelectConditionStep<Record> where =
        dsl.select()
            .from(MEDICAL_ARTICLE)
            .leftJoin(MEDICAL_ARTICLE_ENTITY_TAGS)
            .on(MEDICAL_ARTICLE.ID.eq(MEDICAL_ARTICLE_ENTITY_TAGS.MEDICAL_ARTICLE_ID))
            .leftJoin(TAG)
            .on(TAG.ID.eq(MEDICAL_ARTICLE_ENTITY_TAGS.TAG_ID))
            .where(conditions);

    Result<Record> jooqRecords;
    if (pageDescriptor != null) {
      Long offset = pageDescriptor.getOffset();
      Long pageSize = pageDescriptor.getPageSize();
      jooqRecords = where.offset(offset).limit(pageSize).fetch();
    } else {
      jooqRecords = where.fetch();
    }

    // @formatter:on
    return jooqRecords.stream().map(ArticleMapper::toArticleDTO).collect(Collectors.toList());
  }

  public Optional<MedicalArticleEntity> findById(Long id) {
    return Optional.ofNullable(
        dsl.selectFrom(MEDICAL_ARTICLE)
            .where(MEDICAL_ARTICLE.ID.eq(id))
            .fetchOneInto(MedicalArticleEntity.class));
  }



  public MedicalArticleEntity save(MedicalArticleEntity medicalArticleEntity) {
    if (medicalArticleEntity.getId() != null) {
      return entityManager.merge(medicalArticleEntity);
    } else {
      entityManager.persist(medicalArticleEntity);
      return medicalArticleEntity;
    }
    //TODO test the behavior of the id when is persisted and merged
  }
}
