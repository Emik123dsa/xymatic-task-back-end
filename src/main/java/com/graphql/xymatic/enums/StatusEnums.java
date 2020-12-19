package com.graphql.xymatic.enums;

import java.util.stream.Stream;


public enum StatusEnums {
  active("active"),
  closed("closed"),
  pending("pending"),
  deleted("deleted");

  private String status;

  StatusEnums(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public static StatusEnums of(String priority) {
    return Stream.of(StatusEnums.values())
      .filter(p -> p.getStatus() == priority) 
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}
