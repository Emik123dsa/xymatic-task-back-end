package com.graphql.xymatic.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.graphql.xymatic.model.PostModel;
import com.graphql.xymatic.model.UserModel;
import com.graphql.xymatic.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostResolver implements GraphQLResolver<PostModel> {

  private final UserRepository userRepository;

  @Autowired
  public PostResolver(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<UserModel> getUser(PostModel post) {
    return userRepository.findOneById(post.getUser().getId());
  }
}
