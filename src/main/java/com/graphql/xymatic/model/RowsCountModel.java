package com.graphql.xymatic.model;

import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class RowsCountModel implements Serializable {

  private static final long serialVersionUID =
    SpringSecurityCoreVersion.SERIAL_VERSION_UID;

  private String type;
  private Integer count;

  public RowsCountModel() {}

  public String getType() {
    return type;
  }

  public Integer getCount() {
    return count;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setCount(Integer count) {
    this.count = count;
  }
}
