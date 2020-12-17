package com.graphql.xymatic.repository;

import com.graphql.xymatic.model.RoleModel;
import com.graphql.xymatic.model.UserModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<RoleModel, Long> {
  RoleModel findOneByAuthor(UserModel author);
}
