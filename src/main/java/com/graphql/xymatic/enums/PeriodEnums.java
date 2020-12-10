package com.graphql.xymatic.enums;

public enum PeriodEnums {
  TODAY("current_timestamp"),
  YESTERDAY("1 DAY AGO"),
  DAY("days"),
  MONTH("month"),
  YEAR("year"),
  ALL("ALL TIME");

  private String period;

  PeriodEnums(String period) {
    this.period = period;
  }

  public String getPeriod() {
    return period;
  }
}
