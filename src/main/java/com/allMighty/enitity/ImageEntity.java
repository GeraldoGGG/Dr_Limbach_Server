package com.allMighty.enitity;

import com.allMighty.enumeration.EntityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "image")
public class ImageEntity extends AbstractEntity {
    //will be used for centralized image api and to generate the url for image dto
    private Long entityReferenceId;
    @Enumerated(EnumType.STRING)
    private EntityType entityType;




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
