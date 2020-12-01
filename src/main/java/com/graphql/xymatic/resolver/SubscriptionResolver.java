package com.graphql.xymatic.resolver;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import org.reactivestreams.*;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.repository.UserRepository;

import reactor.core.publisher.*;

public class SubscriptionResolver implements GraphQLSubscriptionResolver {
  
  private final UserRepository userRepository;

  public SubscriptionResolver(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Publisher<List<UserModel>> usersSubscribe() {
    return Flux.interval(Duration.ofSeconds(1)).map(num -> userRepository.findAll());
  }

  public Publisher<Integer> vendorSubscribe() {
    Random random = new Random();
    return Mono.just(random.nextInt());
  }

} 
