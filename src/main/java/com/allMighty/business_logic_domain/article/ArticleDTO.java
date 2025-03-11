package com.allMighty.business_logic_domain.article;

import com.allMighty.business_logic_domain.image.ImageDTO;
import com.allMighty.business_logic_domain.tag.TagDTO;
import com.allMighty.global_operation.dto.AbstractDTO;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

  private Long version;

  private String summary;

  private LocalDateTime creationDate;

  private Set<TagDTO> tags = new HashSet<>();

  private List<ImageDTO> images = new ArrayList<>();
}
