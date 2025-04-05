package com.allMighty.business_logic_domain.search;

import static com.allMighty.business_logic_domain.search.SearchMapper.EVENT_SEARCH_KEYWORD;
import static com.example.jooq.generated.tables.Event.EVENT;
import static org.jooq.impl.DSL.*;
import static org.jooq.impl.DSL.select;

import com.allMighty.business_logic_domain.search.model.SearchRequestDTO;
import com.allMighty.business_logic_domain.search.model.SearchResponseDTO;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SearchRepository {
  private final DSLContext dsl;

  private final SearchMapper.SearchJooqMapper searchJooqMapper =
      new SearchMapper.SearchJooqMapper();

  public SearchResponseDTO search(SearchRequestDTO searchRequestDTO) {

    String searchWord = searchRequestDTO.searchWord();

    return dsl.select(
            multiset(
                    select(EVENT.ID, EVENT.TITLE)
                            .from(EVENT)
                            .where(field("similarity(title, ?)", Double.class, searchWord).gt(0.2))  // Explicit similarity threshold
                            .orderBy(field("similarity(title, ?)", Double.class, searchWord).desc())) // Order by similarity

                .as(EVENT_SEARCH_KEYWORD))
        .fetchOne(searchJooqMapper);
  }
}
