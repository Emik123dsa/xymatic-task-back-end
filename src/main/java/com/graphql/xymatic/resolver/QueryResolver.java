package com.graphql.xymatic.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.graphql.xymatic.exception.UserNotFoundException;
import com.graphql.xymatic.model.PostModel;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.repository.PostRepository;
import com.graphql.xymatic.repository.UserRepository;
import javax.persistence.criteria.CriteriaBuilder;

public class QueryResolver implements GraphQLQueryResolver {

  private final PostRepository postRepository;

  private final UserRepository userRepository;

  public QueryResolver(
    UserRepository userRepository,
    PostRepository postRepository
  ) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  public Iterable<PostModel> findAllPosts() {
    return postRepository.findAll();
  }

  public Iterable<UserModel> findAllUsers() {
    return userRepository.findAll();
  }

  public UserModel findUserByEmail(String email) throws UserNotFoundException {
    UserModel user = userRepository.findOneByEmail(email);
    if (user == null) {
      throw new UserNotFoundException("User Not Found", email);
    }

    return user;
  }

  // public

  public long countUsers() {
    return userRepository.count();
  }

  public long countPosts() {
    return postRepository.count();
  }
}
