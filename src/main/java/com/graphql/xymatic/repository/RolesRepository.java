package com.graphql.xymatic.repository;

import com.graphql.xymatic.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<RoleModel, Long> {}
