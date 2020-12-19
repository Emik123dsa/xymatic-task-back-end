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
import com.graphql.xymatic.service.ChartService;
import com.graphql.xymatic.service.PostService;
import com.graphql.xymatic.service.UserService;
import com.graphql.xymatic.sort.DirectionSort;
import java.util.Arrays;
import java.util.List;
import org.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class QueryResolver implements GraphQLQueryResolver {

  private static final Logger logger = LoggerFactory.getLogger(
    QueryResolver.class
  );

  private final AuthenticationProvider authentication;

  private final PostService postService;
  private final UserService userService;
  private final ChartService chartService;

  public QueryResolver(
    AuthenticationProvider authentication,
    UserService userService,
    PostService postService,
    ChartService chartService
  ) {
    this.postService = postService;
    this.userService = userService;
    this.chartService = chartService;
    this.authentication = authentication;
  }

  public Iterable<PostModel> findAllPosts() {
    return postService.findAll();
  }

  public Iterable<UserModel> findAllUsers() {
    return userService.findAll();
  }

  @PreAuthorize("isAuthenticated()")
  public UserModel findUserByEmail(String email) throws UserNotFoundException {
    // UserModel user = userService.findOneByEmail(email);
    UserModel user = userService.getCurrentUser();

    return user;
  }

  public Iterable<ChartModel> findUserByChart(PeriodEnums pEnums) {
    return chartService.findUserChart(pEnums);
  }

  public Iterable<ChartModel> findPostByChart(PeriodEnums pEnums) {
    return chartService.findPostChart(pEnums);
  }

  public List<PostModel> findPostsByEmail(String email)
    throws UserNotFoundException {
    UserModel user = userService.findOneByEmail(email);

    if (user == null) {
      throw new UserNotFoundException("User Not Found", email);
    }

    final Sort directSort = Sort.by(
      DirectionSort.ASC.getDirection(),
      "createdAt"
    );

    return postService.findAllByAuthor(user, directSort);
  }

  @PreAuthorize("isAnonymous()")
  public UserModel signIn(String email, String password)
    throws UserNotFoundException {
    UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
      email,
      password
    );
    try {
      SecurityContextHolder
        .getContext()
        .setAuthentication(authentication.authenticate(credentials));
      return userService.getCurrentUser();
    } catch (AuthenticationException authException) {
      throw new UserNotFoundException(email, password);
    }
  }

  @PreAuthorize("isAuthenticated()")
  public long countUsers() {
    return userService.count();
  }

  @PreAuthorize("isAuthenticated()")
  public long countPosts() {
    return postService.count();
  }
}
