package com.allMighty.business_logic_domain.image;

import com.allMighty.enumeration.ImageContentType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
  Long id;
  Long entityReferenceId;
  Long type;
  String url;
  String imageData;
  byte[] imageDataByte;
  @NotNull ImageContentType imageContentType;
}
