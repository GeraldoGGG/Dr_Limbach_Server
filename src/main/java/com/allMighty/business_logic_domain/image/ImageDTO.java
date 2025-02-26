package com.allMighty.business_logic_domain.image;

import com.allMighty.enumeration.ImageContentType;
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
  ImageContentType imageContentType;
}
