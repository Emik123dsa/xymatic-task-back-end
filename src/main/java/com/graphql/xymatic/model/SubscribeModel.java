package com.graphql.xymatic.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SubscribeModel implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Long count;
  private final LocalDateTime timestamp;

  public SubscribeModel(Long count, LocalDateTime timestamp) {
    this.timestamp = timestamp;
    this.count = count;
  }

  public Long getCount() {
    return this.count;
  }

  public LocalDateTime getLocalDateTime() {
    return this.timestamp;
  }
}
