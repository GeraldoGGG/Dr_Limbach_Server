package com.allMighty.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "image")
public class ImageEntity extends AbstractEntity {
    @Column
    private String fileName;

    @Column
    private Integer height;

    @Column
    private Integer width;

    @Column(name = "image_data")
    private byte[] imageData;

    @Column(name = "file_key")
    private String fileKey;

    @Column(name = "file_version_id")
    private String versionId;

}
