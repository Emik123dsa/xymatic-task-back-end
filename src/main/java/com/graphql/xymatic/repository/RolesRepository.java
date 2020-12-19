package com.graphql.xymatic.repository;

import com.graphql.xymatic.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<RoleModel, Long> {}
