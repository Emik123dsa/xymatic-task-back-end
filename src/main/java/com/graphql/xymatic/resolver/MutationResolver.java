package com.graphql.xymatic.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.graphql.xymatic.exception.UserExistsException;
import com.graphql.xymatic.model.PostModel;
import com.graphql.xymatic.model.UserInput;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.repository.PostRepository;
import com.graphql.xymatic.repository.UserRepository;
import com.graphql.xymatic.service.PostService;
import com.graphql.xymatic.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;

public class MutationResolver implements GraphQLMutationResolver {

  private final UserService userService;
  private final PostService postService;

  public MutationResolver(UserService userService, PostService postService) {
    this.userService = userService;
    this.postService = postService;
  }

  @PreAuthorize("isAnonymous()")
  public UserModel newUser(UserInput userInput) throws UserExistsException {
    return userService.createNewUser(userInput);
  }

  @PreAuthorize("isAuthenticated()")
  public PostModel newPost(String title, String content, Long userId) {
    PostModel postModel = new PostModel();

    postModel.setTitle(title);
    postModel.setContent(content);
    // postModel.setUser(new UserModel(userId));

    // postService.save(postModel);

    return postModel;
  }

  @PreAuthorize("isAuthenticated()")
  public boolean deletePost(Long id) {
    postService.deleteById(id);
    return true;
  }
}
