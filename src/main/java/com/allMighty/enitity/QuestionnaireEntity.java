package com.allMighty.enitity;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import com.allMighty.enumeration.BusinessModule;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "questionnaire")
public class QuestionnaireEntity extends AbstractEntity {

    private String question;
    private String answer;

    @Enumerated(EnumType.STRING)
    private BusinessModule businessModule;
}
