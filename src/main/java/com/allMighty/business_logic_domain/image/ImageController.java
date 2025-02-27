package com.allMighty.business_logic_domain.image;

import com.allMighty.client.UrlProperty.Image;
import com.allMighty.enumeration.ImageContentType;
import com.allMighty.global_operation.exception_management.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Image.PATH)
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  @GetMapping("/{id}")
  public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
    ImageDTO imageDTO = imageService.getImageById(id);

    if (imageDTO.getImageDataByte() == null) {
      throw new InternalServerException("Image does not hava data!");
    }

    HttpHeaders headers = new HttpHeaders();

    ImageContentType imageContentType = imageDTO.getImageContentType();

    headers.setContentType(imageContentType.getMediaType());
    headers.setContentLength(imageDTO.getImageDataByte().length);

    return new ResponseEntity<>(imageDTO.getImageDataByte(), headers, HttpStatus.OK);
  }
}
