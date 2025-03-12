package com.allMighty.business_logic_domain.event;

import static com.example.jooq.generated.tables.Event.EVENT;

import com.allMighty.enitity.EventEntity;
import com.allMighty.global_operation.response.page.PageDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventRepository {

  private final DSLContext dsl;
  private final EventMapper.EventJooqMapper eventJooqMapper = new EventMapper.EventJooqMapper();

  public Long count() {
    return count(new ArrayList<>());
  }

  public Long count(List<Condition> conditions) {
    return dsl.select(DSL.countDistinct(EVENT.ID))
        .from(EVENT)
        .where(conditions)
        .fetchSingleInto(Long.class);
  }

  public List<EventEntity> getAllEvents(List<Condition> conditions, PageDescriptor pageDescriptor) {
    if (pageDescriptor == null) {
      pageDescriptor = PageDescriptor.maxDataDescriptor();
    }
    Long offset = pageDescriptor.getOffset();
    Long pageSize = pageDescriptor.getPageSize();

    return dsl.select(
            EVENT.ID,
            EVENT.VERSION,
            EVENT.TITLE,
            EVENT.CONTENT,
            EVENT.EVENT_DATE,
            EVENT.EVENT_DURATION,
            EVENT.PRICE,
            EVENT.ARCHIVED,
            EVENT.REMOVED,
            EVENT.ORGANIZATION,
            EVENT.LOCATION,
            EVENT.GUEST_NUMBER)
        .from(EVENT)
        .where(conditions)
        .groupBy(
            EVENT.ID,
            EVENT.VERSION,
            EVENT.TITLE,
            EVENT.CONTENT,
            EVENT.EVENT_DATE,
            EVENT.EVENT_DURATION,
            EVENT.PRICE,
            EVENT.ARCHIVED,
            EVENT.REMOVED,
            EVENT.ORGANIZATION,
            EVENT.GUEST_NUMBER)
        .offset(offset)
        .limit(pageSize)
        .fetch(eventJooqMapper);
  }

  public Optional<EventEntity> findById(Long id) {
    Condition eq = EVENT.ID.eq(id);
    List<EventEntity> events = getAllEvents(Collections.singletonList(eq), null);
    return events.stream().findFirst();
  }
}
