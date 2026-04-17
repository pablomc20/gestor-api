package com.gestor.dominator.constants;

public enum ImageType {
  INITIAL("INITIAL"),
  PROGRESS("PROGRESS"),
  FINAL("FINAL"),
  PORTAFOLIO("PORTAFOLIO"),
  GARANTY("GARANTY");

  private final String value;

  ImageType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
