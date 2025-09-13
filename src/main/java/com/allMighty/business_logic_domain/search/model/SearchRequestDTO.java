package com.allMighty.business_logic_domain.search.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
 public class SearchRequestDTO {
    private String searchWord;
    private Boolean excludeEvents;
    private Boolean excludeAnalysis;
    private Boolean excludeServices;
    private Boolean excludeArticles;

    private SearchFilter searchFilter;

    public boolean includeEvents() {
        return excludeEvents == null || !excludeEvents;
    }

    public boolean includeAnalysis() {
        return excludeAnalysis == null || !excludeAnalysis;
    }

    public boolean includeServices() {
        return excludeServices == null || !excludeServices;
    }

    public boolean includeArticles() {
        return excludeArticles == null || !excludeArticles;
    }

    @Getter
    @Setter
    public static class SearchFilter{
        private List<String> analysisFilters = Collections.emptyList();
    }
}
