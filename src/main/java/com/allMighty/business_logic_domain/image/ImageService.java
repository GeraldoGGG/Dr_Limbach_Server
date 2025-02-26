package com.allMighty.business_logic_domain.image;

import com.allMighty.enumeration.EntityType;

import java.util.List;

public interface ImageService {

  List<ImageDTO> getImages(Long entityReferenceId, EntityType entityType);

  boolean imageExists(Long imageId);
}
