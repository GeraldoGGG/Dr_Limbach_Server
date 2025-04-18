package com.allMighty.business_logic_domain.image;

import static com.allMighty.business_logic_domain.image.ImageMapper.mapToImageDTO;

import com.allMighty.enitity.ImageEntity;
import com.allMighty.enumeration.EntityType;
import com.allMighty.global_operation.BaseService;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import com.allMighty.global_operation.exception_management.exception.NotFoundException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl extends BaseService implements ImageService {
  @Value("${app.image-url}")
  private String imageBaseUrl;

  private final ImageRepository imageRepository;

  public Map<Long, List<ImageDTO>> getImages(List<Long> entityReferenceIds, EntityType entityType) {
    List<ImageEntity> imagesByEntityReference =
        imageRepository.getImagesByEntityReference(entityReferenceIds, entityType);

    return imagesByEntityReference.stream()
        .map(e -> mapToImageDTO(e, imageBaseUrl))
        .collect(Collectors.groupingBy(ImageDTO::getEntityReferenceId));
  }

  public void deleteImages(List<Long> entityReferenceIds, EntityType entityType) {
    imageRepository.deleteImagesByEntityReferenceAndContentType(entityReferenceIds, entityType);
  }

  @Override
  @Transactional
  public void createImages(List<ImageDTO> images, EntityType entityType, Long entityReferenceId) {

    if (CollectionUtils.isEmpty(images)) {
      return;
    }

    for (ImageDTO imageDTO : images) {
      if (imageDTO.getImageData() == null) {
        continue;
      }

      if (imageDTO.getImageContentType() == null) {
        throw new BadRequestException("Image content type is required");
      }
      ImageEntity imageEntity = new ImageEntity();
      imageEntity.setEntityReferenceId(entityReferenceId);
      imageEntity.setEntityType(entityType);
      imageEntity.setImageContentType(imageDTO.getImageContentType());
      try {
        byte[] image = Base64.getDecoder().decode(imageDTO.getImageData());
        imageEntity.setImageData(image);

        em.persist(imageEntity);

      } catch (IllegalArgumentException iae) {
        throw new BadRequestException("Invalid image format." + iae.getMessage());
      }
    }
  }

  @Override
  public ImageDTO getImageById(Long imageId) {
    return imageRepository
        .getImageById(imageId)
        .map(ImageMapper::mapToImageDTO_WithData)
        .orElseThrow(() -> new NotFoundException("Image not found for imageId: " + imageId));
  }
}
