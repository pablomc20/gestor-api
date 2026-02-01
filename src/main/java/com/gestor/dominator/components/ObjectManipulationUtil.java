package com.gestor.dominator.components;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestor.dominator.exceptions.custom.DataValidationException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ObjectManipulationUtil {

  private final ObjectMapper objectMapper;

  public <T> T objectMapperToString(String jsonResult, Class<T> clazz) {
    try {
      T result = objectMapper.readValue(jsonResult, clazz);
      return result;
    } catch (JsonProcessingException e) {
      throw new DataValidationException("Error al procesar el JSON", e);
    }
  }

  public <T> T fromJson(String json, Class<T> clazz) {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      throw new DataValidationException("Error al procesar el JSON", e);
    }
  }

  public <T> T fromJson(String json, TypeReference<T> typeReference) {
    try {
      return objectMapper.readValue(json, typeReference);
    } catch (JsonProcessingException e) {
      throw new DataValidationException("Error al procesar el JSON", e);
    }
  }

}
