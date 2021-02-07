package com.graphql.xymatic.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.graphql.xymatic.enums.PeriodEnums;
import com.graphql.xymatic.exception.UserNotFoundException;
import com.graphql.xymatic.model.ChartModel;
import com.graphql.xymatic.model.ImpressionsModel;
import com.graphql.xymatic.model.PlaysModel;
import com.graphql.xymatic.model.PostModel;
import com.graphql.xymatic.model.RowsCountModel;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.service.ChartService;
import com.graphql.xymatic.service.ImpressionsService;
import com.graphql.xymatic.service.PlayService;
import com.graphql.xymatic.service.PostService;
import com.graphql.xymatic.service.RowsCountService;
import com.graphql.xymatic.service.UserService;
import com.graphql.xymatic.sort.ImpressionSort;
import com.graphql.xymatic.sort.PlaySort;
import com.graphql.xymatic.sort.PostSort;
import com.graphql.xymatic.sort.UserSort;
import java.util.List;
import org.slf4j.*;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class QueryResolver implements GraphQLQueryResolver {

  private static final Logger logger = LoggerFactory.getLogger(
    QueryResolver.class
  );

  private final AuthenticationProvider authentication;

  private final PostService postService;
  private final UserService userService;
  private final ChartService chartService;
  private final ImpressionsService impressionsService;
  private final RowsCountService rowsCountService;
  private final PlayService playService;

  public QueryResolver(
    AuthenticationProvider authentication,
    UserService userService,
    PostService postService,
    ChartService chartService,
    PlayService playService,
    RowsCountService rowsCountService,
    ImpressionsService impressionsService
  ) {
    this.postService = postService;
    this.userService = userService;
    this.chartService = chartService;
    this.rowsCountService = rowsCountService;
    this.authentication = authentication;
    this.impressionsService = impressionsService;
    this.playService = playService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  public Iterable<PostModel> findAllPosts(
    Integer page,
    Integer size,
    PostSort sort
  ) {
    return postService.findAll(
      PageRequest.of(
        page,
        size,
        sort != null ? sort.getSort() : Sort.unsorted()
      )
    );
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  public Iterable<UserModel> findAllUsers(
    Integer page,
    Integer size,
    UserSort sort
  ) {
    return userService.findAll(
      PageRequest.of(
        page,
        size,
        sort != null ? sort.getSort() : Sort.unsorted()
      )
    );
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  public Iterable<PlaysModel> findAllPlays(
    Integer page,
    Integer size,
    PlaySort sort
  ) {
    return playService.findAll(
      PageRequest.of(
        page,
        size,
        sort != null ? sort.getSort() : Sort.unsorted()
      )
    );
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  public Iterable<ImpressionsModel> findAllImpressions(
    Integer page,
    Integer size,
    ImpressionSort sort
  ) {
    return impressionsService.findAll(
      PageRequest.of(
        page,
        size,
        sort != null ? sort.getSort() : Sort.unsorted()
      )
    );
  }

  @PreAuthorize("isAuthenticated()")
  public UserModel findUserByEmail(String email) throws UserNotFoundException {
    return userService.findOneByEmail(email);
  }

  public Iterable<ChartModel> findUserByChart(PeriodEnums pEnums) {
    return chartService.findUserChart(pEnums);
  }

  public Iterable<ChartModel> findPostByChart(PeriodEnums pEnums) {
    return chartService.findPostChart(pEnums);
  }

  public Iterable<ChartModel> findImpressionsByChart(PeriodEnums pEnums) {
    return chartService.findImpressionsChart(pEnums);
  }

  public Iterable<ChartModel> findPlayByChart(PeriodEnums pEnums) {
    return chartService.findPlayChart(pEnums);
  }

  public List<ImpressionsModel> findAllImpressions() {
    return impressionsService.findAll();
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
  public UserModel getCurrentUser() {
    return userService.getCurrentUser();
  }

  // @PreAuthorize("hasAnyAuthority")
  public long countUsers() {
    return userService.count();
  }

  @PreAuthorize("isAuthenticated()")
  public long countPosts() {
    return postService.count();
  }

  //@PreAuthorize("hasAuthority('ADMIN')")
  public List<RowsCountModel> countAllRows() {
    return rowsCountService.countAllRows();
  }
}
