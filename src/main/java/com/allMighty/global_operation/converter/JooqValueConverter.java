package com.allMighty.global_operation.converter;

import com.fasterxml.jackson.databind.JsonNode;
import org.jooq.types.Interval;
import org.jooq.types.YearToSecond;

import java.time.Duration;

public class JooqValueConverter {
  public static final ValueConversionHelper conversionHelper = new ValueConversionHelper();

  public static Long getLong(JsonNode value) {
    if (value == null || value.isNull()) {
      return null;
    }
    return value.asLong();
  }

  public static Boolean getBoolean(JsonNode value) {
    if (value == null || value.isNull()) {
      return null;
    }
    return value.asBoolean();
  }

  public static Integer getInt(JsonNode value) {
    if (value == null || value.isNull()) {
      return null;
    }
    return value.asInt();

  }

  public static String getString(JsonNode value) {
    if (value == null || value.isNull()) {
      return null;
    }
    return value.asText();
  }

  public static <T extends Enum<T>> T getEnum(Class<T> enumType, Object value) {
    if (enumType == null || value == null) {
      return null;
    }
    if (value instanceof String) {
      return getEnum(enumType, (String) value);
    }

    if (value instanceof Integer) {
      return getEnum(enumType, (Integer) value);
    }
    return null;
  }

  public static <T extends Enum<T>> T getEnum(Class<T> enumType, JsonNode nodeValue) {
    if (enumType == null || nodeValue == null || nodeValue.isNull()) {
      return null;
    }
    if (nodeValue.isTextual()) {
      return getEnum(enumType, nodeValue.asText());
    }

    if (nodeValue.isInt()) {
      return getEnum(enumType, nodeValue.asInt());
    }
    return null;
  }

  public static <T extends Enum<T>> T getEnum(Class<T> enumType, Integer value) {
    if (enumType == null || value == null) {
      return null;
    }

    for (T enumConstant : enumType.getEnumConstants()) {
      if (Integer.valueOf(enumConstant.ordinal()) == value) {
        return enumConstant;
      }
    }

    return null;
  }

  public static <T extends Enum<T>> T getEnum(Class<T> enumType, String value) {
    if (enumType == null || value == null) {
      return null;
    }

    for (T enumConstant : enumType.getEnumConstants()) {
      if (enumConstant.name().equalsIgnoreCase(value)) {
        return enumConstant;
      }
    }
    return null;
  }


  //TODO test this
  public static Duration toDuration(YearToSecond yearToSecond) {
    if (yearToSecond == null) {
      return null; // Return null if the input is null
    }

    // Convert the YEAR TO SECOND interval to total seconds
    long totalSeconds = yearToSecond.getYears() * 365 * 24 * 60 * 60  // Years to seconds
            + yearToSecond.getMonths() * 30 * 24 * 60 * 60 // Months to seconds
            + yearToSecond.getDays() * 24 * 60 * 60      // Days to seconds
            + yearToSecond.getHours() * 60 * 60           // Hours to seconds
            + yearToSecond.getMinutes() * 60              // Minutes to seconds
            + yearToSecond.getSeconds();                  // Seconds

    return Duration.ofSeconds(totalSeconds);
  }

}