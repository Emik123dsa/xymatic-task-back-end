package com.graphql.xymatic.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.graphql.xymatic.model.PostModel;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.repository.PostRepository;
import com.graphql.xymatic.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public class MutationResolver implements GraphQLMutationResolver {

  private final UserRepository userRepository;

  private final PostRepository postRepository;

  public MutationResolver(
    UserRepository userRepository,
    PostRepository postRepository
  ) {
    this.userRepository = userRepository;
    this.postRepository = postRepository;
  }

  @PreAuthorize("isAnonymous()")
  public UserModel newUser(String name, String password, String email) {
    UserModel userModel = new UserModel(name, email, password);
    userRepository.save(userModel);
    return userModel;
  }

  public PostModel newPost(String title, String content, Long userId) {
    PostModel postModel = new PostModel();

    postModel.setTitle(title);
    postModel.setContent(content);
    // postModel.setUser(new UserModel(userId));

    postRepository.save(postModel);

    return postModel;
  }

  public boolean deletePost(Long id) {
    postRepository.deleteById(id);
    return true;
  }
}
