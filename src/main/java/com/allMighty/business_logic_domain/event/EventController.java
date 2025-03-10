package com.allMighty.business_logic_domain.event;

import static com.allMighty.client.UrlProperty.ID_PATH;
import static com.allMighty.global_operation.response.ResponseFactory.createPage;
import static com.allMighty.global_operation.response.ResponseFactory.createResponse;

import com.allMighty.client.UrlProperty.Event;
import com.allMighty.global_operation.response.EntityResponseDTO;
import com.allMighty.global_operation.response.page.EntityPageResponseDTO;
import com.allMighty.global_operation.response.page.PageDescriptor;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Event.PATH)
@RequiredArgsConstructor
@Validated
public class EventController {
  private final EventService eventService;

  @GetMapping
  public ResponseEntity<EntityPageResponseDTO<EventDTO>> getAllEvents(
      @RequestParam(name = "page", defaultValue = "1") Long page,
      @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize,
      @RequestParam(value = "filter", defaultValue = "") List<String> filters) {

    Long count = eventService.count(filters);
    PageDescriptor pageDescriptor = new PageDescriptor(page, pageSize);
    List<EventDTO> articleDTOs = eventService.getEvents(filters, pageDescriptor);
    return ResponseEntity.ok(createPage(count, articleDTOs, pageDescriptor));
  }

  @GetMapping(ID_PATH)
  public ResponseEntity<EntityResponseDTO<EventDTO>> getEventById(
      @PathVariable(name = "id") Long id) {

    EventDTO articleDTO = eventService.getEventById(id);
    return ResponseEntity.ok(createResponse(articleDTO));
  }

  @PostMapping
  public ResponseEntity<EntityResponseDTO<EventDTO>> createEvent(@RequestBody @Valid EventDTO articleDTO) {

    Long articleId = eventService.createEvent(articleDTO);
    EventDTO createdEvent = eventService.getEventById(articleId);

    return ResponseEntity.ok(createResponse(createdEvent));
  }

  @PutMapping(ID_PATH)
  public ResponseEntity<EntityResponseDTO<EventDTO>> updateEvent(@PathVariable(name = "id") Long id,
                                                                 @RequestBody @Valid EventDTO articleDTO) {

    Long articleId = eventService.updateEvent(id, articleDTO);
    EventDTO updatedEvent = eventService.getEventById(articleId);
    return ResponseEntity.ok(createResponse(updatedEvent));
  }
}
