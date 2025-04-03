package com.allMighty.enitity;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "email")
public class EmailEntity extends AbstractEntity {
    @Column
    @Email(message = "Invalid email address format")
    private String emailAddress;
}
