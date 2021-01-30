package com.graphql.xymatic.enums;

import org.springframework.data.domain.*;

public enum DirectionSortEnums {
  ASC(Sort.Direction.ASC),
  DESC(Sort.Direction.DESC);

  private final Sort.Direction direction;

  DirectionSortEnums(Sort.Direction direction) {
    this.direction = direction;
  }

  public Sort.Direction getDirectionSort() {
    return direction;
  }
}
