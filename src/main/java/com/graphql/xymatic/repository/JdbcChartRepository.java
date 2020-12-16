package com.graphql.xymatic.repository;

import com.graphql.xymatic.enums.PeriodEnums;
import com.graphql.xymatic.model.ChartModel;
import java.util.*;
import java.util.Vector;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcChartRepository implements ChartRepository {

  private static final Logger logger = LoggerFactory.getLogger(
    JdbcChartRepository.class
  );

  @Autowired
  public JdbcTemplate jdbcTemplate;

  private String coercedTimeInterval(PeriodEnums pEnums) {
    switch (pEnums) {
      case TODAY:
        {
          return "SELECT generate_series(date_trunc('day', current_date)::timestamp, current_date::timestamp, ?::interval) as ddate, ?::interval as ddays FROM";
        }
      case YESTERDAY:
        {
          return "SELECT generate_series((current_date - interval '1 day')::timestamp, current_date::timestamp, ?::interval) as ddate, ?::interval as ddays FROM";
        }
      case DAY:
        {
          return "SELECT generate_series(date_trunc('month', current_date)::timestamp, current_date::timestamp, ?::interval) as ddate, ?::interval as ddays FROM";
        }
      case MONTH:
        {
          return "SELECT generate_series(date_trunc('year', current_date)::timestamp, current_date::timestamp, ?::interval) as ddate, ?::interval as ddays FROM";
        }
      default:
        {
          return "SELECT generate_series(created_at::timestamp, current_date::timestamp, ?::interval) as ddate, ?::interval as ddays FROM";
        }
    }
  }

  @Override
  public List<ChartModel> findUserChart(PeriodEnums pEnums) {
    StringBuffer stringBuffer = new StringBuffer();

    ArrayList<String> queryBuilder = new ArrayList<String>();

    queryBuilder.add("WITH MOCKS AS");
    queryBuilder.add("(");
    queryBuilder.add(coercedTimeInterval(pEnums));
    queryBuilder.add("(");
    queryBuilder.add(
      "SELECT min(created_at) AS created_at FROM xt_users LIMIT 1"
    );
    queryBuilder.add(")");
    queryBuilder.add("AS SCHEMA");
    queryBuilder.add(")");
    queryBuilder.add(
      "SELECT ddate as timestamp, count(xt_users.*) as delta, sum(count(xt_users.*))"
    );
    queryBuilder.add("over (order by ddate) as deltaTotal");
    queryBuilder.add("FROM MOCKS LEFT JOIN xt_users");
    queryBuilder.add("ON created_at >= ddate and created_at < ddate + ddays");
    queryBuilder.add("GROUP BY ddate ORDER BY ddate");

    final Integer queryBuilderSize = queryBuilder.size();

    for (Integer _iQBS = 0; _iQBS < queryBuilderSize; _iQBS++) {
      stringBuffer.append(queryBuilder.get(_iQBS));
      if (_iQBS != queryBuilderSize - 1) {
        stringBuffer.append(" ");
      } else {
        stringBuffer.append(";");
      }
    }

    return jdbcTemplate.query(
      stringBuffer.toString(),
      new Object[] { pEnums.getPeriod(), pEnums.getPeriod() },
      (rs, rowNum) ->
        new ChartModel(
          rs.getTimestamp("timestamp"),
          rs.getLong("delta"),
          rs.getLong("deltaTotal")
        )
    );
  }
}
