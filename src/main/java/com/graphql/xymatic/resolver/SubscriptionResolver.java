package com.graphql.xymatic.resolver;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import com.graphql.xymatic.model.SubscribeModel;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.repository.UserRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import org.reactivestreams.*;
import org.slf4j.*;
import reactor.core.publisher.*;

public class SubscriptionResolver implements GraphQLSubscriptionResolver {

  private static final Logger logger = LoggerFactory.getLogger(
    SubscriptionResolver.class
  );

  private final UserRepository userRepository;

  public SubscriptionResolver(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Publisher<SubscribeModel> userSubscribe() {
    return Flux
      .interval(Duration.ofSeconds(1))
      .map(
        num -> new SubscribeModel(userRepository.count(), LocalDateTime.now())
      );
  }
}
