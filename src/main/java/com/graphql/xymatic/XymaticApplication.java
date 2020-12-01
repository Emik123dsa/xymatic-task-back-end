package com.graphql.xymatic;

import com.graphql.xymatic.adapters.GraphQLErrorAdapter;
import com.graphql.xymatic.repository.PostRepository;
import com.graphql.xymatic.repository.UserRepository;
import com.graphql.xymatic.resolver.MutationResolver;
import com.graphql.xymatic.resolver.PostResolver;
import com.graphql.xymatic.resolver.QueryResolver;
import com.graphql.xymatic.resolver.SubscriptionResolver;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class XymaticApplication {
  public static void main(String[] args) {
    SpringApplication.run(XymaticApplication.class, args);
  }

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
  public SubscriptionResolver subscription(UserRepository userRepository) {
	  return new SubscriptionResolver(userRepository);
  }
  /**
   *  Query Resolving
   * @param userRepository
   * @param postRepository
   * @return
   */
  @Bean 
  public QueryResolver query(UserRepository userRepository, PostRepository postRepository) {
	  return new QueryResolver(userRepository, postRepository);
  }
  /**
   * Mutations Resolving
   * @param userRepository
   * @param postRepository
   * @return
   */
  @Bean 
  public MutationResolver mutation(UserRepository userRepository, PostRepository postRepository) {
	  return new MutationResolver(userRepository, postRepository);
  }
  /**
   * Posts Resolving
   * @param userRepository
   * @return
   */
  @Bean  
  public PostResolver postResolver(UserRepository userRepository) {
	  return new PostResolver(userRepository);
  }

}
