package com.graphql.xymatic;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.graphql.xymatic.adapters.GraphQLErrorAdapter;
import com.graphql.xymatic.repository.ChartRepository;
import com.graphql.xymatic.repository.PostRepository;
import com.graphql.xymatic.repository.UserRepository;
import com.graphql.xymatic.resolver.MutationResolver;
import com.graphql.xymatic.resolver.QueryResolver;
import com.graphql.xymatic.resolver.SubscriptionResolver;
import com.graphql.xymatic.service.UserService;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLScalarType;
import graphql.servlet.GraphQLErrorHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ComponentScan
@SpringBootApplication
public class XymaticApplication implements CommandLineRunner {

  private final String jwtSecret = "XYMATIC";
  private static final Logger logger = LoggerFactory.getLogger(
    XymaticApplication.class
  );

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
    UserRepository userRepository,
    PostRepository postRepository
  ) {
    return new SubscriptionResolver(userRepository, postRepository);
  }

  /**
   *  Query Resolving
   * @param userRepository
   * @param postRepository
   * @return
   */
  @Bean
  public QueryResolver query(
    UserRepository userRepository,
    PostRepository postRepository,
    ChartRepository chartRepository
  ) {
    return new QueryResolver(userRepository, postRepository, chartRepository);
  }

  /**
   * Mutations Resolving
   * @param userRepository
   * @param postRepository
   * @return
   */
  @Bean
  public MutationResolver mutation(
    UserRepository userRepository,
    PostRepository postRepository
  ) {
    return new MutationResolver(userRepository, postRepository);
  }
  
  /**
   *  Open Filter Entity Manager
   * @return
   */
  @Bean 
  public Filter openFilter() {
    return new OpenEntityManagerInViewFilter();
  }
}
