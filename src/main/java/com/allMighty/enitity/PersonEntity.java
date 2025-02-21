package com.allMighty.enitity;

import com.allMighty.config.security.person.role.Role;
import com.allMighty.enitity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
@Getter
public class PersonEntity extends AbstractEntity {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
