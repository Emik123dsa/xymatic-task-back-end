package com.graphql.xymatic.evaluators;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionRoot
  extends SecurityExpressionRoot
  implements MethodSecurityExpressionOperations {

  public CustomMethodSecurityExpressionRoot(Authentication authentication) {
    super(authentication);
  }

  @Override
  public void setFilterObject(Object filterObject) {}

  @Override
  public Object getFilterObject() {
    return this;
  }

  @Override
  public void setReturnObject(Object returnObject) {}

  @Override
  public Object getReturnObject() {
    return getPrincipal();
  }

  @Override
  public Object getThis() {
    return this;
  }
}
