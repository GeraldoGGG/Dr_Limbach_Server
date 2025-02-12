package com.allMighty.config.restfull;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collection;
import java.util.Collections;
@Configuration
public class RESTConfigurer implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.removeConvertible(String.class, Collection.class);
        registry.addConverter(String.class, Collection.class, noCommaSplitStringToCollectionConverter());
    }

    public Converter<String, Collection> noCommaSplitStringToCollectionConverter() {
        return Collections::singletonList;
    }
}
