package com.graphql.xymatic.resolver;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import org.reactivestreams.*;
import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import com.graphql.xymatic.model.StockPrice;

import reactor.core.publisher.Flux;

public class SubscriptionResolver implements GraphQLSubscriptionResolver {

  public Publisher<StockPrice> stockPrice(String symbol) {
    Random random = new Random();
    return Flux.interval(Duration.ofSeconds(1))
            .map(num -> new StockPrice(symbol, random.nextDouble(), LocalDateTime.now()));
  }

} 
