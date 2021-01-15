package com.graphql.xymatic.resolver;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import com.graphql.xymatic.model.SubscribeModel;
import com.graphql.xymatic.repository.ImpressionsRepository;
import com.graphql.xymatic.repository.PlaysRepository;
import com.graphql.xymatic.service.ImpressionsService;
import com.graphql.xymatic.service.PlayService;
import com.graphql.xymatic.service.PostService;
import com.graphql.xymatic.service.UserService;
import java.time.Duration;
import java.time.LocalDate;
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
  private final ImpressionsService impressionsService;
  private final PlayService playService; 


  public SubscriptionResolver(
    AuthenticationProvider authentication,
    UserService userService,
    PostService postService,
    ImpressionsService impressionsService, 
    PlayService playService
  ) {
    this.authentication = authentication;
    this.userService = userService;
    this.postService = postService;
    this.impressionsService = impressionsService;
    this.playService = playService;
  }

  public Publisher<SubscribeModel> userSubscribe() {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(num -> new SubscribeModel(userService.count(), LocalDateTime.now().withNano(0)));
  }

  public Publisher<SubscribeModel> postSubscribe() {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(num -> new SubscribeModel(postService.count(), LocalDateTime.now().withNano(0)));
  }

  public Publisher<SubscribeModel> impressionsSubscribe() {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(num -> new SubscribeModel(impressionsService.count(),LocalDateTime.now().withNano(0)));
  }

  public Publisher<SubscribeModel> playsSubscribe() {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(num -> new SubscribeModel(playService.count(), LocalDateTime.now().withNano(0)));
  }
}
