package com.graphql.xymatic.utils;

import com.graphql.xymatic.model.JWTUserDetails;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Getter
public class JWTPreAuthenticationToken
  extends PreAuthenticatedAuthenticationToken {

  private static final long serialVersionUID =
    SpringSecurityCoreVersion.SERIAL_VERSION_UID;

  @Builder
  public JWTPreAuthenticationToken(
    JWTUserDetails principal,
    WebAuthenticationDetails details
  ) {
    super(principal, null, principal.getAuthorities());
    super.setDetails(details);
  }

  @Override
  public Object getCredentials() {
    return null;
  }
}
