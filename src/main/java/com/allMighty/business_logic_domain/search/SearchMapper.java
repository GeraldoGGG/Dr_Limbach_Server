package com.allMighty.business_logic_domain.search;

import static com.example.jooq.generated.tables.Event.EVENT;

import com.allMighty.business_logic_domain.search.model.SearchResponseDTO;
import com.allMighty.business_logic_domain.search.model.SearchUnitResponseDTO;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;

public class SearchMapper {
  public static final String EVENT_SEARCH_KEYWORD = "events";

  static class SearchJooqMapper implements RecordMapper<Record, SearchResponseDTO> {

    @Override
    public SearchResponseDTO map(Record searchRecord) {
      SearchResponseDTO dto = new SearchResponseDTO();

      //events
      Result<Record> eventsResult = searchRecord.get(EVENT_SEARCH_KEYWORD, Result.class);
      List<SearchUnitResponseDTO> eventsFound = mapEvents(eventsResult);





      dto.setEventsFound(eventsFound);
      return dto;
    }

    private static List<SearchUnitResponseDTO> mapEvents(Result<Record> eventsResult) {
      List<SearchUnitResponseDTO> events = new ArrayList<>();
      if (eventsResult != null) {
        for (Record record : eventsResult) {
          SearchUnitResponseDTO event = new SearchUnitResponseDTO();
          event.setId(record.get(EVENT.ID));
          event.setTitle(record.get(EVENT.TITLE));
          events.add(event);
        }
      }
      return events;
    }
  }
}
