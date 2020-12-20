package com.graphql.xymatic;

import com.graphql.xymatic.evaluators.CustomPermissionEvaluator;
import com.graphql.xymatic.handler.CustomMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

// @Configuration
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
  @Override
  protected MethodSecurityExpressionHandler createExpressionHandler() {
    CustomMethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler();
    expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator());
    return expressionHandler;
  }
}
