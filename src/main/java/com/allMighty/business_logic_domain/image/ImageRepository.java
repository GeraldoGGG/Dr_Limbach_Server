package com.allMighty.business_logic_domain.image;

import com.allMighty.enitity.ArticleEntity;
import com.allMighty.enitity.ImageEntity;
import com.allMighty.enumeration.EntityType;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Base64;
import java.util.List;

import static com.example.jooq.generated.tables.Image.IMAGE;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

  private final DSLContext dsl;

  public List<ImageEntity> getImagesByEntityReference(
      Long entityReferenceId, EntityType entityType) {
    return dsl.select()
        .from(IMAGE)
        .where(
            IMAGE
                .ENTITY_REFERENCE_ID
                .eq(entityReferenceId)
                .and(IMAGE.ENTITY_TYPE.eq(entityType.name())))
        .fetchInto(ImageEntity.class); // Mapping the result into ImageEntity objects
  }

  public List<ImageEntity> getAllImages() {
    return dsl.select().from(IMAGE).fetchInto(ImageEntity.class);
  }

  public boolean imageExists(Long imageId) {
    return dsl.fetchExists(dsl.selectOne().from(IMAGE).where(IMAGE.ID.eq(imageId)));
  }
/*
  public void handleImageEntities(List<ImageDTO> images, ArticleEntity saved, EntityManager em) {
    for(ImageDTO imageDTO : images) {
      Long imageId = imageDTO.getId();
      if(imageId != null){
        boolean imageExists = false; //imageService.imageExists(imageId);
        if(imageExists){
          continue;
        }
      }

      ImageEntity imageEntity = new ImageEntity();
      byte[] imageData = Base64.getDecoder().decode(imageDTO.getImageData());
      imageEntity.setImageData(imageData);
      imageEntity.setEntityReferenceId(saved.getId());
      imageEntity.setImageContentType(imageDTO.getImageContentType());
      em.persist(imageEntity);
    }
  }*/

}
