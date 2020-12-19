package com.graphql.xymatic.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BadCredentialsException
  extends RuntimeException
  implements GraphQLError {

  private static final long serialVersionUID = 1L;

  private Map<String, Object> extensions = new HashMap<>();

  public BadCredentialsException(String message, String email) {
    super(message);
    extensions.put("Bad Credentials: ", email);
  }

  @Override
  public List<SourceLocation> getLocations() {
    return null;
  }

  @Override
  public Map<String, Object> getExtensions() {
    return extensions;
  }

  @Override
  public ErrorType getErrorType() {
    return ErrorType.DataFetchingException;
  }
}
