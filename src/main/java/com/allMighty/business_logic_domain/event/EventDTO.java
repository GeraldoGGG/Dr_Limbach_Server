package com.allMighty.business_logic_domain.event;

import com.allMighty.business_logic_domain.image.ImageDTO;
import com.allMighty.global_operation.dto.AbstractDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO extends AbstractDTO {
  private Long id;

  @NotBlank(message = "Title cannot be blank")
  private String title;

  @NotBlank(message = "Content cannot be blank")
  private String content;

  @NotNull(message = "Event date cannot be blank")
  private LocalDateTime eventDate;

  @NotNull(message = "Event duration cannot be blank")
  private Long eventDuration;

  private Integer price;

  private String organization;

  private Integer guestNumber;

  private String location;

  private boolean archived;

  private List<ImageDTO> images = new ArrayList<>();
}
