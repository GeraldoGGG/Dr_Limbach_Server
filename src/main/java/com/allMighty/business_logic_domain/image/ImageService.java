package com.allMighty.business_logic_domain.image;

import com.allMighty.enumeration.EntityType;
import java.util.List;
import java.util.Map;

public interface ImageService {

  Map<Long, List<ImageDTO>> getImages(List<Long> entityReferenceId, EntityType entityType);

  void deleteImages(List<Long> entityReferenceIds, EntityType entityType);

  void createImages(List<ImageDTO> images, EntityType entityType, Long entityReferenceId);

  ImageDTO getImageById(Long id);
}
