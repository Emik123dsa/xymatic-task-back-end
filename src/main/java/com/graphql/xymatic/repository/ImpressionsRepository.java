package com.graphql.xymatic.repository;

import com.graphql.xymatic.model.ImpressionsModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpressionsRepository
  extends JpaRepository<ImpressionsModel, Long> {}
