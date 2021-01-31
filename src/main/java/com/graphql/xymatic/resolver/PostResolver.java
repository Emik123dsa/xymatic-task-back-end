package com.graphql.xymatic.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.graphql.xymatic.model.ImpressionsModel;
import com.graphql.xymatic.model.PostModel;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.service.ImpressionsService;
import com.graphql.xymatic.service.PostService;
import com.graphql.xymatic.service.UserService;
import com.graphql.xymatic.sort.PostSort;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PostResolver implements GraphQLResolver<PostModel> {

  private final UserService userService;
  private final ImpressionsService impressionsService;
  private final PostService postService;

  @Autowired
  public PostResolver(UserService userService, ImpressionsService impressionsService, PostService postService) {
    this.userService = userService;
    this.impressionsService = impressionsService;
    this.postService = postService;
  }

  public UserModel getUser(PostModel post) {
    return userService.findOneById(post.getUser().getId());
  }

  public Long getCount(PostModel post) {
    return postService.count();
  }

  public List<ImpressionsModel> getVerbose(PostModel post) {
    return impressionsService.findAllByPost(post);
  }
}
