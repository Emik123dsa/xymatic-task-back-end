package com.graphql.xymatic.adapters;

import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class GraphQLErrorAdapter implements GraphQLError {

  private static final long serialVersionUID = 1L;

  private GraphQLError error;

  @Autowired
  public GraphQLErrorAdapter(GraphQLError error) {
    this.error = error;
  }

  @Override
  public Map<String, Object> getExtensions() {
    return error.getExtensions();
  }

  @Override
  public List<SourceLocation> getLocations() {
    return error.getLocations();
  }

  @Override
  public ErrorType getErrorType() {
    return error.getErrorType();
  }

  @Override
  public List<Object> getPath() {
    return error.getPath();
  }

  @Override
  public Map<String, Object> toSpecification() {
    return error.toSpecification();
  }

  @Override
  public String getMessage() {
    return (error instanceof ExceptionWhileDataFetching)
      ? ((ExceptionWhileDataFetching) error).getException().getMessage()
      : error.getMessage();
  }
}
