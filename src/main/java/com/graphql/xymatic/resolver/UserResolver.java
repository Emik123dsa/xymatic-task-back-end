package com.graphql.xymatic.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class UserResolver implements GraphQLResolver<UserModel> {

  private final UserService userService;

  @Autowired
  public UserResolver(UserService userService) {
    this.userService = userService;
  }

  @PreAuthorize("hasAuthority('USER')")
  public String getToken(UserModel userModel) {
    return userService.getToken(userModel);
  }
}
