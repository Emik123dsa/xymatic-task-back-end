package com.graphql.xymatic;

import com.graphql.xymatic.filters.JWTFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(com.graphql.xymatic.SecurityProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final AuthenticationProvider authenticationProvider;
  private final JWTFilter jwtFilter;

  @Autowired
  public WebSecurityConfig(
    AuthenticationProvider authenticationProvider,
    JWTFilter jwtFilter
  ) {
    this.authenticationProvider = authenticationProvider;
    this.jwtFilter = jwtFilter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder authManagerBuilder) {
    authManagerBuilder.authenticationProvider(authenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .cors()
      .disable()
      .csrf()
      .disable()
      .authorizeRequests()
      .anyRequest()
      .permitAll()
      .and()
      .requestCache()
      .requestCache(new NullRequestCache())
      .and()
      .httpBasic()
      .and()
      .headers()
      .frameOptions()
      .sameOrigin()
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .addFilterBefore(jwtFilter, RequestHeaderAuthenticationFilter.class);
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowCredentials(false);
    configuration.addAllowedOrigin("*");
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("GET");
    configuration.addAllowedMethod("PUT");
    configuration.addAllowedMethod("POST");
    configuration.addAllowedMethod("OPTIONS");
    configuration.setAllowedMethods(List.of("*"));
    source.registerCorsConfiguration("/**", configuration);

    return new CorsFilter(source);
  }
}
