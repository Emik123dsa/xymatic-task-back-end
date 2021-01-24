package com.graphql.xymatic.repository;

import com.graphql.xymatic.model.RowsCountModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.lang.model.type.UnknownTypeException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRowsCountRepository implements RowsCountRepository {

  private final JdbcTemplate jdbcTemplate;

  private final Pattern TABLE_PATTERN = Pattern.compile(
    "^[(](\\w*)\\,\\s*?(\\d*)[)$]",
    Pattern.CASE_INSENSITIVE
  );

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(
    JdbcRowsCountRepository.class
  );

  @Autowired
  public JdbcRowsCountRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<RowsCountModel> countAllRows() {
    List<String> countAllTables = jdbcTemplate.query(
      "SELECT count_em_all() as rowsCount",
      (rs, rowNum) -> {
        logger.info(rs.toString());
        return rs.getString("rowsCount");
      }
    );

    List<RowsCountModel> countCoercedTables = countAllTables
      .stream()
      .filter(s -> !s.isEmpty())
      .map(TABLE_PATTERN::matcher)
      .filter(Matcher::find)
      .map(
        matcher -> {
          RowsCountModel rowsCountModel = new RowsCountModel();
          try {
            logger.info(
              "Matching element has been identified: " + matcher.toString()
            );
            rowsCountModel.setType(matcher.group(1));
            rowsCountModel.setCount(Integer.parseInt(matcher.group(2)));
          } catch (UnknownTypeException exception) {
            exception.printStackTrace(System.out);
          }

          return rowsCountModel;
        }
      )
      .collect(Collectors.toList());

    return countCoercedTables;
  }
}
