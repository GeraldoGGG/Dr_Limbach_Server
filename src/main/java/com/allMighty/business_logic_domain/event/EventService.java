package com.allMighty.business_logic_domain.event;

import static com.allMighty.business_logic_domain.event.EventMapper.toEventEntity;
import static com.allMighty.global_operation.filter.JooqConditionBuilder.buildConditions;

import com.allMighty.business_logic_domain.image.ImageDTO;
import com.allMighty.business_logic_domain.image.ImageService;
import com.allMighty.enitity.EventEntity;
import com.allMighty.enumeration.EntityType;
import com.allMighty.global_operation.BaseService;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import com.allMighty.global_operation.filter.FilterParser;
import com.allMighty.global_operation.response.page.PageDescriptor;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventService extends BaseService {
  private final FilterParser<EventField> filterParser = new FilterParser<>(EventField.values());

  private final EventRepository eventRepository;
  private final ImageService imageService;

  public Long count() {
    return eventRepository.count();
  }

  public Long count(List<String> filters) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    return eventRepository.count(conditions);
  }

  public List<EventDTO> getEvents(List<String> filters, PageDescriptor pageDescriptor) {

    List<Condition> conditions = buildConditions(filters, filterParser);
    List<EventDTO> eventDTOS =
        eventRepository.getAllEvents(conditions, pageDescriptor).stream()
            .map(EventMapper::toEventDTO)
            .toList();

    addImages(eventDTOS);

    return eventDTOS;
  }

  public EventDTO getEventById(Long id) {
    EventDTO eventDTO =
        eventRepository
            .findById(id)
            .map(EventMapper::toEventDTO)
            .orElseThrow(() -> new BadRequestException("Article not found!"));

    addImages(Collections.singletonList(eventDTO));

    return eventDTO;
  }

  public Long createEvent(EventDTO articleDTO) {
    EventEntity eventEntity = new EventEntity();
    toEventEntity(articleDTO, eventEntity);

    EventEntity saved = em.merge(eventEntity);

    List<ImageDTO> images = articleDTO.getImages();
    imageService.createImages(images, EntityType.EVENT, saved.getId());

    return saved.getId();
  }

  public Long updateEvent(Long id, EventDTO articleDTO) {
    EventEntity eventEntity =
        eventRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("Article not found!"));

    toEventEntity(articleDTO, eventEntity);

    EventEntity saved = em.merge(eventEntity);

    List<ImageDTO> imageDTOs = articleDTO.getImages();

    boolean hasImageData =
        imageDTOs.stream().map(ImageDTO::getImageData).anyMatch(StringUtils::isNotBlank);

    if (hasImageData) {
      imageService.deleteImages(Collections.singletonList(id), EntityType.EVENT);
      imageService.createImages(imageDTOs, EntityType.EVENT, id);
    }

    return saved.getId();
  }

  private void addImages(List<EventDTO> eventDTOS) {
    List<Long> referenceIds = eventDTOS.stream().map(EventDTO::getId).toList();
    Map<Long, List<ImageDTO>> images = imageService.getImages(referenceIds, EntityType.EVENT);
    eventDTOS.forEach(dto -> dto.setImages(images.get(dto.getId())));
  }
}
