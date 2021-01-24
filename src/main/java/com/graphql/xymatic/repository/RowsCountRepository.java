package com.graphql.xymatic.repository;

import com.graphql.xymatic.model.RowsCountModel;
import java.util.List;

public interface RowsCountRepository {
  public List<RowsCountModel> countAllRows();
}
