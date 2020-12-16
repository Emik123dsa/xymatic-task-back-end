package com.graphql.xymatic.enums;

public enum StatusEnums {
  ACTIVE("active"),
  CLOSED("closed"),
  PENDING("pending"),
  DELETED("deleted");

  private String status;

  StatusEnums(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
