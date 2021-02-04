package com.graphql.xymatic.service;

import com.graphql.xymatic.model.TriggerModel;
import com.graphql.xymatic.repository.TriggerRepository;
import com.graphql.xymatic.sort.TriggerSort;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TriggerService {

  private final TriggerRepository triggerRepository;

  @Autowired
  public TriggerService(TriggerRepository triggerRepository) {
    this.triggerRepository = triggerRepository;
  }

  public Page<TriggerModel> findAll(PageRequest pageRequest) {
    return triggerRepository.findAll(pageRequest);
  }
}
