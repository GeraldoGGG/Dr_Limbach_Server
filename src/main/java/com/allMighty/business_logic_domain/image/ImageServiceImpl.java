package com.allMighty.business_logic_domain.image;

import com.allMighty.enitity.ImageEntity;
import com.allMighty.enumeration.EntityType;
import com.allMighty.global_operation.BaseService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl extends BaseService implements ImageService {
  @Value("${app.image-url}")
  private String imageBaseUrl;

  private final ImageRepository imageRepository;

  public List<ImageDTO> getImages(Long entityReferenceId, EntityType entityType) {
    List<ImageEntity> imagesByEntityReference =
        imageRepository.getImagesByEntityReference(entityReferenceId, entityType);

    List<ImageDTO> imagesDto = new ArrayList<>();
    for (ImageEntity imageEntity : imagesByEntityReference) {
      ImageDTO imageDto = new ImageDTO();
      imageDto.setUrl(imageBaseUrl + "direjtoria e leshit " + "/" + imageEntity.getId());
      imagesDto.add(imageDto);
    }
    return imagesDto;
  }

  /*public void create(){
      byte[] image = Base64.getDecoder().decode(competencyDTO.getImage());

  }*/

  public boolean imageExists(Long imageId) {
    return imageRepository.imageExists(imageId);
  }
}
