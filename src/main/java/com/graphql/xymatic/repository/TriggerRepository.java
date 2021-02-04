package com.graphql.xymatic.repository;

import com.graphql.xymatic.model.TriggerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriggerRepository extends JpaRepository<TriggerModel, Long> {}
