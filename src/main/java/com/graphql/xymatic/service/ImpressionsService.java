package com.graphql.xymatic.service;

import com.graphql.xymatic.model.ImpressionsModel;
import com.graphql.xymatic.repository.ImpressionsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImpressionsService {

  private final ImpressionsRepository impressionsRepository;

  @Autowired
  public ImpressionsService(ImpressionsRepository impressionsRepository) {
    this.impressionsRepository = impressionsRepository;
  }

  public List<ImpressionsModel> findAll() {
    return impressionsRepository.findAll();
  }

  public Long count() {
    return impressionsRepository.count();
  }
}
