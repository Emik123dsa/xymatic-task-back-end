package com.graphql.xymatic.repository;

import com.graphql.xymatic.enums.PeriodEnums;
import com.graphql.xymatic.model.ChartModel;
import com.graphql.xymatic.repository.ChartRepository;
import java.util.List;

public interface ChartRepository {
  List<ChartModel> findUserChart(PeriodEnums periodEnums);
  List<ChartModel> findPostChart(PeriodEnums periodEnums);
  // List<ChartModel> findImpressionsChart(PeriodEnums periodEnums);
  // List<ChartModel> findPlaysChart(PeriodEnums periodEnums);
  // List<ChartModel> findTriggersChart(PeriodEnums periodEnums);
}
