package com.graphql.xymatic.enums;

public enum AttitudeEnums {
  LIKE("LIKE"),
  DISLIKE("DISLIKE");

  private String attitude;

  AttitudeEnums(String attitude) {
    this.attitude = attitude;
  }

  public String getAttitude() {
    return attitude;
  }
}
