package com.graphql.xymatic.service;

import com.graphql.xymatic.exception.ImpressionNotFoundException;
import com.graphql.xymatic.model.ImpressionsModel;
import com.graphql.xymatic.model.PostModel;
import com.graphql.xymatic.repository.ImpressionsRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

  public List<ImpressionsModel> findAllByPost(PostModel post)
    throws ImpressionNotFoundException {
    return impressionsRepository
      .findAllById(post.getId())
      .stream()
      .map(
        impression ->
          Optional
            .ofNullable(impression)
            .orElseThrow(
              () ->
                new ImpressionNotFoundException(
                  impression.getId().toString(),
                  post.getId().toString()
                )
            )
      )
      .collect(Collectors.toList());
  }

  public List<ImpressionsModel> findAll(PageRequest request) {
    return impressionsRepository
    .findAll(request)
    .stream()
    .collect(Collectors.toList());
  }

  public Long count() {
    return impressionsRepository.count();
  }
}
