package com.allMighty.global_operation.filter;

public enum FilterOperator {
    //@formatter:off
    EQ("eq"),
    NOT_EQ("neq"),
    GT("gt"),
    LT("lt"),
    GTE("gte"),
    LTE("lte"),
    LIKE("like"),
    CUSTOM_QUERY_BUILDER_LOGIC(""),
    EQ_IGNORE_CASE("eq_case_insensitive"),
    EQ_CASE_SENSITIVE("eq_case_sensitive"),
    START_WITH("start_with"),
    IN("in"),
    IS_BLANK("is_blank"),
    IN_WITH_BLANK("in_with_blank"),
    IS_ARCHIVED("is_archived"),
    BETWEEN("between");
    //@formatter:on

    public static final String DELIMITER = ":";
    public static final String VALUE_SEPARATOR = ",";

    private final String operator;

    FilterOperator(String operator) {
        this.operator = operator;
    }

    public String getParamOperator() {
        return DELIMITER + operator + DELIMITER;
    }

}
