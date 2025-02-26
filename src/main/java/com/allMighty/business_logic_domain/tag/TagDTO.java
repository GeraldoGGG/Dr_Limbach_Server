package com.allMighty.business_logic_domain.tag;


import com.allMighty.global_operation.dto.AbstractDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDTO extends AbstractDTO {
    private Long id;
    private String name;
}
