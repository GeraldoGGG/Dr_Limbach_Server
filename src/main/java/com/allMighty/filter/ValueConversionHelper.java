package com.allMighty.filter;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Log4j2
public class ValueConversionHelper {
    private final String COMMA = ",";

    public Boolean toBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        return null;
    }

    public Boolean toBooleanInverted(Object value) {
        Boolean result = toBoolean(value);
        if (result != null) {
            return !result;
        }

        return result;
    }

    public Integer toInt(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            String formattedValue = formatStringValue((String) value);
            if (NumberUtils.isCreatable(formattedValue)) {
                return Integer.parseInt(formattedValue);
            }
        }
        return null;
    }

    public Double toDouble(Object value) {
        if (value instanceof BigInteger) {
            return ((BigInteger) value).doubleValue();
        } else if (value instanceof Float) {
            return ((Float) value).doubleValue();
        } else if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof String) {
            String formattedValue = formatStringValue((String) value);
            if (NumberUtils.isCreatable(formattedValue)) {
                return Double.parseDouble(formattedValue);
            }
        }

        return null;
    }

    private String formatStringValue(String value) {
        if (value == null) {
            return EMPTY;
        }
        if (value.contains(COMMA)) {
            return value.replaceAll(COMMA, EMPTY);
        }
        return value;
    }

    public List<Long> toListOfNumbers(String commaSeparatedIds) {
        if (StringUtils.isBlank(commaSeparatedIds)) {
            return Collections.emptyList();
        }
        return Arrays.stream(commaSeparatedIds.split(",")).map(candidate -> {
            try {
                return toLong(candidate);
            } catch (Exception e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Long toLong(Object value) {
        if (value instanceof BigInteger) {
            return ((BigInteger) value).longValue();
        } else if (value instanceof String && NumberUtils.isCreatable((String) value)) {
            return Long.parseLong((String) value);
        } else if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Integer) {
            return ((Integer) value).longValue();
        } else if (value instanceof Double) {
            return ((Double) value).longValue();
        }
        return null;
    }

    public LocalDateTime toLocalDateTime(OffsetDateTime value, ZoneId zoneId) {
        if (zoneId == null || value == null) {
            return null;
        }
        LocalDateTime localDateTime = value.atZoneSameInstant(zoneId).toLocalDateTime();
        return localDateTime;
    }

    public LocalDateTime toLocalDateTime(Object dateValue) {
        if (dateValue instanceof Timestamp) {
            return ((Timestamp) dateValue).toLocalDateTime();
        } else if (dateValue instanceof LocalDateTime) {
            return (LocalDateTime) dateValue;
        } else if (dateValue instanceof String) {
            return LocalDateTime.parse((String) dateValue, DateTimeFormatter.ISO_DATE_TIME);
        } else if (dateValue instanceof OffsetDateTime) {
            return ((OffsetDateTime) dateValue).toLocalDateTime();
        }
        return null;
    }

    public OffsetDateTime toOffsetDateTime(Object dateValue, ZoneId zoneId) {
        OffsetDateTime result = null;
        if (dateValue instanceof OffsetDateTime) {
            result = (OffsetDateTime) dateValue;
        }

        if (result == null) {
            LocalDateTime localDateTime = toLocalDateTime(dateValue);
            if (localDateTime != null) {
                result = localDateTime.atZone(zoneId != null ? zoneId : ZoneOffset.systemDefault()).toOffsetDateTime();
            }
        }
        return result;
    }

    public OffsetDateTime toOffsetDateTime(Object dateValue) {
        return toOffsetDateTime(dateValue, null);
    }

    public <T> T safeGet(JsonNode payload, Function<JsonNode, T> getter) {
        if (payload == null) {
            return null;
        }
        return getter.apply(payload);
    }

    public List<Long> toList(JsonNode entityIdArray) {
        if (entityIdArray == null || !entityIdArray.isArray()) {
            return null;
        }

        List<Long> entityIds = new ArrayList<>();
        for (JsonNode entityId : entityIdArray) {
            if (!entityId.isNull()) {
                entityIds.add(entityId.asLong());
            }
        }

        if (entityIds.isEmpty()) {
            return null;
        }

        return entityIds;
    }


}
