package com.allMighty.enumeration;

public enum ImageContentType {
  JPEG("image/jpeg"),
  PNG("image/png"),
  GIF("image/gif"),
  BMP("image/bmp");

  private final String headerValue;

  ImageContentType(String headerValue) {
    this.headerValue = headerValue;
  }

  public String getHeaderValue() {
    return headerValue;
  }
}
