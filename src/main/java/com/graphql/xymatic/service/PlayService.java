package com.graphql.xymatic.service;

import com.graphql.xymatic.model.PlaysModel;
import com.graphql.xymatic.repository.PlaysRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayService {

  private final PlaysRepository playsRepository;

  @Autowired
  public PlayService(PlaysRepository playsRepository) {
    this.playsRepository = playsRepository;
  }

  public List<PlaysModel> findAllPlays() {
    return playsRepository.findAll();
  }

  public Long count() {
    return playsRepository.count();
  }
}
