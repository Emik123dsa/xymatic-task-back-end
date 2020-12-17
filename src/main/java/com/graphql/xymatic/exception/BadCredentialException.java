package com.graphql.xymatic.exception;

import java.text.MessageFormat;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class BadCredentialException extends RuntimeException {

  private static final long serialVersionUID =
    SpringSecurityCoreVersion.SERIAL_VERSION_UID;

  private final String badCredentials;

  public BadCredentialException(String badCredentials) {
    this.badCredentials = badCredentials;
  }

  @Override
  public String getMessage() {
    return MessageFormat.format(
      "Bad Credentials : %s - exception",
      badCredentials
    );
  }
}
