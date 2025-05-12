package com.allMighty.business_logic_domain.search;

import com.allMighty.business_logic_domain.search.model.SearchRequestDTO;
import com.allMighty.business_logic_domain.search.model.SearchResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final SearchRepository searchRepository;

    public SearchResponseDTO search(SearchRequestDTO searchRequestDTO) {
        return searchRepository.search(searchRequestDTO);
    }

}
