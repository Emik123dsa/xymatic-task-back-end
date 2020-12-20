package com.graphql.xymatic.enums;

public enum DateSortEnums {
  CREATED_AT("createdAt"),
  UPDATED_AT("updatedAt");

  private String dateSort;

  DateSortEnums(String dateSort) {
    this.dateSort = dateSort;
  }

  public String getDateSort() {
    return dateSort;
  }
}
