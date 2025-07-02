package com.allMighty.business_logic_domain.search.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
 public class SearchRequestDTO {
    private String searchWord;
    private Boolean excludeEvents;
    private Boolean excludeAnalysis;
    private Boolean excludeServices;
    private Boolean excludeArticles;

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
}
