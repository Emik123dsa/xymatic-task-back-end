package com.graphql.xymatic.repository;

import com.graphql.xymatic.model.PlaysModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaysRepository extends JpaRepository<PlaysModel, Long> {}
