package com.graphql.xymatic.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class ImpressionNotFoundException
  extends RuntimeException
  implements GraphQLError {

  private static final long serialVersionUID =
    SpringSecurityCoreVersion.SERIAL_VERSION_UID;

  private Map<String, Object> extensions = new HashMap<>();

  public ImpressionNotFoundException(String message, String impression) {
    super(message);
    extensions.put("invalid Impression: ", impression);
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
