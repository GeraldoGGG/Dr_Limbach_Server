package com.allMighty.business_logic_domain.email_detail;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetailDTO {
    @Email(message = "Please provide a valid email address.")
    private String email;
    private String name;
    private String message;
    private Integer phoneNumber;
}
