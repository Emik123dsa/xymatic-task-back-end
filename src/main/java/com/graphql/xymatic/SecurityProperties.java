package com.graphql.xymatic;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "xymatic.security")
public class SecurityProperties {

  private final String tokenSecret;

  private final String tokenIssuer;

  private final Duration tokenExpiration = Duration.ofHours(24);

  private final Integer tokenStrengthness = 10;

  public SecurityProperties(String tokenSecret, String tokenIssuer) {
    this.tokenSecret = tokenSecret;
    this.tokenIssuer = tokenIssuer;
  }

  public String getTokenSecret() {
    return tokenSecret;
  }

  public String getTokenIssuer() {
    return tokenIssuer;
  }

  public Duration getTokenExpriation() {
    return tokenExpiration;
  }

  public Integer getTokenStrengthness() {
    return tokenStrengthness;
  }
}
