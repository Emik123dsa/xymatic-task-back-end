package com.graphql.xymatic.service;

import com.graphql.xymatic.model.PostModel;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.repository.PostRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  private final PostRepository postRepository;

  @Autowired
  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public Page<PostModel> findAll(PageRequest request) {
    return postRepository.findAll(request);
  }

  public Boolean deleteById(Long id) {
    if (existsById(id)) {
      postRepository.deleteById(id);
      return true;
    }

    return false;
  }

  public List<PostModel> findAllByAuthor(UserModel userModel) {
    return postRepository.findAllByAuthor(userModel);
  }

  public long count() {
    return postRepository.count();
  }

  private boolean existsById(Long id) {
    return postRepository.existsById(id);
  }
}
