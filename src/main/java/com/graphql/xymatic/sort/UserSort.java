package com.graphql.xymatic.sort;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.graphql.xymatic.enums.DateSortEnums;
import com.graphql.xymatic.enums.DirectionSortEnums;
import org.springframework.data.domain.Sort;

public class UserSort {

  private final DateSortEnums date;
  private final DirectionSortEnums direction;

  @JsonCreator
  public UserSort(
    @JsonProperty("date") DateSortEnums date,
    @JsonProperty("direction") DirectionSortEnums direction
  ) {
    this.date = date;
    this.direction = direction;
  }

  public DateSortEnums getDate() {
    return date;
  }

  public DirectionSortEnums getDirection() {
    return direction;
  }

  public Sort getSort() {
    return Sort.by(getDirection().getDirectionSort(), getDate().getDateSort());
  }
}
