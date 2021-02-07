package com.graphql.xymatic.resolver;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import com.graphql.xymatic.model.SubscribeModel;
import com.graphql.xymatic.model.TriggerModel;
import com.graphql.xymatic.service.ImpressionsService;
import com.graphql.xymatic.service.PlayService;
import com.graphql.xymatic.service.PostService;
import com.graphql.xymatic.service.TriggerService;
import com.graphql.xymatic.service.UserService;
import com.graphql.xymatic.sort.TriggerSort;
import com.oembedler.moon.graphql.boot.GraphQLWebAutoConfiguration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

import org.reactivestreams.*;
import org.slf4j.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationProvider;

import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.GraphQLContext;
import graphql.servlet.GraphQLWebsocketServlet;
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
  private final TriggerService triggerService;

  public SubscriptionResolver(
    AuthenticationProvider authentication,
    UserService userService,
    PostService postService,
    ImpressionsService impressionsService,
    PlayService playService,
    TriggerService triggerService
  ) {
    this.authentication = authentication;
    this.userService = userService;
    this.postService = postService;
    this.impressionsService = impressionsService;
    this.playService = playService;
    this.triggerService = triggerService;
  }

  public Publisher<SubscribeModel> userSubscribe() {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(
        num ->
          new SubscribeModel(
            userService.count(),
            LocalDateTime.now().withNano(0)
          )
      );
  }

  public Publisher<SubscribeModel> postSubscribe(DataFetchingEnvironment env) {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(
        num ->
          new SubscribeModel(
            postService.count(),
            LocalDateTime.now().withNano(0)
          )
      );
  }

  public Publisher<SubscribeModel> impressionsSubscribe() {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(
        num ->
          new SubscribeModel(
            impressionsService.count(),
            LocalDateTime.now().withNano(0)
          )
      );
  }

  public Publisher<SubscribeModel> playsSubscribe() {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(
        num ->
          new SubscribeModel(
            playService.count(),
            LocalDateTime.now().withNano(0)
          )
      );
  }

  public Publisher<Page<TriggerModel>> triggersSubscribe(
    Integer page,
    Integer size,
    TriggerSort triggerSort
  ) {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(
        trigger ->
          triggerService.findAll(
            PageRequest.of(
              page,
              size,
              triggerSort == null ? Sort.unsorted() : triggerSort.getSort()
            )
          )
      );
  }
}
