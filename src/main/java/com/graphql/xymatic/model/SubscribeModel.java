package com.graphql.xymatic.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SubscribeModel implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Long delta;
  private final LocalDateTime timestamp;

  public SubscribeModel(Long delta, LocalDateTime timestamp) {
    this.timestamp = timestamp;
    this.delta = delta;
  }

  public Long getCount() {
    return this.delta;
  }

  public LocalDateTime getLocalDateTime() {
    return this.timestamp;
  }
}
