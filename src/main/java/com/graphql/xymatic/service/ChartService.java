package com.graphql.xymatic.service;

import com.graphql.xymatic.enums.PeriodEnums;
import com.graphql.xymatic.model.ChartModel;
import com.graphql.xymatic.repository.ChartRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChartService {

  private final ChartRepository chartRepository;
  @Autowired
  public ChartService(ChartRepository chartRepository) {
    this.chartRepository = chartRepository;
  }
  /**
   * User Chart
   * @param periodEnums
   * @return
   */
  public List<ChartModel> findUserChart(PeriodEnums periodEnums) {
    return chartRepository.findUserChart(periodEnums);
  }
  /**
   * Post Chart
   * @param periodEnums
   * @return
   */
  public List<ChartModel> findPostChart(PeriodEnums periodEnums) {
    return chartRepository.findPostChart(periodEnums);
  }
  /**
   * Impressions Chart
   * @param periodEnums
   * @return
   */
  public List<ChartModel> findImpressionsChart(PeriodEnums periodEnums) {
    return chartRepository.findImpressionsChart(periodEnums);
  }
  /**
   * Plays Chart
   * @param periodEnums
   * @return
   */
  public List<ChartModel> findPlayChart(PeriodEnums periodEnums) {
    return chartRepository.findPlayChart(periodEnums);
  }
}
