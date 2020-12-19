package com.graphql.xymatic.service;

import static com.graphql.xymatic.utils.StreamUtils.collectionStreams;
import static java.util.function.Predicate.not;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.graphql.xymatic.SecurityProperties;
import com.graphql.xymatic.enums.RoleEnums;
import com.graphql.xymatic.enums.StatusEnums;
import com.graphql.xymatic.exception.BadCredentialsException;
import com.graphql.xymatic.exception.RoleNotFoundException;
import com.graphql.xymatic.exception.UserExistsException;
import com.graphql.xymatic.exception.UserNotFoundException;
import com.graphql.xymatic.model.JWTUserDetails;
import com.graphql.xymatic.model.RoleModel;
import com.graphql.xymatic.model.UserInput;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.repository.RolesRepository;
import com.graphql.xymatic.repository.UserRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

  private static final Logger logger = LoggerFactory.getLogger(
    UserService.class
  );

  private final UserRepository userRepository;
  private final RolesRepository rolesRepository;
  private final JWTVerifier jwtVerifier;
  private final Algorithm algorithm;
  private final SecurityProperties security;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(
    UserRepository userRepository,
    RolesRepository rolesRepository,
    JWTVerifier jwtVerifier,
    Algorithm algorithm,
    SecurityProperties security,
    PasswordEncoder passwordEncoder
  ) {
    this.userRepository = userRepository;
    this.rolesRepository = rolesRepository;
    this.jwtVerifier = jwtVerifier;
    this.algorithm = algorithm;
    this.security = security;
    this.passwordEncoder = passwordEncoder;
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
    UserModel userModel = userRepository
      .findByEmail(email)
      .map(user -> user)
      .orElseThrow(
        () -> new BadCredentialsException("User wasn't found", email)
      );

    JWTUserDetails jwtUserDetails = getUserDetails(
      userModel,
      getToken(userModel)
    );

    if (jwtUserDetails == null) {
      throw new UsernameNotFoundException(email);
    }

    return jwtUserDetails;
  }

  @Transactional
  public JWTUserDetails loadUserByToken(String token)
    throws BadCredentialsException, UserNotFoundException {
    Optional<DecodedJWT> decoded = getDecodedToken(token);

    Optional<String> email = decoded.map(o -> o.getSubject());

    if (!email.isPresent()) {
      throw new BadCredentialsException("Unauthorized | 401", token);
    }

    final UserModel userModel = userRepository
      .findByEmail(email.get())
      .map(user -> user)
      .orElseThrow(
        () -> new UserNotFoundException("Unauthorized | 401", email.get())
      );

    JWTUserDetails jwtUserDetails = getUserDetails(userModel, token);

    if (jwtUserDetails == null) {
      throw new UserNotFoundException("Unauthorized | 401", token);
    }

    return jwtUserDetails;
  }

  @Transactional
  public UserModel createNewUser(UserInput userInput)
    throws UserExistsException {
    if (!exists(userInput)) {
      UserModel userModel = new UserModel(
        userInput.getEmail(),
        passwordEncoder.encode(userInput.getPassword()),
        userInput.getName()
      );

      userModel.setStatus(StatusEnums.active);
      userModel.setRoles(Set.of(RoleEnums.USER));

      return userRepository.saveAndFlush(userModel);
    } else {
      throw new UserExistsException("User Exists", userInput.getEmail());
    }
  }

  @Transactional(readOnly = true)
  public UserModel getCurrentUser() {
    return Optional
      .ofNullable(SecurityContextHolder.getContext())
      .map(SecurityContext::getAuthentication)
      .map(Authentication::getName)
      .flatMap(userRepository::findByEmail)
      .orElse(null);
  }

  public Boolean isAdmin() {
    return Optional
      .ofNullable(SecurityContextHolder.getContext())
      .map(SecurityContext::getAuthentication)
      .map(Authentication::getAuthorities)
      .stream()
      .flatMap(Collection::stream)
      .map(GrantedAuthority::getAuthority)
      .anyMatch(RoleEnums.ADMIN.getRole()::equals);
  }

  public Boolean isAuthenticated() {
    return Optional
      .ofNullable(SecurityContextHolder.getContext())
      .map(SecurityContext::getAuthentication)
      .filter(Authentication::isAuthenticated)
      .filter(not(this::isAnonymous))
      .isPresent();
  }

  public Boolean isAnonymous(Authentication authentication) {
    return authentication instanceof AnonymousAuthenticationToken;
  }

  private Boolean exists(UserInput userInput) {
    return userRepository.existsByEmail(userInput.getEmail());
  }

  private JWTUserDetails getUserDetails(UserModel userModel, String token)
    throws RoleNotFoundException {
    final Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<SimpleGrantedAuthority>();

    for (RoleEnums role : userModel.getRoles()) {
      grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
    }

    return JWTUserDetails
      .builder()
      .username(userModel.getEmail())
      .password(userModel.getPassword())
      .token(token)
      .authorities(
        collectionStreams(grantedAuthorities).collect(Collectors.toList())
      )
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

  private Optional<DecodedJWT> getDecodedToken(String token) {
    try {
      return Optional.of(jwtVerifier.verify(token));
    } catch (JWTVerificationException jwtVerificationException) {
      return Optional.empty();
    }
  }

  public UserModel findOneByEmail(String email) throws UserNotFoundException {
    return userRepository
      .findByEmail(email)
      .map(user -> user)
      .orElseThrow(
        () -> new UserNotFoundException("Unauthorized | 401", email)
      );
  }

  public long count() {
    return userRepository.count();
  }
}
