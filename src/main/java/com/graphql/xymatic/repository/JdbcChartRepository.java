package com.graphql.xymatic.repository;

import com.graphql.xymatic.enums.PeriodEnums;
import com.graphql.xymatic.enums.TableEnums;
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

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public JdbcChartRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private String coercedTimeInterval(PeriodEnums pEnums) {
    switch (pEnums) {
      case TODAY:
        {
          return "SELECT generate_series(date_trunc('day', current_timestamp)::timestamp, current_timestamp::timestamp, ?::interval) as ddate, ?::interval as ddays FROM";
        }
      case YESTERDAY:
        {
          return "SELECT generate_series(date_trunc('day', current_timestamp - interval '1 day')::timestamp, date_trunc('day', current_timestamp), ?::interval) as ddate, ?::interval as ddays FROM";
        }
      case DAY:
        {
          return "SELECT generate_series(date_trunc('month', current_timestamp)::timestamp, current_date::timestamp, ?::interval) as ddate, ?::interval as ddays FROM";
        }
      case MONTH:
        {
          return "SELECT generate_series(date_trunc('year', current_date)::timestamp, current_date::timestamp, ?::interval) as ddate, ?::interval as ddays FROM";
        }
      case YEAR:
        {
          return "SELECT generate_series(date_trunc('year', current_date), current_date::timestamp, ?::interval) as ddate, ?::interval as ddays FROM";
        }
      case ALL:
        {
          return "SELECT generate_series(date_trunc('year', created_at)::timestamp, current_date::timestamp, ?::interval) as ddate, ?::interval as ddays FROM";
        }
      default:
        {
          return "SELECT generate_series(date_trunc('year', created_at)::timestamp, current_timestamp::timestamp, ?::interval) as ddate, ?::interval as ddays FROM";
        }
    }
  }

  private final String queryBuilder(String table, PeriodEnums pEnums) {
    StringBuffer stringBuffer = new StringBuffer();

    ArrayList<String> queryBuilder = new ArrayList<String>();

    queryBuilder.add("WITH MOCKS AS");
    queryBuilder.add("(");
    queryBuilder.add(coercedTimeInterval(pEnums));
    queryBuilder.add("(");
    queryBuilder.add("SELECT min(created_at) AS created_at FROM");
    queryBuilder.add(table);
    queryBuilder.add("LIMIT 1");
    queryBuilder.add(")");
    queryBuilder.add("AS SCHEMA");
    queryBuilder.add(")");
    queryBuilder.add("SELECT ddate as timestamp,");
    queryBuilder.add("count(");
    queryBuilder.add(table);
    queryBuilder.add(".*) as delta, sum(count(");
    queryBuilder.add(table);
    queryBuilder.add(".*))");
    queryBuilder.add("over (order by ddate) as deltaTotal");
    queryBuilder.add("FROM MOCKS LEFT JOIN");
    queryBuilder.add(table);
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

    return stringBuffer.toString();
  }

  @Override
  public List<ChartModel> findUserChart(PeriodEnums pEnums) {
    return jdbcTemplate.query(
      queryBuilder(TableEnums.USERS.getTable(), pEnums),
      new Object[] { pEnums.getPeriod(), pEnums.getPeriod() },
      (rs, rowNum) ->
        new ChartModel(
          rs.getTimestamp("timestamp"),
          rs.getLong("delta"),
          rs.getLong("deltaTotal")
        )
    );
  }

  @Override
  public List<ChartModel> findPostChart(PeriodEnums pEnums) {
    return jdbcTemplate.query(
      queryBuilder(TableEnums.POSTS.getTable(), pEnums),
      new Object[] { pEnums.getPeriod(), pEnums.getPeriod() },
      (rs, rowNum) ->
        new ChartModel(
          rs.getTimestamp("timestamp"),
          rs.getLong("delta"),
          rs.getLong("deltaTotal")
        )
    );
  }

  @Override
  public List<ChartModel> findPlayChart(PeriodEnums pEnums) {
    return jdbcTemplate.query(
      queryBuilder(TableEnums.PLAYS.getTable(), pEnums),
      new Object[] { pEnums.getPeriod(), pEnums.getPeriod() },
      (rs, rowNum) ->
        new ChartModel(
          rs.getTimestamp("timestamp"),
          rs.getLong("delta"),
          rs.getLong("deltaTotal")
        )
    );
  }

  @Override
  public List<ChartModel> findImpressionsChart(PeriodEnums pEnums) {
    return jdbcTemplate.query(
      queryBuilder(TableEnums.IMPRESSIONS.getTable(), pEnums),
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
