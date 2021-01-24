package com.graphql.xymatic.service;

import com.graphql.xymatic.model.RowsCountModel;
import com.graphql.xymatic.repository.RowsCountRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RowsCountService {

  private final RowsCountRepository rowsCountRepository;

  @Autowired
  public RowsCountService(RowsCountRepository rowsCountRepository) {
    this.rowsCountRepository = rowsCountRepository;
  }

  public List<RowsCountModel> countAllRows() {
    return rowsCountRepository.countAllRows();
  }
}
