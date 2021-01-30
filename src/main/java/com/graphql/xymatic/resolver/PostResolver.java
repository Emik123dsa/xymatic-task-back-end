package com.graphql.xymatic.resolver;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.graphql.xymatic.model.ImpressionsModel;
import com.graphql.xymatic.model.PostModel;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.service.ImpressionsService;
import com.graphql.xymatic.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostResolver implements GraphQLResolver<PostModel> {

  private final UserService userService;
  private final ImpressionsService impressionsService;

  @Autowired
  public PostResolver(UserService userService, ImpressionsService impressionsService) {
    this.userService = userService;
    this.impressionsService = impressionsService;
  }

  public UserModel getUser(PostModel post) {
    return userService.findOneById(post.getUser().getId());
  }

  public List<ImpressionsModel> getVerbose(PostModel post) {
    return impressionsService.findAllByPost(post);
  }
}
