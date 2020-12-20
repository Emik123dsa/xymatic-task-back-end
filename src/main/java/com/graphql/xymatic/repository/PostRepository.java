package com.graphql.xymatic.repository;

import com.graphql.xymatic.model.PostModel;
import com.graphql.xymatic.model.UserModel;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {
  Optional<PostModel> findOneById(Long id);
  List<PostModel> findAllByAuthor(UserModel userModel);
}
