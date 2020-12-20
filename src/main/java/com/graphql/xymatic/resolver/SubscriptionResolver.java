package com.graphql.xymatic.resolver;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import com.graphql.xymatic.model.SubscribeModel;
import com.graphql.xymatic.service.PostService;
import com.graphql.xymatic.service.UserService;
import java.time.Duration;
import java.time.LocalDateTime;
import org.reactivestreams.*;
import org.slf4j.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import reactor.core.publisher.*;

public class SubscriptionResolver implements GraphQLSubscriptionResolver {

  private static final Logger logger = LoggerFactory.getLogger(
    SubscriptionResolver.class
  );

  private final AuthenticationProvider authentication;

  private final UserService userService;
  private final PostService postService;

  public SubscriptionResolver(
    AuthenticationProvider authentication,
    UserService userService,
    PostService postService
  ) {
    this.authentication = authentication;
    this.userService = userService;
    this.postService = postService;
  }

  // @PreAuthorize("isAuthenticated()")
  public Publisher<SubscribeModel> userSubscribe() {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(num -> new SubscribeModel(userService.count(), LocalDateTime.now()));
  }

  public Publisher<SubscribeModel> postSubscribe() {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(num -> new SubscribeModel(postService.count(), LocalDateTime.now()));
  }

  public Publisher<SubscribeModel> impressionsSubscribe() {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(num -> new SubscribeModel(userService.count(), LocalDateTime.now()));
  }

  public Publisher<SubscribeModel> playsSubscribe() {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(num -> new SubscribeModel(userService.count(), LocalDateTime.now()));
  }
}
