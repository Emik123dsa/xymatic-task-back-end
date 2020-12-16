package com.graphql.xymatic.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.graphql.xymatic.enums.PeriodEnums;
import com.graphql.xymatic.exception.UserNotFoundException;
import com.graphql.xymatic.model.ChartModel;
import com.graphql.xymatic.model.PostModel;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.repository.ChartRepository;
import com.graphql.xymatic.repository.PostRepository;
import com.graphql.xymatic.repository.UserRepository;
import java.util.List;
import org.slf4j.*;
import org.springframework.data.domain.*;

public class QueryResolver implements GraphQLQueryResolver {

  private static final Logger logger = LoggerFactory.getLogger(
    QueryResolver.class
  );

  private final PostRepository postRepository;

  private final UserRepository userRepository;

  private final ChartRepository chartRepository;

  public QueryResolver(
    UserRepository userRepository,
    PostRepository postRepository,
    ChartRepository chartRepository
  ) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.chartRepository = chartRepository;
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

  public Iterable<ChartModel> findUserByChart(PeriodEnums pEnums) {
    return chartRepository.findUserChart(pEnums);
  }

  public Iterable<ChartModel> findPostByChart(PeriodEnums pEnums) {
    return chartRepository.findPostChart(pEnums);
  }

  public List<PostModel> findPostsByEmail(String email)
    throws UserNotFoundException {
    UserModel user = userRepository.findOneByEmail(email);

    if (user == null) {
      throw new UserNotFoundException("User Not Found", email);
    }

    final Sort directSort = Sort.by(Sort.Direction.DESC, "createdAt");

    return postRepository.findAllByAuthor(user, directSort);
  }

  public long countUsers() {
    return userRepository.count();
  }

  public long countPosts() {
    return postRepository.count();
  }
}
