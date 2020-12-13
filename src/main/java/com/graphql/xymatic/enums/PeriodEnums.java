package com.graphql.xymatic.enums;

public enum PeriodEnums {
  TODAY("1 hour"),
  YESTERDAY("1 hour - 1 day"),
  DAY("1 day"),
  MONTH("1 month"),
  YEAR("1 year"),
  ALL("1 year");

  private String period;

  PeriodEnums(String period) {
    this.period = period;
  }

  public String getPeriod() {
    return period;
  }
}
