package com.graphql.xymatic.sort;

import org.springframework.data.domain.*;

public enum DirectionSort {
  ASC(Sort.Direction.ASC),
  DESC(Sort.Direction.DESC);

  private final Sort.Direction direction;

  DirectionSort(Sort.Direction direction) {
    this.direction = direction;
  }

  public Sort.Direction getDirection() {
    return direction;
  }
}
