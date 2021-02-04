package com.graphql.xymatic.repository;

import com.graphql.xymatic.model.ImpressionsModel;
import com.graphql.xymatic.model.PostModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpressionsRepository
  extends JpaRepository<ImpressionsModel, Long> {
  List<ImpressionsModel> findAllById(Long id);
}
