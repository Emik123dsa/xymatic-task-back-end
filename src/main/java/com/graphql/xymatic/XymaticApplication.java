package com.graphql.xymatic;

import com.graphql.xymatic.adapters.GraphQLErrorAdapter;
import com.graphql.xymatic.resolver.MutationResolver;
import com.graphql.xymatic.resolver.QueryResolver;
import com.graphql.xymatic.resolver.SubscriptionResolver;
import com.graphql.xymatic.service.ChartService;
import com.graphql.xymatic.service.ImpressionsService;
import com.graphql.xymatic.service.PlayService;
import com.graphql.xymatic.service.PostService;
import com.graphql.xymatic.service.RowsCountService;
import com.graphql.xymatic.service.UserService;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.authentication.AuthenticationProvider;

@ComponentScan
@SpringBootApplication
public class XymaticApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(XymaticApplication.class, args);
  }

  @Override
  public void run(String... args) {}

  @Bean
  public GraphQLErrorHandler errorHandler() {
    return new GraphQLErrorHandler() {
      @Override
      public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        List<GraphQLError> clientErrors = errors
          .stream()
          .filter(this::isClientError)
          .collect(Collectors.toList());

        List<GraphQLError> serverErrors = errors
          .stream()
          .filter(e -> !isClientError(e))
          .map(GraphQLErrorAdapter::new)
          .collect(Collectors.toList());

        List<GraphQLError> e = new ArrayList<>();
        e.addAll(clientErrors);
        e.addAll(serverErrors);
        return e;
      }

      protected boolean isClientError(GraphQLError error) {
        return !(
          error instanceof ExceptionWhileDataFetching ||
          error instanceof Throwable
        );
      }
    };
  }

  /**
   * Great!
   * Finally, we can use Graph
   * Ql resolvers for Web Socket connection
   * @param userRepository
   * @return
   */
  @Bean
  public SubscriptionResolver subscription(
    AuthenticationProvider authentication,
    UserService userService,
    PostService postService,
    ImpressionsService impressionsService,
    PlayService playService
  ) {
    return new SubscriptionResolver(
      authentication,
      userService,
      postService,
      impressionsService,
      playService
    );
  }

  /**
   *  Query Resolving
   * @param userRepository
   * @param postRepository
   * @return
   */
  @Bean
  public QueryResolver query(
    AuthenticationProvider authenticationProvider,
    UserService userService,
    PostService postService,
    ChartService chartService,
    RowsCountService rowsCountService,
    ImpressionsService impressionsService
  ) {
    return new QueryResolver(
      authenticationProvider,
      userService,
      postService,
      chartService,
      rowsCountService,
      impressionsService
    );
  }

  /**
   * Mutations Resolving
   * @param userRepository
   * @param postRepository
   * @return
   */
  @Bean
  public MutationResolver mutation(
    UserService userService,
    PostService postService
  ) {
    return new MutationResolver(userService, postService);
  }

  /**
   *  Open Filter Entity Manager
   * @return
   */
  @Bean
  public Filter openFilter() {
    return new OpenEntityManagerInViewFilter();
  }

  /**
   * Message Digestion
   * @return
   * @throws NoSuchAlgorithmException
   */
  @Bean
  public MessageDigest messageDigest() throws NoSuchAlgorithmException {
    return MessageDigest.getInstance("SHA1");
  }
}
