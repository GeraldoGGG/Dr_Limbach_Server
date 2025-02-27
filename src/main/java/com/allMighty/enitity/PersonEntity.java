package com.allMighty.enitity;

import com.allMighty.config.security.person.role.Role;
import com.allMighty.enitity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
@Getter
@Setter
public class PersonEntity extends AbstractEntity {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
