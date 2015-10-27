package org.metadatacenter.ingestors.geo.metadb;

import org.metadatacenter.ingestors.geo.GEOIngestorException;
import org.metadatacenter.ingestors.geo.metadata.GEOMetadata;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GEOmetadbIngestor
{
  private final String databaseFilename;

  private static final String SERIES_SELECT = "SELECT * FROM " + GEOmetadbNames.SERIES_TABLE_NAME;
  private static final String SAMPLE_SELECT = "SELECT * FROM " + GEOmetadbNames.SAMPLE_TABLE_NAME;
  private static final String PLATFORM_SELECT = "SELECT * FROM " + GEOmetadbNames.PLATFORM_TABLE_NAME;

  public GEOmetadbIngestor(String databaseFilename)
  {
    this.databaseFilename = databaseFilename;
  }

  public GEOMetadata extractGEOMetadata() throws GEOIngestorException
  {
    registerJDBCDriver();

    try (Connection connection = DriverManager.getConnection(JDBC.PREFIX + databaseFilename);
        PreparedStatement seriesSelectStatement = connection.prepareStatement(SERIES_SELECT);
        PreparedStatement platformSelectStatement = connection.prepareStatement(PLATFORM_SELECT);
        PreparedStatement sampleSelectStatement = connection.prepareStatement(SAMPLE_SELECT)) {

      Map<String, Map<String, String>> seriesData = extractTableData(seriesSelectStatement,
          GEOmetadbNames.SERIES_TABLE_NAME, GEOmetadbNames.SERIES_TABLE_GSE_COLUMN_NAME,
          GEOmetadbNames.SeriesTableColumnNames);
      Map<String, Map<String, String>> platformData = extractTableData(platformSelectStatement,
          GEOmetadbNames.PLATFORM_TABLE_NAME, GEOmetadbNames.PLATFORM_TABLE_GPL_COLUMN_NAME,
          GEOmetadbNames.PlatformTableColumnNames);

      processSamplesTable(sampleSelectStatement, seriesData, platformData);

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

  private void processSamplesTable(PreparedStatement sampleSelectStatement, Map<String, Map<String, String>> seriesData,
      Map<String, Map<String, String>> platformData) throws SQLException, GEOIngestorException
  {
    ResultSet rs = sampleSelectStatement.executeQuery();

    int currentRowNumber = 1;
    while (rs.next()) {
      Map<String, String> sampleRow = new LinkedHashMap<>();
      for (String columnName : GEOmetadbNames.SampleTableColumnNames) {
        String value = rs.getString(columnName);
        if (value != null && !value.isEmpty())
          sampleRow.put(columnName, value);
      }
      String gsm = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_GSM_COLUMN_NAME, sampleRow,
          currentRowNumber);
      String gpl = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_GPL_COLUMN_NAME, sampleRow,
          currentRowNumber);
      String seriesID = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_SERIES_ID_COLUMN_NAME, sampleRow,
          currentRowNumber);

      String title = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_TITLE_COLUMN_NAME, sampleRow,
          currentRowNumber);
      String status = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_STATUS_COLUMN_NAME, sampleRow,
          currentRowNumber);
      String type = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_TYPE_COLUMN_NAME, sampleRow,
          currentRowNumber);

      Optional<String> channel1Source = getOptionalStringValueFromRow(
          GEOmetadbNames.SAMPLE_TABLE_SOURCE_NAME_CH1_COLUMN_NAME, sampleRow);
      Optional<String> channel1Characteristic = getOptionalStringValueFromRow(
          GEOmetadbNames.SAMPLE_TABLE_CHARACTERISTIC_CH1_COLUMN_NAME, sampleRow);
      Optional<String> channel1Molecule = getOptionalStringValueFromRow(
          GEOmetadbNames.SAMPLE_TABLE_MOLECULE_CH1_COLUMN_NAME, sampleRow);
      Optional<String> channel1Label = getOptionalStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_LABEL_CH1_COLUMN_NAME,
          sampleRow);
      Optional<String> channel1TreatmentProtocol = getOptionalStringValueFromRow(
          GEOmetadbNames.SAMPLE_TABLE_TREATMENT_PROTOCOL_CH1_COLUMN_NAME, sampleRow);
      Optional<String> channel1ExtractProtocol = getOptionalStringValueFromRow(
          GEOmetadbNames.SAMPLE_TABLE_EXTRACT_PROTOCOL_CH1_COLUMN_NAME, sampleRow);

      Optional<String> channel2Source = getOptionalStringValueFromRow(
          GEOmetadbNames.SAMPLE_TABLE_SOURCE_NAME_CH2_COLUMN_NAME, sampleRow);
      Optional<String> channel2Characteristic = getOptionalStringValueFromRow(
          GEOmetadbNames.SAMPLE_TABLE_CHARACTERISTIC_CH2_COLUMN_NAME, sampleRow);
      Optional<String> channel2Molecule = getOptionalStringValueFromRow(
          GEOmetadbNames.SAMPLE_TABLE_MOLECULE_CH2_COLUMN_NAME, sampleRow);
      Optional<String> channel2Label = getOptionalStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_LABEL_CH2_COLUMN_NAME,
          sampleRow);
      Optional<String> channel2TreatmentProtocol = getOptionalStringValueFromRow(
          GEOmetadbNames.SAMPLE_TABLE_TREATMENT_PROTOCOL_CH2_COLUMN_NAME, sampleRow);
      Optional<String> channel2ExtractProtocol = getOptionalStringValueFromRow(
          GEOmetadbNames.SAMPLE_TABLE_EXTRACT_PROTOCOL_CH2_COLUMN_NAME, sampleRow);

      Optional<String> hybProtocol = getOptionalStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_HYB_PROTOCOL_COLUMN_NAME,
          sampleRow);
      Optional<String> description = getOptionalStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_DESCRIPTION_COLUMN_NAME,
          sampleRow);
      Optional<String> dataProcessing = getOptionalStringValueFromRow(
          GEOmetadbNames.SAMPLE_TABLE_DATA_PROCESSING_COLUMN_NAME, sampleRow);
      Optional<String> contact = getOptionalStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_CONTACT_COLUMN_NAME,
          sampleRow);
      Optional<String> supplementaryFile = getOptionalStringValueFromRow(
          GEOmetadbNames.SAMPLE_TABLE_SUPPLEMENTARY_FILE_COLUMN_NAME, sampleRow);

      currentRowNumber++;
    }
    sampleSelectStatement.close();
    System.out.println(
        "Number of rows " + currentRowNumber + " in " + GEOmetadbNames.SAMPLE_TABLE_GSM_COLUMN_NAME + " table");
  }

  private String getRequiredStringValueFromRow(String columnName, Map<String, String> row, int rowNumber)
      throws GEOIngestorException
  {
    if (row.containsKey(columnName))
      return row.get(columnName);
    else
      throw new GEOIngestorException("missing value for required column " + columnName + " at row " + rowNumber);
  }

  private Optional<String> getOptionalStringValueFromRow(String columnName, Map<String, String> row)
      throws GEOIngestorException
  {
    if (row.containsKey(columnName))
      return Optional.of(row.get(columnName));
    else
      return Optional.empty();
  }

  private void f() throws GEOIngestorException
  {
    Map<String, String> seriesRow = null;
    int currentRowNumber = 1;
    String title = getRequiredStringValueFromRow(GEOmetadbNames.SERIES_TABLE_TITLE_COLUMN_NAME, seriesRow,
        currentRowNumber);
    String type = getRequiredStringValueFromRow(GEOmetadbNames.SERIES_TABLE_TYPE_COLUMN_NAME, seriesRow,
        currentRowNumber);
    Optional<String> contributor = getOptionalStringValueFromRow(GEOmetadbNames.SERIES_TABLE_CONTRIBUTOR_COLUMN_NAME,
        seriesRow);
    Optional<String> contact = getOptionalStringValueFromRow(GEOmetadbNames.SERIES_TABLE_CONTACT_COLUMN_NAME,
        seriesRow);
    Optional<String> status = getOptionalStringValueFromRow(GEOmetadbNames.SERIES_TABLE_STATUS_COLUMN_NAME, seriesRow);
    Optional<String> overallDesign = getOptionalStringValueFromRow(
        GEOmetadbNames.SERIES_TABLE_OVERALL_DESIGN_COLUMN_NAME, seriesRow);
    Optional<String> submissionDate = getOptionalStringValueFromRow(
        GEOmetadbNames.SERIES_TABLE_SUBMISSION_DATE_COLUMN_NAME, seriesRow);
    Optional<String> lastUpdateDate = getOptionalStringValueFromRow(
        GEOmetadbNames.SERIES_TABLE_LAST_UPDATE_DATE_COLUMN_NAME, seriesRow);
    Optional<String> pubmedID = getOptionalStringValueFromRow(GEOmetadbNames.SERIES_TABLE_PUBMED_ID_COLUMN_NAME,
        seriesRow);
    Optional<String> supplementaryFile = getOptionalStringValueFromRow(
        GEOmetadbNames.SERIES_TABLE_SUPPLEMENTARY_FILE_COLUMN_NAME, seriesRow);
  }

  private void g() throws GEOIngestorException
  {
    Map<String, String> platformRow = null;
    int currentRowNumber = 1;
    String title = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_TITLE_COLUMN_NAME, platformRow,
        currentRowNumber);
    String gpl = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_GPL_COLUMN_NAME, platformRow,
        currentRowNumber);
    Optional<String> status = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_STATUS_COLUMN_NAME,
        platformRow);
    Optional<String> submissionDate = getOptionalStringValueFromRow(
        GEOmetadbNames.PLATFORM_TABLE_SUBMISSION_DATE_COLUMN_NAME, platformRow);
    Optional<String> lastUpdateDate = getOptionalStringValueFromRow(
        GEOmetadbNames.PLATFORM_TABLE_LAST_UPDATE_DATE_COLUMN_NAME, platformRow);
    Optional<String> technology = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_TECHNOLOGY_COLUMN_NAME,
        platformRow);
    Optional<String> distribution = getOptionalStringValueFromRow(
        GEOmetadbNames.PLATFORM_TABLE_DISTRIBUTION_COLUMN_NAME, platformRow);
    Optional<String> organism = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_ORGANISM_COLUMN_NAME,
        platformRow);
    Optional<String> manufacturer = getOptionalStringValueFromRow(
        GEOmetadbNames.PLATFORM_TABLE_MANUFACTURER_COLUMN_NAME, platformRow);
    Optional<String> manufactureProtocol = getOptionalStringValueFromRow(
        GEOmetadbNames.PLATFORM_TABLE_MANUFACTURE_PROTOCOL_COLUMN_NAME, platformRow);
    Optional<String> coating = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_COATING_COLUMN_NAME,
        platformRow);
    Optional<String> catalogNumber = getOptionalStringValueFromRow(
        GEOmetadbNames.PLATFORM_TABLE_CATALOG_NUMBER_COLUMN_NAME, platformRow);
    Optional<String> support = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_SUPPORT_COLUMN_NAME,
        platformRow);
    Optional<String> description = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_DESCRIPTION_COLUMN_NAME,
        platformRow);
    Optional<String> webLink = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_WEB_LINK_COLUMN_NAME,
        platformRow);
    Optional<String> contact = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_CONTACT_COLUMN_NAME,
        platformRow);
    Optional<String> dataRowCount = getOptionalStringValueFromRow(
        GEOmetadbNames.PLATFORM_TABLE_DATA_ROW_COUNT_COLUMN_NAME, platformRow);
    Optional<String> supplementaryFile = getOptionalStringValueFromRow(
        GEOmetadbNames.PLATFORM_TABLE_SUPPLEMENTARY_FILE_COLUMN_NAME, platformRow);
    Optional<String> biocPackage = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_BIOC_PACKAGE_COLUMN_NAME,
        platformRow);

//    Platform p = new Platform(title, distribution, technology, organism, manufacturer, manufactureProtocol, description,
//            catalogNumber, webLink, support, coating, contributor, pubmedID);

  }

  // (key -> (column name -> column value))
  private Map<String, Map<String, String>> extractTableData(PreparedStatement selectStatement, String tableName,
      String keyColumnName, List<String> columnNames) throws SQLException, GEOIngestorException
  {
    Map<String, Map<String, String>> tableData = new HashMap<>();
    ResultSet rs = selectStatement.executeQuery();

    int currentRowNumber = 1;
    while (rs.next()) {
      String key = rs.getString(keyColumnName);

      if (key == null || key.isEmpty())
        throw new GEOIngestorException("empty " + keyColumnName + " at row " + rs.getRow());

      if (tableData.containsKey(key))
        throw new GEOIngestorException("duplicate entries for ID " + key);

      Map<String, String> row = new LinkedHashMap<>();
      for (String columnName : columnNames) {
        String value = rs.getString(columnName);
        if (value != null && !value.isEmpty())
          row.put(columnName, value);
      }
      tableData.put(key, row);
      currentRowNumber++;
    }
    selectStatement.close();
    System.out.println("Number of rows " + currentRowNumber + " in " + tableName + " table");

    return tableData;
  }

  //  private Series extractSeries(Sheet geoMetadataSheet) throws GEOIngestorException
  //  {
  //    Map<String, List<String>> seriesFields = extractSeriesFields(geoMetadataSheet);
  //    String title = getRequiredMultiValueFieldValue(seriesFields, SERIES_TITLE_FIELD_NAME, SERIES_HEADER_NAME);
  //    List<String> summary = getMultiValueFieldValues(seriesFields, SERIES_SUMMARY_FIELD_NAME);
  //    List<String> overallDesign = getMultiValueFieldValues(seriesFields, SERIES_OVERALL_DESIGN_FIELD_NAME);
  //    List<ContributorName> contributors = extractContributorNames(seriesFields, SERIES_PUBMED_ID_FIELD_NAME);
  //    List<String> pubmedIDs = getMultiValueFieldValues(seriesFields, SERIES_PUBMED_ID_FIELD_NAME);
  //    Map<String, Map<String, String>> variables = new HashMap<>(); // TODO
  //    Map<String, List<String>> repeat = new HashMap<>(); // TODO
  //
  //    return new Series(title, summary, overallDesign, contributors, pubmedIDs, variables, repeat);
  //  }

  private void series()
  {
    Map<String, String> row = null;

    if (row.containsKey(GEOmetadbNames.SERIES_TABLE_TITLE_COLUMN_NAME)) {
      //System.out.println("title " + row.get(GEOmetadbNames.SERIES_TABLE_TITLE_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SERIES_TABLE_CONTRIBUTOR_COLUMN_NAME)) {
      //System.out.println("cont " + row.get(GEOmetadbNames.SERIES_TABLE_CONTRIBUTOR_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SERIES_TABLE_CONTACT_COLUMN_NAME)) {
      // Semicolon separated. With Name, Email, Phone, Fax, Laboratory, Department, Institute, Address, City, State
      // Zip/postal-code, Country, Web_link
      //System.out.println("conta " + row.get(GEOmetadbNames.SERIES_TABLE_CONTACT_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SERIES_TABLE_STATUS_COLUMN_NAME)) {
      //System.out.println("status " + row.get(GEOmetadbNames.SERIES_TABLE_STATUS_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SERIES_TABLE_OVERALL_DESIGN_COLUMN_NAME)) {
      System.out.println("overall " + row.get(GEOmetadbNames.SERIES_TABLE_OVERALL_DESIGN_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SERIES_TABLE_SUBMISSION_DATE_COLUMN_NAME)) {
      //System.out.println("sub " + row.get(GEOmetadbNames.SERIES_TABLE_SUBMISSION_DATE_COLUMN_NAME));
    }
    if (row.containsKey(GEOmetadbNames.SERIES_TABLE_LAST_UPDATE_DATE_COLUMN_NAME)) {
      //System.out.println("lup " + row.get(GEOmetadbNames.SERIES_TABLE_LAST_UPDATE_DATE_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SERIES_TABLE_TYPE_COLUMN_NAME)) {
      // Semicolon separated
      //System.out.println("type " + row.get(GEOmetadbNames.SERIES_TABLE_TYPE_COLUMN_NAME));
    }
    if (row.containsKey(GEOmetadbNames.SERIES_TABLE_PUBMED_ID_COLUMN_NAME)) {
      //System.out.println("pubmedid " + row.get(GEOmetadbNames.SERIES_TABLE_PUBMED_ID_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SERIES_TABLE_SUPPLEMENTARY_FILE_COLUMN_NAME)) {
      // Semicolon separated
      //System.out.println("suppl " + row.get(GEOmetadbNames.SERIES_TABLE_SUPPLEMENTARY_FILE_COLUMN_NAME));
    }
  }

  private void samples()
  {
    Map<String, String> row = null;

    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_TITLE_COLUMN_NAME)) {
      //System.out.println("title " + row.get(GEOmetadbNames.SAMPLE_TABLE_TITLE_COLUMN_NAME));
    }
    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_STATUS_COLUMN_NAME)) {
      //System.out.println("status " + row.get(GEOmetadbNames.SAMPLE_TABLE_STATUS_COLUMN_NAME));
    }
    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_TYPE_COLUMN_NAME)) {
      //System.out.println("type " + row.get(GEOmetadbNames.SAMPLE_TABLE_TYPE_COLUMN_NAME));
      // [other, SRA, RNA, genomic, SARST, protein, MPSS, SAGE, mixed]
    }

    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_SOURCE_NAME_CH1_COLUMN_NAME)) {
      //System.out.println("source ch1 " + row.get(GEOmetadbNames.SAMPLE_TABLE_SOURCE_NAME_CH1_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_CHARACTERISTIC_CH1_COLUMN_NAME)) {
      // characteristic_name1: value1, value2; characteristic_name2: value1, value2;
      // Some have errors - no name: value at all. Some have http:// as field
      //System.out.println("char ch1-" + row.get(GEOmetadbNames.SAMPLE_TABLE_CHARACTERISTIC_CH1_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_MOLECULE_CH1_COLUMN_NAME)) {
      // [genomic DNA, other, total RNA, nuclear RNA, protein, cytoplasmic RNA, polyA RNA]
      //System.out.println("mol ch1-" + row.get(GEOmetadbNames.SAMPLE_TABLE_MOLECULE_CH1_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_LABEL_CH1_COLUMN_NAME)) {
      //System.out.println("label ch1-" + row.get(GEOmetadbNames.SAMPLE_TABLE_LABEL_CH1_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_TREATMENT_PROTOCOL_CH1_COLUMN_NAME)) {
      // Fairly free form
      //System.out.println("tprot ch1-" + row.get(GEOmetadbNames.SAMPLE_TABLE_TREATMENT_PROTOCOL_CH1_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_EXTRACT_PROTOCOL_CH1_COLUMN_NAME)) {
      //System.out.println("expr ch1-" + row.get(GEOmetadbNames.SAMPLE_TABLE_EXTRACT_PROTOCOL_CH1_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_GSM_COLUMN_NAME)) {
      //System.out.println("gsm " + row.get(GEOmetadbNames.SAMPLE_TABLE_GSM_COLUMN_NAME));
    }
    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_GPL_COLUMN_NAME)) {
      //System.out.println("gpl " + row.get(GEOmetadbNames.SAMPLE_TABLE_GPL_COLUMN_NAME));
    }
    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_SERIES_ID_COLUMN_NAME)) {
      //System.out.println("sid " + row.get(GEOmetadbNames.SAMPLE_TABLE_SERIES_ID_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_HYB_PROTOCOL_COLUMN_NAME)) {
      // Fairly free form
      //System.out.println("hyb " + row.get(GEOmetadbNames.SAMPLE_TABLE_HYB_PROTOCOL_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_DESCRIPTION_COLUMN_NAME)) {
      // Free form plus semi-colon separated lists of colon-separated attribute value pairs
      //System.out.println("desc " + row.get(GEOmetadbNames.SAMPLE_TABLE_DESCRIPTION_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_DATA_PROCESSING_COLUMN_NAME)) {
      // Free form
      //System.out.println("dprocess " + row.get(GEOmetadbNames.SAMPLE_TABLE_DATA_PROCESSING_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_CONTACT_COLUMN_NAME)) {
      // As per contact in series
      //System.out.println("cont " + row.get(GEOmetadbNames.SAMPLE_TABLE_CONTACT_COLUMN_NAME));
    }

    if (row.containsKey(GEOmetadbNames.SAMPLE_TABLE_SUPPLEMENTARY_FILE_COLUMN_NAME)) {
      // Often empty (not just null) but also has URLs
      System.out.println("supp-" + row.get(GEOmetadbNames.SAMPLE_TABLE_SUPPLEMENTARY_FILE_COLUMN_NAME));
    }
  }

  private void platform()
  {
    Map<String, String> row = null;

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_TITLE_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_GPL_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_STATUS_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_SUBMISSION_DATE_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_LAST_UPDATE_DATE_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_TECHNOLOGY_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_DISTRIBUTION_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_ORGANISM_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_MANUFACTURER_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_MANUFACTURE_PROTOCOL_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_COATING_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_CATALOG_NUMBER_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_SUPPORT_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_DESCRIPTION_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_WEB_LINK_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_CONTACT_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_DATA_ROW_COUNT_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_SUPPLEMENTARY_FILE_COLUMN_NAME)) {
    }

    if (row.containsKey(GEOmetadbNames.PLATFORM_TABLE_BIOC_PACKAGE_COLUMN_NAME)) {
    }
  }
}
