package com.studeal.team.global.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false) // autoApply를 true로 하면 전체 프로젝트에 자동 적용됨
public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {

  @Override
  public String convertToDatabaseColumn(Boolean attribute) {
    if (attribute == null) {
      return "N";
    }
    return attribute ? "Y" : "N";
  }

  @Override
  public Boolean convertToEntityAttribute(String dbData) {
    return "Y".equalsIgnoreCase(dbData);
  }
}
