package com.allMighty.util;

import static org.jooq.impl.DSL.inline;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.impl.DSL;

public class SearchUtil {

  public static Field<String> normalize(Field<String> originalField) {
    return DSL.replace(DSL.replace(DSL.lower(originalField), "ë", "e"), "ç", "c");
  }

  public static @NotNull Param<String> normalize(String searchWord) {

    if (StringUtils.isEmpty(searchWord)) {
      // matches nothing
      return inline("!@#$%^&*()");
    }
    String normalized = searchWord.toLowerCase().replace("ë", "e").replace("ç", "c");

    return inline("%" + normalized + "%");
  }
}
