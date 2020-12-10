package com.graphql.xymatic.repository;

import com.graphql.xymatic.enums.PeriodEnums;
import com.graphql.xymatic.model.ChartModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcChartRepository implements ChartRepository {

  @Autowired
  public JdbcTemplate jdbcTemplate;

  @Override
  public List<ChartModel> findUserChart(PeriodEnums pEnums) {
    StringBuffer stringBuffer = new StringBuffer();

    stringBuffer.append("WITH MOCKS AS ( ");
    stringBuffer.append(
      "SELECT generate_series(created_at::timestamp, current_date::timestamp, ?::interval) as ddate, ?::interval as ddays FROM "
    );
    stringBuffer.append(
      "(SELECT min(created_at) AS created_at FROM xt_users LIMIT 1) AS SCHEMA) "
    );
    stringBuffer.append(
      "SELECT ddate as timestamp, count(xt_users.*) as delta, sum(count(xt_users.*)) over (order by ddate) as deltaTotal "
    );
    stringBuffer.append(
      "FROM MOCKS LEFT JOIN xt_users ON created_at >= ddate and created_at < ddate + ddays "
    );
    stringBuffer.append("GROUP BY ddate ORDER BY ddate");

    return jdbcTemplate.query(
      stringBuffer.toString(),
      new Object[] { pEnums.getPeriod(), pEnums.getPeriod() },
      (rs, rowNum) ->
        new ChartModel(
          rs.getDate("timestamp"),
          rs.getLong("delta"),
          rs.getLong("deltaTotal")
        )
    );
  }
}
