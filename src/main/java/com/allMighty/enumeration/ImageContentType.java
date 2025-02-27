package com.allMighty.enumeration;

import lombok.Getter;
import org.springframework.http.MediaType;

@Getter
public enum ImageContentType {
  JPEG(MediaType.IMAGE_JPEG),
  PNG(MediaType.IMAGE_PNG),
  GIF(MediaType.IMAGE_GIF);

  private final MediaType mediaType;

  ImageContentType(MediaType mediaType) {
    this.mediaType = mediaType;
  }

}
