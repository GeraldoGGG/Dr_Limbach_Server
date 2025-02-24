package com.allMighty.business_logic_domain.medical_article;

import com.allMighty.global_operation.dto.AbstractDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDTO extends AbstractDTO {
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Author cannot be blank")
    private String author;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    private boolean archived;
}
