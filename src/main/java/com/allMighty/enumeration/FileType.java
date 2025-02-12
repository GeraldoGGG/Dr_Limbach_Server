package com.allMighty.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FileType {

    XLSX("xlsx"),
    PDF("pdf"),
    JPEG("jpeg"),
    JPG("jpg"),
    PNG("png"),
    DOCX("docx");

    private String extension;

    public String extension() {
        return extension;
    }
}
