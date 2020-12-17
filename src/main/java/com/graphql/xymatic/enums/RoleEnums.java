package com.graphql.xymatic.enums;

public enum RoleEnums {
  USER("USER"),
  ADMIN("ADMIN");

  private String role;

  RoleEnums(String role) {
    this.role = role;
  }

  public String getRole() {
    return this.role;
  }
}
