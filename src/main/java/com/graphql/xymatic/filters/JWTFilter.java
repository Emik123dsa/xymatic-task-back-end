package com.graphql.xymatic.filters;

import com.graphql.xymatic.SecurityProperties;
import com.graphql.xymatic.service.UserService;
import com.graphql.xymatic.utils.JWTPreAuthenticationToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final Pattern BEARER_PATTERN = Pattern.compile(
    "^Bearer (.+?)$"
  );

  private final UserService userService;
  private final SecurityProperties security;

  @Autowired
  public JWTFilter(UserService userService, SecurityProperties security) {
    this.userService = userService;
    this.security = security;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  )
    throws IOException, ServletException {
    getToken(request)
      .map(userService::loadUserByToken)
      .map(
        userDetails ->
          JWTPreAuthenticationToken
            .builder()
            .principal(userDetails)
            .details(new WebAuthenticationDetailsSource().buildDetails(request))
            .build()
      )
      .ifPresent(
        authentication ->
          SecurityContextHolder.getContext().setAuthentication(authentication)
      );

    filterChain.doFilter(request, response);
  }

  private Optional<String> getToken(HttpServletRequest httpServletRequest) {
    return Optional
      .ofNullable(httpServletRequest.getHeader(AUTHORIZATION_HEADER))
      .filter(s -> !s.isEmpty())
      .map(BEARER_PATTERN::matcher)
      .filter(Matcher::find)
      .map(matcher -> matcher.group(1));
  }
}
