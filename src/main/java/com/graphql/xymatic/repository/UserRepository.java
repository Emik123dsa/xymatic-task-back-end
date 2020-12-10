package com.graphql.xymatic.repository;

import com.graphql.xymatic.model.UserModel;
import java.util.List;
import java.util.Optional;
import net.bytebuddy.TypeCache.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
  Optional<UserModel> findOneById(Long id);
  UserModel findOneByEmail(String email);
}
