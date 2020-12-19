package com.graphql.xymatic.service;

import com.graphql.xymatic.model.PostModel;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.repository.PostRepository;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  private final PostRepository postRepository;

  @Autowired
  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public List<PostModel> findAll() {
    return postRepository.findAll();
  }

  public List<PostModel> findAllByAuthor(UserModel userModel, Sort sort) {
    return postRepository.findAllByAuthor(userModel, sort);
  }

  public long count() {
    return postRepository.count();
  }
}
