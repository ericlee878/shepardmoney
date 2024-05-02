package com.shepherdmoney.interviewproject.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TreeMapConverter implements AttributeConverter<TreeMap<String, Integer>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TreeMap<String, Integer> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert TreeMap to String", e);
        }
    }

    @Override
    public TreeMap<String, Integer> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<TreeMap<String, Integer>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert String to TreeMap", e);
        }
    }
}
