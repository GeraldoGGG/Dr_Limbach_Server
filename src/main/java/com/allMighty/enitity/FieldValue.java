package com.allMighty.enitity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "field_value")
public class FieldValue extends AbstractEntity {

    private String string_value;
    private String key_value;

    @Column(name = "analysis_id", insertable = false, updatable = false)
    private Long analysisId;

}



