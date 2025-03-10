package com.allMighty.global_operation.filter;

import com.allMighty.global_operation.converter.ValueConversionHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.time.LocalDateTime;
import java.util.*;

@Log4j2
public class JooqConditionBuilder {
  private static final ValueConversionHelper converter = new ValueConversionHelper();
  private static final String VALUE_SEPARATOR = ",";
  private static final String FILTER_VALUE_ALL = "all";

  // TODO later this can be moved to its own class that can take more responsibilities and generate
  // more than conditions
  public static <T extends FilterableField> List<Condition> buildConditions(
      List<String> filters, FilterParser<T> filterParser) {
    List<FilterDescriptor<T, ?>> filterDescriptors = filterParser.parseFilters(filters);
    return buildConditions(filterDescriptors);
  }

  // more than conditions
  public static <T extends FilterableField> List<Condition> buildConditions(
      String filter, FilterParser<T> filterParser) {
    List<FilterDescriptor<T, ?>> filterDescriptors = filterParser.parseFilters(Collections.singletonList(filter));
    return buildConditions(filterDescriptors);
  }

  public static <T extends FilterableField> List<Condition> buildConditions(
      List<FilterDescriptor<T, ?>> filterDescriptors) {
    List<Condition> conditions = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(filterDescriptors)) {
      for (FilterDescriptor<T, ?> filter : filterDescriptors) {
        T field = filter.getField();
        Condition condition =
            buildCondition(field.getJooqField(), filter.getOperator(), filter.getValue());
        conditions.add(condition);
      }
    } else {
      conditions.add(DSL.noCondition());
    }
    return conditions;
  }

  public static Condition buildCondition(Field field, FilterOperator operator, Object value) {
    if (field == null) {
      return DSL.noCondition();
    }

    if (field.getType().equals(LocalDateTime.class) && value instanceof String) {
      value = converter.toOffsetDateTime(value);
    }

    switch (operator) {
      case EQ:
        if (field.getType().isAssignableFrom(String.class)) {
          return field.equalIgnoreCase(Objects.toString(value, ""));
        } else if (field.getType().isAssignableFrom(Boolean.class)) {
          if (value != null && !value.toString().toLowerCase().equals(FILTER_VALUE_ALL)) {
            Boolean booleanValue = converter.toBoolean(value);
            if (Boolean.TRUE.equals(booleanValue)) {
              return field.eq(true);
            } else if (booleanValue != null) {
              return field.eq(booleanValue).or(field.isNull());
            }
          }
          break;
        } else {
          return field.eq(value);
        }
      case GT:
        return field.gt(value);
      case GTE:
        return field.greaterOrEqual(value);
      case LIKE:
      case START_WITH:
        String filterValue = (String) value;

        if (StringUtils.isNotBlank(filterValue)) {
          filterValue =
              FilterOperator.START_WITH == operator ? filterValue + "%" : "%" + filterValue + "%";
          return DSL.lower(field).like(filterValue.toLowerCase());
        }

        return DSL.noCondition();
      case LT:
        return field.lt(value);
      case LTE:
        return field.lessOrEqual(value);
      case NOT_EQ:
        if (value instanceof String) {
          if (StringUtils.isNotBlank((String) value)) {
            return field.isNull().or(field.notEqualIgnoreCase(Objects.toString(value, "")));
          } else {
            return field.notEqualIgnoreCase(Objects.toString(value, ""));
          }
        } else {
          return field.ne(value);
        }

      case EQ_CASE_SENSITIVE:
        return field.eq(value);
      case EQ_IGNORE_CASE:
        return field.equalIgnoreCase(Objects.toString(value, ""));
      case IN:
      case IN_WITH_BLANK:
        Condition inCondition = DSL.noCondition();
        if (value instanceof List) {
          inCondition = field.in(value);
        } else if (value instanceof String) {
          // noinspection DataFlowIssue
          filterValue = (String) value;

          if (StringUtils.isNotBlank(filterValue)) {
            String[] arr = filterValue.split(VALUE_SEPARATOR);
            Class<?> clazz = field.getType();

            if (clazz.equals(Long.class)) {
              List<Long> valueList = new ArrayList<>();

              for (String s : arr) {
                valueList.add(Long.valueOf(s));
              }

              // noinspection unchecked
              inCondition = field.in(valueList);
            } else if (clazz.equals(String.class)) {
              // noinspection unchecked
              inCondition = field.in(new ArrayList<>(Arrays.asList(arr)));
            } else if (clazz.equals(Integer.class)) {
              List<Integer> valueList = new ArrayList<>();

              for (String s : arr) {
                valueList.add(Integer.valueOf(s));
              }

              // noinspection unchecked
              inCondition = field.in(valueList);
            } else {
              log.warn("Unsupported type {} for 'in' operator", clazz.getName());
            }
          } else {
            // noinspection unchecked
            inCondition = field.in(Collections.emptyList());
          }
        }

        if (operator == FilterOperator.IN_WITH_BLANK) {
          inCondition = inCondition.or(field.isNull());
        }

        return inCondition;
      case IS_BLANK:
        @SuppressWarnings("DataFlowIssue")
        boolean blankValue = BooleanUtils.toBoolean((String) value);
        Condition blankFieldCondition = blankValue ? field.isNull() : field.isNotNull();
        // noinspection unchecked
        if (field.getType().isAssignableFrom(String.class)) {
          // noinspection unchecked
          Field<Integer> trimmedFieldLength = DSL.length(DSL.trim(field));

          blankFieldCondition =
              blankFieldCondition.or(
                  blankValue ? trimmedFieldLength.eq(0) : trimmedFieldLength.gt(0));
        }

        return blankFieldCondition;

      default:
        break;
    }

    return DSL.noCondition();
  }
}
