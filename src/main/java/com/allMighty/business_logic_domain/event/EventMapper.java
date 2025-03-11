package com.allMighty.business_logic_domain.event;

import static com.example.jooq.generated.tables.Event.EVENT;

import com.allMighty.enitity.EventEntity;
import java.time.Duration;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class EventMapper {

  public static EventDTO toEventDTO(EventEntity eventEntity) {
    EventDTO eventDTO = new EventDTO();
    eventDTO.setId(eventEntity.getId());
    eventDTO.setTitle(eventEntity.getTitle());
    eventDTO.setContent(eventEntity.getContent());
    eventDTO.setEventDate(eventEntity.getEventDate());
    eventDTO.setEventDuration(eventEntity.getEventDuration());
    eventDTO.setPrice(eventEntity.getPrice());
    eventDTO.setArchived(eventEntity.isArchived());
    eventDTO.setRemoved(eventEntity.isRemoved());
    eventDTO.setVersion(eventEntity.getVersion());
    eventDTO.setOrganization(eventEntity.getOrganization());
    eventDTO.setGuestNumber(eventEntity.getGuestNumber());

    return eventDTO;
  }

  public static void toEventEntity(EventDTO eventDTO, EventEntity eventEntity) {
    eventEntity.setTitle(eventDTO.getTitle());
    eventEntity.setContent(eventDTO.getContent());
    eventEntity.setEventDate(eventDTO.getEventDate());
    eventEntity.setEventDuration(eventDTO.getEventDuration());
    eventEntity.setPrice(eventDTO.getPrice());
    eventEntity.setArchived(eventDTO.isArchived());
    eventEntity.setRemoved(eventDTO.isRemoved());
    eventEntity.setVersion(eventDTO.getVersion());
    eventEntity.setOrganization(eventDTO.getOrganization());
    eventEntity.setGuestNumber(eventDTO.getGuestNumber());
  }

  static class EventJooqMapper implements RecordMapper<Record, EventEntity> {

    @Override
    public EventEntity map(Record record) {
      EventEntity event = new EventEntity();

      event.setId(record.get(EVENT.ID));
      event.setVersion(record.get(EVENT.VERSION));
      event.setTitle(record.get(EVENT.TITLE));
      event.setContent(record.get(EVENT.CONTENT));
      event.setEventDate(record.get(EVENT.EVENT_DATE));
      event.setEventDuration(record.get(EVENT.EVENT_DURATION));
      event.setPrice(record.get(EVENT.PRICE));
      event.setArchived(record.get(EVENT.ARCHIVED));
      event.setRemoved(record.get(EVENT.REMOVED));
      event.setOrganization(record.get(EVENT.ORGANIZATION));
      event.setGuestNumber(record.get(EVENT.GUEST_NUMBER));

      return event;
    }
  }
}
