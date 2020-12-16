package com.graphql.xymatic.enums;

public enum TableEnums {
  USERS("xt_users"),
  POSTS("xt_posts"),
  IMPRESSIONS("xt_impressions"),
  PLAYS("xt_plays"),
  TRIGGERS("xt_triggers");

  private String table;

  TableEnums(String table) {
    this.table = table;
  }

  public String getTable() {
    return table;
  }
}
