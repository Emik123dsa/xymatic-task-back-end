package com.graphql.xymatic.model;

import com.graphql.xymatic.enums.PeriodEnums;
import java.io.Serializable;
import java.sql.Date;

public class ChartModel implements Serializable {

  private static final long serialVersionUID = 1L;

  private PeriodEnums periodEnums;

  private final Date timestamp;

  private final Long delta;

  private final Long deltaTotal;

  public ChartModel(Date timestamp, Long delta, Long deltaTotal) {
    this.timestamp = timestamp;
    this.delta = delta;
    this.deltaTotal = deltaTotal;
  }

  public void setPeriodEnums(PeriodEnums periodEnums) {
    this.periodEnums = periodEnums;
  }

  public PeriodEnums getPeriodEnums() {
    return periodEnums;
  }

  public Date getTimestamp() {
    return this.timestamp;
  }

  public Long getDelta() {
    return this.delta;
  }

  public Long getDeltaTotal() {
    return this.deltaTotal;
  }
}
