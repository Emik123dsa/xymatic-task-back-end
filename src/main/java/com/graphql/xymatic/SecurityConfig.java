package com.graphql.xymatic;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.graphql.xymatic.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

  @Bean
  public Algorithm jwtAlgorithm(SecurityProperties security) {
    return Algorithm.HMAC256(security.getTokenSecret());
  }

  @Bean
  public JWTVerifier verifier(
    SecurityProperties security,
    Algorithm algorithm
  ) {
    return JWT.require(algorithm).withIssuer(security.getTokenIssuer()).build();
  }

  @Bean
  public PasswordEncoder passwordEncoder(SecurityProperties security) {
    return new BCryptPasswordEncoder(security.getTokenStrengthness());
  }

  @Bean
  public AuthenticationProvider authenticationProvider(
    UserService userService,
    PasswordEncoder passwordEncoder
  ) {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    return daoAuthenticationProvider;
  }
}
