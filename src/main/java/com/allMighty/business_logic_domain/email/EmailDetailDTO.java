package com.allMighty.business_logic_domain.email;

import com.allMighty.global_operation.dto.AbstractDTO;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetailDTO extends AbstractDTO {
    @Email(message = "Please provide a valid email address.")
    private String email;
    private String name;
    private String message;
    private String phoneNumber;
    private Long id;
}
