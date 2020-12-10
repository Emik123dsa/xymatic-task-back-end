package com.graphql.xymatic.repository;

import com.graphql.xymatic.enums.PeriodEnums;
import com.graphql.xymatic.model.ChartModel;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public interface ChartRepository {
  List<ChartModel> findChartUserByNative(PeriodEnums periodEnums);

  public class ChartModelImpl implements ChartRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<ChartModel> findChartUserByNative(PeriodEnums periodEnums) {
      StringBuffer stringBuffer = new StringBuffer();

      stringBuffer.append("WITH USER_MOCKS AS ( ");
      stringBuffer.append(
        "SELECT generate_series(created_at::timestamp, current_date::timestamp, ?1::interval) as ddate, ?1::interval as ddays FROM "
      );
      stringBuffer.append(
        "(SELECT min(created_at) AS created_at FROM xt_users LIMIT 1) AS USER_SCHEMA) "
      );
      stringBuffer.append("SELECT ddate, count(xt_users.*) as users, ");

      return Collections.checkedList(
        entityManager
          .createNativeQuery(stringBuffer.toString())
          .setParameter(1, periodEnums.getPeriod())
          .getResultList(),
        ChartModel.class
      );
    }
  }
}
