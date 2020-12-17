package com.graphql.xymatic.exception;

import java.text.MessageFormat;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class RoleException extends RuntimeException {

  private static final long serialVersionUID =
    SpringSecurityCoreVersion.SERIAL_VERSION_UID;

  private final String role;

  public RoleException(String role) {
    this.role = role;
  }

  @Override
  public String getMessage() {
    return MessageFormat.format("Role %s hasn't been resolved ", role);
  }
}
