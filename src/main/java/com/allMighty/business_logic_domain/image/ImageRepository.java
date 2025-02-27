package com.allMighty.business_logic_domain.image;

import com.allMighty.enitity.ArticleEntity;
import com.allMighty.enitity.ImageEntity;
import com.allMighty.enumeration.EntityType;
import com.allMighty.enumeration.ImageContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static com.example.jooq.generated.tables.Image.IMAGE;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

  private final DSLContext dsl;
  private final ImageMapper.ImageJooqMapper imageJooqMapper = new ImageMapper.ImageJooqMapper();

  public List<ImageEntity> getImagesByEntityReference(
      List<Long> entityReferenceIds, EntityType entityType) {
    return dsl.select(IMAGE.ID, IMAGE.ENTITY_REFERENCE_ID, IMAGE.IMAGE_CONTENT_TYPE)
        .from(IMAGE)
        .where(
            IMAGE
                .ENTITY_REFERENCE_ID
                .in(entityReferenceIds)
                .and(IMAGE.ENTITY_TYPE.eq(entityType.name())))
        .fetch(imageJooqMapper);
  }

  public Optional<ImageEntity> getImageById(Long imageId) {
    ImageEntity image =
        dsl.select(IMAGE.ID, IMAGE.IMAGE_DATA, IMAGE.IMAGE_CONTENT_TYPE)
            .from(IMAGE)
            .where(IMAGE.ID.eq(imageId))
            .fetchOne(new ImageMapper.ImageDataJooqMapper());

    return Optional.ofNullable(image);
  }

  /*  public List<ImageEntity> getAllImages() {
    return dsl.select().from(IMAGE).fetchInto(ImageEntity.class);
  }

  public boolean imageExists(Long imageId) {
    return dsl.fetchExists(dsl.selectOne().from(IMAGE).where(IMAGE.ID.eq(imageId)));
  }*/

  @Transactional
  public int deleteImagesByEntityReferenceAndContentType(
      List<Long> entityReferenceIds, EntityType entityType) {
    return dsl.deleteFrom(IMAGE)
        .where(
            IMAGE
                .ENTITY_REFERENCE_ID
                .in(entityReferenceIds)
                .and(IMAGE.ENTITY_TYPE.eq(entityType.name())))
        .execute();
  }
}
