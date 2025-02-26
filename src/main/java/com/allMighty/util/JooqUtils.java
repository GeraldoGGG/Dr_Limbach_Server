package com.allMighty.util;

import static org.jooq.impl.DSL.coalesce;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.Condition;
import org.jooq.Converter;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.JSONEntry;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.Settings;
import org.jooq.impl.AbstractConverter;
import org.jooq.impl.DSL;
import org.jooq.impl.EnumConverter;
import org.jooq.impl.IdentityConverter;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;


public class JooqUtils {
  private static final Logger LOGGER = LogManager.getLogger(JooqUtils.class);

  private static final Map<Class<? extends Enum<?>>, DataType<?>> stringEnumDataTypes = new HashMap<>();
  private static final Map<Class<? extends Enum<?>>, DataType<?>> ordinalEnumDataTypes = new HashMap<>();



  public static final Converter<JSON, JsonNode> JSON_NODE_CONVERTER = new AbstractConverter<JSON, JsonNode>(JSON.class,
      JsonNode.class) {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public JsonNode from(JSON databaseObject) {
      if (databaseObject == null) {
        return null;
      }

      try {
        return mapper.readTree(databaseObject.data());
      } catch (JsonProcessingException e) {
        LOGGER.error(e.getMessage(), e);
      }
      return null;
    }

    @Override
    public JSON to(JsonNode node) {
      if (node == null) {
        return null;
      }

      try {
        return JSON.valueOf(mapper.writeValueAsString(node));
      } catch (JsonProcessingException e) {
        LOGGER.error(e.getMessage(), e);
        return null;
      }
    }
  };


  public static final DataType<JsonNode> JSON_NODE_DATA_TYPE = SQLDataType.JSON
      .asConvertedDataType(JSON_NODE_CONVERTER);

  public static DSLContext createContext(String schema) {
    Settings dslSettings = new Settings()
        .withRenderMapping(new RenderMapping().withSchemata(new MappedSchema().withInput("").withOutput(schema)));
    return DSL.using(SQLDialect.POSTGRES, dslSettings);
  }

  public static Field<Object> jsonFieldAgg(QueryPart... fields) {
    String filterPlaceholder = IntStream.range(0, fields.length).mapToObj(i -> "{" + i + "} is not null")
        .collect(Collectors.joining(" or "));
    return jsonFieldAgg(filterPlaceholder, fields);
  }

  /**
   * Allows the caller to specify which fields should appear in the filter.
   */
  public static Field<Object> jsonFieldAgg(QueryPart[] fields, QueryPart[] filters) {
    QueryPart[] params = new QueryPart[fields.length + filters.length];
    StringBuilder rowPlaceholder = new StringBuilder();
    for (int i = 0; i < fields.length; i++) {
      params[i] = fields[i];
      if (rowPlaceholder.length() > 0) {
        rowPlaceholder.append(", ");
      }
      rowPlaceholder.append("({").append(i).append("})");
    }
    StringBuilder filterSql = new StringBuilder();
    for (int i = 0; i < filters.length; i++) {
      params[fields.length + i] = filters[i];
      if (filterSql.length() > 0) {
        filterSql.append(" or ");
      }
      filterSql.append("{").append(fields.length + i).append("} is not null");
    }
    return DSL.field(new StringBuilder("json_agg(distinct row(").append(rowPlaceholder).append(")) filter(where ")
        .append(filterSql).append(")").toString(), params);
  }

  public static Field<JsonNode> jsonNodeFieldAgg(QueryPart... fields) {
    return DSL.field("?", JSON_NODE_DATA_TYPE, jsonFieldAgg(fields));
  }

  public static Field<JsonNode> jsonNodeFieldAgg(QueryPart[] fields, QueryPart[] filters) {
    return DSL.field("?", JSON_NODE_DATA_TYPE, jsonFieldAgg(fields, filters));
  }

  public static Field<JsonNode> distinctJsonNodeFieldAgg(QueryPart field) {
    return DSL.field("json_agg(distinct {0})", JSON_NODE_DATA_TYPE, field);
  }

  public static Field<Object> jsonFieldAgg(String filter, QueryPart... fields) {
    String rowPlaceholder = IntStream.range(0, fields.length).mapToObj(i -> "({" + i + "})")
        .collect(Collectors.joining(", "));
    if (StringUtils.isNotBlank(filter)) {
      return DSL.field(String.format("json_agg(distinct row(%s)) filter(where %s)", rowPlaceholder, filter), fields);
    } else {
      return DSL.field(String.format("json_agg(distinct row(%s))", rowPlaceholder), fields);
    }
  }

  public static Field<JsonNode> jsonNodeFieldAgg(String filter, QueryPart... fields) {
    return DSL.field("?", JSON_NODE_DATA_TYPE, jsonFieldAgg(filter, fields));
  }

  public static Field<JsonNode> jsonNodeField(QueryPart field) {
    return DSL.field("?", JSON_NODE_DATA_TYPE, field);
  }



  /**
   * Aggregates a set of fields into a JSON object, and aggregates those objects into an array. The produced JSON is
   * converted into a {@link java.util.List} of the given type using Jackson's ObjectMapper.
   *
   * As it's not possible to use e.g. PERSON.ID.as("alias") to change the key of a value, it should instead be done with
   * a JSONEntry, using DSL.jsonEntry("alias", PERSON.ID).
   *
   * For example, jsonAgg(PersonDTO.class, PERSON.ID, PERSON.NAME, DSL.jsonEntry("email", PERSON.USERID)) could result
   * in the following JSON [{"id": 1, "name": "Bob", "email": "bob@example.com"},{"id": 5, "name": "John", "email":
   * "john@doe.com"}] which would be converted to a List<PersonDTO> in the result.
   *
   * Note that it's not possible to use converters on the fields passed to this method. The ObjectMapper used knows how
   * to convert TranslationUnitDTO, however.
   *
   * @param  type
   *                 The class that each object should be converted to
   * @param  parts
   *                 A list of either {@link Field} or {@link JSONEntry}
   * @param  <T>
   *                 The type that each object should be converted to
   * @return       A field of type List<T>
   */
  public static <T> Field<List<T>> jsonArrayAgg(Class<T> type, QueryPart... parts) {
    return jsonArrayAgg(JacksonConverter.getObjectMapperTypeFactory().constructCollectionType(List.class, type), parts);
  }

  public static <T> Field<Set<T>> jsonArrayAggSet(Class<T> type, QueryPart... parts) {
    return jsonArrayAgg(JacksonConverter.getObjectMapperTypeFactory().constructCollectionType(Set.class, type), parts);
  }

  private static <T> Field<T> jsonArrayAgg(CollectionType type, QueryPart... parts) {
    JSONEntry<?>[] jsonEntries = new JSONEntry[parts.length];
    for (int i = 0; i < parts.length; i++) {
      QueryPart part = parts[i];
      if (part instanceof JSONEntry) {
        jsonEntries[i] = (JSONEntry<?>) part;
      } else if (part instanceof Field) {
        Field<?> field = (Field<?>) part;
        if (!(field.getConverter() instanceof IdentityConverter)) {
          throw new IllegalArgumentException(
              String.format("Field %s has converter of type %s. Custom converters will not work inside JSON functions",
                  field.getName(), field.getConverter()));
        }
        jsonEntries[i] = DSL.jsonEntry(field);
      } else {
        throw new IllegalArgumentException("Only QueryParts of type Field or JSONEntry are supported");
      }
    }
    Field<JSONB> field = DSL.jsonbArrayAgg(DSL.field("distinct ?", DSL.jsonbObject(jsonEntries)));
    DataType<T> dataType = SQLDataType.JSONB.asConvertedDataType(new JacksonConverter<>(type));
    return DSL.field("?", dataType, field);
  }

  /**
   * When selecting a JSONB array in a query, this converts that field to a List of the given type.
   *
   * Example:
   *
   * context.SELECT( JooqUtils.jsonbArrayToList(DSL.jsonbArrayAgg(PERSON.FIRSTNAME), String.class) ).FROM(PERSON);
   *
   * What you end up with is a {@link Field<List<String>>} instead of a {@link Field<JSONB>}
   */
  public static <T> Field<List<T>> jsonbArrayToList(Field<JSONB> field, Class<T> elementType) {
    CollectionType collectionType = JacksonConverter.getObjectMapperTypeFactory().constructCollectionType(List.class,
        elementType);
    return DSL.field("?", SQLDataType.JSONB.asConvertedDataType(new JacksonConverter<>(collectionType)), field);
  }

  /**
   * Converts the given field of type string to a field of the given enum type.
   *
   * The converted data type is cached and re-used when toEnum is called with the same enum type. Since this will be
   * used for a limited set of enums and a data type does not use much memory, having a static map of this kind is
   * acceptable.
   *
   * @param field
   *                    The field to convert
   * @param enumClass
   *                    The enum type to convert to
   */
  public static <F extends Field<String>, T extends Enum<T>> Field<T> toEnum(F field, Class<T> enumClass) {
    DataType<T> dataType = (DataType<T>) stringEnumDataTypes.computeIfAbsent(enumClass, e -> {
      EnumConverter<String, T> enumConverter = new EnumConverter<>(String.class, enumClass);
      return SQLDataType.VARCHAR.asConvertedDataType(enumConverter);
    });
    return DSL.field("?", dataType, field);
  }

  /**
   * Same as above, but for enums stored as ordinals.
   */
  public static <F extends Field<? extends Number>, T extends Enum<T>> Field<T> toEnumOrdinal(F field,
      Class<T> enumClass) {
    DataType<T> dataType = (DataType<T>) ordinalEnumDataTypes.computeIfAbsent(enumClass, e -> {
      EnumConverter<Integer, T> enumConverter = new EnumConverter<>(Integer.class, enumClass);
      return SQLDataType.INTEGER.asConvertedDataType(enumConverter);
    });
    return DSL.field("?", dataType, field);
  }




  /**
   * Returns the value of the given key in the given JSONB field.
   */
  public static Field<Object> jsonbValue(Field<JSONB> field, Object key) {
    return DSL.field(String.format("{0} ->> '%s'", key), field);
  }

  /**
   * @param  jsonbFieldName
   *                          Name of the jsonb field
   * @return                Table which is the result of applying jsonb_each_text postgres function
   */
  public static Table<Record> jsonbEachText(String jsonbFieldName) {
    return DSL.table("jsonb_each_text({0})", fieldForName(jsonbFieldName));
  }

  /**
   * @param  substring
   *                     Text to search for
   * @param  string
   *                     Text to search within
   * @return           Index of the substring within the string (0 if not found)
   */
  public static Field<Integer> indexOf(Field<String> substring, Field<String> string) {
    return DSL.field("position({0} in {1})", SQLDataType.INTEGER, substring, string);
  }

  /**
   * @param  name
   *                Name of the field to be returned
   * @return      Field for the given name
   */
  public static Field<Object> fieldForName(String name) {
    return fieldForName(name, Object.class);
  }

  /**
   * @param  name
   *                Name of the field to be returned
   * @param  type
   *                Type of the field to be returned
   * @return      (typed) Field for the given name
   */
  public static <T> Field<T> fieldForName(String name, Class<T> type) {
    return DSL.field(DSL.name(name), type);
  }



  /**
   * @param  stringField
   *                       Field to concatenate
   * @param  separator
   *                       Concatenation separator
   * @return             Concatenated field values
   */
  public static Field<String> stringAgg(Field<String> stringField, String separator) {
    return DSL.field("STRING_AGG({0}, {1})", SQLDataType.VARCHAR, stringField, separator);
  }

  public static <T extends TableImpl<?>> T getNamedField(T table, String prefix, Long id) {
    return (T) table.as(String.format("%s_%d", prefix, id));
  }

  public static Field<Object> interval(Integer years, Integer months, Integer days) {
    return interval(years, months, null, days, null, null, null);
  }

  public static Field<Object> interval(Integer years, Integer months, Integer weeks, Integer days, Integer hours,
      Integer minutes, Integer seconds) {
    return DSL.field(MessageFormat.format(
        "interval ''{0} years {1} months {2} weeks {3} days {4} hours {5} minutes {6} seconds''",
        ObjectUtils.defaultIfNull(years, 0), ObjectUtils.defaultIfNull(months, 0), ObjectUtils.defaultIfNull(weeks, 0),
        ObjectUtils.defaultIfNull(days, 0), ObjectUtils.defaultIfNull(hours, 0), ObjectUtils.defaultIfNull(minutes, 0),
        ObjectUtils.defaultIfNull(seconds, 0)));
  }
}
