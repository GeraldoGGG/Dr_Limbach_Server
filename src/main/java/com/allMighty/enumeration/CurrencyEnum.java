package com.allMighty.enumeration;

public enum CurrencyEnum {
    USD("US Dollar", "$"),
    EUR("Euro", "€"),
    GBP("British Pound", "£"),
    JPY("Japanese Yen", "¥"),
    AUD("Australian Dollar", "A$"),
    CAD("Canadian Dollar", "C$"),
    CHF("Swiss Franc", "CHF"),
    CNY("Chinese Yuan", "¥"),
    INR("Indian Rupee", "₹");

    private final String fullName;
    private final String symbol;

    CurrencyEnum(String fullName, String symbol) {
        this.fullName = fullName;
        this.symbol = symbol;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSymbol() {
        return symbol;
    }
}
