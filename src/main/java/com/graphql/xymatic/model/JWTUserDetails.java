package com.graphql.xymatic.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
@RequiredArgsConstructor
public class JWTUserDetails implements UserDetails {

  private static final long serialVersionUID =
    SpringSecurityCoreVersion.SERIAL_VERSION_UID;

  private final String username;
  private final String password;
  private final String token;
  private final List<SimpleGrantedAuthority> authorities;

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }
}
