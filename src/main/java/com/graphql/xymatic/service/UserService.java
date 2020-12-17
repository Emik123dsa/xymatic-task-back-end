package com.graphql.xymatic.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.graphql.xymatic.SecurityProperties;
import com.graphql.xymatic.exception.BadCredentialException;
import com.graphql.xymatic.exception.RoleException;
import com.graphql.xymatic.model.JWTUserDetails;
import com.graphql.xymatic.model.RoleModel;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.repository.RolesRepository;
import com.graphql.xymatic.repository.UserRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final RolesRepository rolesRepository;
  private final JWTVerifier jwtVerifier;
  private final Algorithm algorithm;
  private final SecurityProperties security;

  @Autowired
  public UserService(
    UserRepository userRepository,
    RolesRepository rolesRepository,
    JWTVerifier jwtVerifier,
    Algorithm algorithm,
    SecurityProperties security
  ) {
    this.userRepository = userRepository;
    this.rolesRepository = rolesRepository;
    this.jwtVerifier = jwtVerifier;
    this.algorithm = algorithm;
    this.security = security;
  }

  public List<UserModel> findAll() {
    return userRepository.findAll();
  }

  public void saveUser(UserModel user) {
    userRepository.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public JWTUserDetails loadUserByUsername(String email)
    throws UsernameNotFoundException {
    UserModel userModel = userRepository.findOneByEmail(email);

    if (userModel == null) {
      throw new UsernameNotFoundException(email);
    }

    JWTUserDetails jwtUserDetails = getUserDetails(
      userModel,
      getToken(userModel)
    );

    if (jwtUserDetails == null) {
      throw new UsernameNotFoundException(email);
    }

    return jwtUserDetails;
  }

  @Transactional(readOnly = true)
  public JWTUserDetails loadUserByToken(String token)
    throws BadCredentialException {
    final DecodedJWT dOptional = getDecodedToken(token);

    final String email = dOptional.getSubject();

    final UserModel userModel = userRepository.findOneByEmail(email);

    if (email == null) {
      throw new BadCredentialException(email);
    }

    JWTUserDetails jwtUserDetails = getUserDetails(userModel, token);

    return jwtUserDetails;
  }

  private JWTUserDetails getUserDetails(UserModel userModel, String token)
    throws RoleException {
    final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

    final RoleModel rModel = rolesRepository.findOneByAuthor(userModel);

    if (rModel == null) {
      throw new RoleException(null);
    }

    authorities.add(new SimpleGrantedAuthority(rModel.getRole()));

    return JWTUserDetails
      .builder()
      .username(userModel.getEmail())
      .password(userModel.getPassword())
      .token(token)
      .authorities(authorities)
      .build();
  }

  @Transactional(readOnly = true)
  public String getToken(UserModel userModel) {
    Instant time = Instant.now();
    Instant expiration = Instant.now().plus(security.getTokenExpriation());
    return JWT
      .create()
      .withIssuer(security.getTokenIssuer())
      .withIssuedAt(Date.from(time))
      .withExpiresAt(Date.from(expiration))
      .withSubject(userModel.getEmail())
      .sign(algorithm);
  }

  private DecodedJWT getDecodedToken(String token) {
    try {
      return jwtVerifier.verify(token);
    } catch (JWTVerificationException jwtVerificationException) {
      return null;
    }
  }
}
