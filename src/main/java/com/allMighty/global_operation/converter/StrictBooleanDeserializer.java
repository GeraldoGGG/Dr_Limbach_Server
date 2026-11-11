package com.allMighty.global_operation.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class StrictBooleanDeserializer extends JsonDeserializer<Boolean> {

  @Override
  public Boolean deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    JsonToken token = parser.currentToken();
    if (token == JsonToken.VALUE_TRUE) {
      return Boolean.TRUE;
    }
    if (token == JsonToken.VALUE_FALSE) {
      return Boolean.FALSE;
    }
    return (Boolean)
        context.handleUnexpectedToken(
            Boolean.class, token, parser, "Value must be a JSON boolean (true or false)");
  }
}
