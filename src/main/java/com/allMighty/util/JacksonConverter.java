package com.allMighty.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.Converter;
import org.jooq.JSONB;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * A jOOQ converter for converting from JSONB to a desired class using Jackson.
 *
 * jOOQ also supports Jackson out of the box, but the {@link ObjectMapper} it uses is not customizable, and it doesn't
 * handle collections unless the complete query is mapped by it.
 *
 * @since 14-Jan-2021
 */
public class JacksonConverter<T> implements Converter<JSONB, T> {

  private static final Logger logger = LogManager.getLogger(JacksonConverter.class);
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    SimpleModule module = new SimpleModule();
    objectMapper.registerModule(module);
  }

  private final Class<T> toType;
  private CollectionType collectionType;

  public JacksonConverter(Class<T> toType) {
    this.toType = toType;
  }

  @SuppressWarnings("unchecked")
  public JacksonConverter(CollectionType collectionType) {
    toType = (Class<T>) collectionType.getRawClass();
    this.collectionType = collectionType;
  }

  @Override
  public T from(JSONB json) {
    if (json == null) {
      return null;
    }
    String jsonString = json.data();
    try {
      return collectionType == null ? objectMapper.readValue(jsonString, toType)
          : objectMapper.readValue(jsonString, collectionType);
    } catch (JsonProcessingException e) {
      Object type = collectionType == null ? toType : collectionType;
      logger.error("Error converting from [" + jsonString + "] to " + type, e);
      return null;
    }
  }

  @Override
  public JSONB to(T userObject) {
    if (userObject == null) {
      return null;
    }
    try {
      return JSONB.valueOf(objectMapper.writeValueAsString(userObject));
    } catch (JsonProcessingException e) {
      logger.error("Error serializing object of type " + userObject.getClass(), e);
      return null;
    }
  }

  @Override
  @NonNull
  public Class<JSONB> fromType() {
    return JSONB.class;
  }

  @Override
  @NonNull
  public Class<T> toType() {
    return toType;
  }

  public static TypeFactory getObjectMapperTypeFactory() {
    return objectMapper.getTypeFactory();
  }
}
