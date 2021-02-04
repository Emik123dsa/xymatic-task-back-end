package com.graphql.xymatic.service;

import com.graphql.xymatic.model.PlaysModel;
import com.graphql.xymatic.repository.PlaysRepository;
import java.util.List;

import javax.print.attribute.standard.PageRanges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PlayService {

  private final PlaysRepository playsRepository;

  @Autowired
  public PlayService(PlaysRepository playsRepository) {
    this.playsRepository = playsRepository;
  }

  public Page<PlaysModel> findAll(PageRequest request) {
    return playsRepository.findAll(request);
  }

  public Long count() {
    return playsRepository.count();
  }
}
