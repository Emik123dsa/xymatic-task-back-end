package com.graphql.xymatic.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserExistsException
  extends RuntimeException
  implements GraphQLError {

  private static final long serialVersionUID = 1L;

  private Map<String, Object> extensions = new HashMap<>();

  public UserExistsException(String message, String user) {
    super(message);
    extensions.put("User Exists: ", user);
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
