package org.metadatacenter.ingestors.geo.metadb;

import org.metadatacenter.ingestors.geo.GEOIngestorException;
import org.metadatacenter.ingestors.geo.metadata.GEOMetadata;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

public class GEOmetadbIngestor
{
  private final String databaseFilename;

  public GEOmetadbIngestor(String databaseFilename)
  {
    this.databaseFilename = databaseFilename;
  }

  public GEOMetadata extractGEOMetadata() throws GEOIngestorException
  {
    registerJDBCDriver();

    try (Connection connection = DriverManager.getConnection(JDBC.PREFIX + databaseFilename);
      Statement statement = connection.createStatement()) {

      ResultSet rs = statement.executeQuery("select * from " + GEOmetadbNames.SERIES_TABLE_NAME);

      //      public static final String SERIES_TABLE_REPEATS_COLUMN_NAME = "repeats";
      //      public static final String SERIES_TABLE_REPEATS_SAMPLE_LIST_COLUMN_NAME = "repeats_sample_list";
      //      public static final String SERIES_TABLE_VARIABLE_COLUMN_NAME = "variable";
      //      public static final String SERIES_TABLE_VARIABLE_DESCRIPTION_COLUMN_NAME = "variable_description";

      while (rs.next()) {
        Map<String, String> row = new LinkedHashMap<>();
        for (String columnName : GEOmetadbNames.SeriesTableColumnNames) {
          String value = rs.getString(columnName);
          if (value != null)
            row.put(columnName, value);
        }
        System.out.println("repeats " + row.get(GEOmetadbNames.SERIES_TABLE_REPEATS_COLUMN_NAME));
        System.out.println("repeats_sample_list " + row.get(GEOmetadbNames.SERIES_TABLE_REPEATS_SAMPLE_LIST_COLUMN_NAME));
        System.out.println("variable " + row.get(GEOmetadbNames.SERIES_TABLE_VARIABLE_COLUMN_NAME));
        System.out.println("repeats " + row.get(GEOmetadbNames.SERIES_TABLE_REPEATS_COLUMN_NAME));
      }

    } catch (SQLException e) {
      throw new GEOIngestorException("database error: " + e.getMessage());
    }
    return null; // TODO
  }

  private void registerJDBCDriver() throws GEOIngestorException
  {
    try {
      DriverManager.registerDriver(new org.sqlite.JDBC());
    } catch (SQLException e) {
      throw new GEOIngestorException("error registering JDBC driver: " + e.getMessage());
    }
  }
}
