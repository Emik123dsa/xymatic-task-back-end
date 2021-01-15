package com.graphql.xymatic.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.graphql.xymatic.exception.PostNotFoundException;
import com.graphql.xymatic.exception.UserNotFoundException;
import com.graphql.xymatic.model.ImpressionsModel;
import com.graphql.xymatic.model.PostModel;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.service.ImpressionsService;
import com.graphql.xymatic.service.PostService;
import com.graphql.xymatic.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImpressionsResolver implements GraphQLResolver<ImpressionsModel> {

  private final ImpressionsService impressionsService;
  private final UserService userService;
  private final PostService postService;

  @Autowired
  public ImpressionsResolver(
    ImpressionsService impressionsService,
    UserService userService,
    PostService postService
  ) {
    this.impressionsService = impressionsService;
    this.userService = userService;
    this.postService = postService;
  }

  public UserModel getAuthor(ImpressionsModel impressionsModel) {
    return userService.findOneById(impressionsModel.getAuthor().getId());
  }

  public PostModel getPost(ImpressionsModel impressionsModel) {
    return postService.findOneById(impressionsModel.getPost().getId());
  }
}
