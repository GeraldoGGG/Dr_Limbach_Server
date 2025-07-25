package com.allMighty.business_logic_domain.image;

import static com.example.jooq.generated.tables.Image.IMAGE;

import com.allMighty.enitity.ImageEntity;
import com.allMighty.enumeration.EntityType;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
  public void deleteImagesByEntityReferenceAndContentType(
      List<Long> entityReferenceIds, EntityType entityType) {
    dsl.deleteFrom(IMAGE)
        .where(IMAGE.ENTITY_REFERENCE_ID
                .in(entityReferenceIds)
                .and(IMAGE.ENTITY_TYPE.eq(entityType.name())))
        .execute();
  }
}
