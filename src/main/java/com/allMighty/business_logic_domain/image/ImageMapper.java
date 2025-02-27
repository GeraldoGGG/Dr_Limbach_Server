package com.allMighty.business_logic_domain.image;

import static com.example.jooq.generated.tables.Image.IMAGE;

import com.allMighty.client.UrlProperty;
import com.allMighty.enitity.ImageEntity;
import com.allMighty.enumeration.ImageContentType;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class ImageMapper {

  public static ImageDTO mapToImageDTO(ImageEntity imageEntity, String imageBaseUrl) {
    ImageDTO imageDto = new ImageDTO();
    imageDto.setUrl(getImageUrl(imageEntity, imageBaseUrl));
    imageDto.setImageContentType(imageEntity.getImageContentType());
    imageDto.setEntityReferenceId(imageEntity.getEntityReferenceId());

    return imageDto;
  }

  private static String getImageUrl(ImageEntity imageEntity, String imageBaseUrl) {
    return imageBaseUrl + UrlProperty.Image.PATH + "/" + imageEntity.getId();
  }

  public static ImageDTO mapToImageDTO_WithData(ImageEntity imageEntity) {
    ImageDTO imageDto = new ImageDTO();
    imageDto.setId(imageEntity.getId());
    imageDto.setImageContentType(imageEntity.getImageContentType());
    imageDto.setImageDataByte(imageEntity.getImageData());
    return imageDto;
  }

  static class ImageJooqMapper implements RecordMapper<Record, ImageEntity> {

    @Override
    public ImageEntity map(Record record) {
      ImageEntity imageEntity = new ImageEntity();
      imageEntity.setId(record.get(IMAGE.ID));
      imageEntity.setImageContentType(record.get(IMAGE.IMAGE_CONTENT_TYPE, ImageContentType.class));
      imageEntity.setEntityReferenceId(record.get(IMAGE.ENTITY_REFERENCE_ID));
      return imageEntity;
    }
  }

  static class ImageDataJooqMapper implements RecordMapper<Record, ImageEntity> {

    @Override
    public ImageEntity map(Record record) {
      ImageEntity imageEntity = new ImageEntity();
      imageEntity.setId(record.get(IMAGE.ID));
      imageEntity.setImageContentType(record.get(IMAGE.IMAGE_CONTENT_TYPE, ImageContentType.class));
      imageEntity.setImageData(record.get(IMAGE.IMAGE_DATA));
      return imageEntity;
    }
  }
}
