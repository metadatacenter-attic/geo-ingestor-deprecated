package org.metadatacenter.ingestors.geo.metadb;

import org.metadatacenter.ingestors.geo.GEOIngestorException;
import org.metadatacenter.ingestors.geo.metadata.Contributor;
import org.metadatacenter.ingestors.geo.metadata.GEOMetadata;
import org.metadatacenter.ingestors.geo.metadata.PerChannelSampleInfo;
import org.metadatacenter.ingestors.geo.metadata.Platform;
import org.metadatacenter.ingestors.geo.metadata.Series;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A GEOmetadb database contains a full dump of GEO metadata. Copies of this database, which is in SQLite format,
 * can be found at http://gbnci.abcc.ncifcrf.gov/geo/. Typically the file is called GEOmetadb.sqlite.
 */
public class GEOmetadbIngestor
{
  private final String sqliteDatabaseFilename;

  private static final String SERIES_SELECT = "SELECT * FROM " + GEOmetadbNames.SERIES_TABLE_NAME;
  private static final String SAMPLE_SELECT = "SELECT * FROM " + GEOmetadbNames.SAMPLE_TABLE_NAME;
  private static final String PLATFORM_SELECT = "SELECT * FROM " + GEOmetadbNames.PLATFORM_TABLE_NAME;

  public GEOmetadbIngestor(String sqliteDatabaseFilename)
  {
    this.sqliteDatabaseFilename = sqliteDatabaseFilename;
  }

  public GEOMetadata extractGEOMetadata() throws GEOIngestorException
  {
    registerJDBCDriver();

    try (Connection connection = DriverManager.getConnection(JDBC.PREFIX + sqliteDatabaseFilename);
      PreparedStatement seriesSelectStatement = connection.prepareStatement(SERIES_SELECT);
      PreparedStatement platformSelectStatement = connection.prepareStatement(PLATFORM_SELECT);
      PreparedStatement sampleSelectStatement = connection.prepareStatement(SAMPLE_SELECT)) {

      Map<String, Map<String, String>> seriesRows = extractTableRows(seriesSelectStatement,
        GEOmetadbNames.SERIES_TABLE_NAME, GEOmetadbNames.SERIES_TABLE_GSE_COLUMN_NAME,
        GEOmetadbNames.SeriesTableColumnNames);
      Map<String, Map<String, String>> platformRows = extractTableRows(platformSelectStatement,
        GEOmetadbNames.PLATFORM_TABLE_NAME, GEOmetadbNames.PLATFORM_TABLE_GPL_COLUMN_NAME,
        GEOmetadbNames.PlatformTableColumnNames);

      processSamplesTable(sampleSelectStatement, seriesRows, platformRows);

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

  /**
   * @param sampleSelectStatement
   * @param seriesRows            gse -> series row
   * @param platformRows          gpl -> platform row
   * @throws SQLException
   * @throws GEOIngestorException
   */
  private void processSamplesTable(PreparedStatement sampleSelectStatement, Map<String, Map<String, String>> seriesRows,
    Map<String, Map<String, String>> platformRows) throws SQLException, GEOIngestorException
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

      extractSampleFromRow(sampleRow, currentRowNumber);

      currentRowNumber++;
    }
    sampleSelectStatement.close();
    System.out
      .println("Number of rows " + currentRowNumber + " in " + GEOmetadbNames.SAMPLE_TABLE_GSM_COLUMN_NAME + " table");
  }

  private void extractSampleFromRow(Map<String, String> sampleRow, int currentRowNumber) throws GEOIngestorException
  {
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

    String channel1Source = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_SOURCE_NAME_CH1_COLUMN_NAME,
      sampleRow, currentRowNumber);
    Optional<String> channel1RawCharacteristic = getOptionalStringValueFromRow(
      GEOmetadbNames.SAMPLE_TABLE_CHARACTERISTIC_CH1_COLUMN_NAME, sampleRow);
    Map<String, String> channel1Characteristics = new HashMap<>(); // TODO
    String channel1Molecule = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_MOLECULE_CH1_COLUMN_NAME,
      sampleRow, currentRowNumber);
    String channel1Label = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_LABEL_CH1_COLUMN_NAME, sampleRow,
      currentRowNumber);
    Optional<String> channel1TreatmentProtocol = getOptionalStringValueFromRow(
      GEOmetadbNames.SAMPLE_TABLE_TREATMENT_PROTOCOL_CH1_COLUMN_NAME, sampleRow);
    Optional<String> channel1ExtractProtocol = getOptionalStringValueFromRow(
      GEOmetadbNames.SAMPLE_TABLE_EXTRACT_PROTOCOL_CH1_COLUMN_NAME, sampleRow);

    String channel2Source = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_SOURCE_NAME_CH2_COLUMN_NAME,
      sampleRow, currentRowNumber);
    Optional<String> channel2RawCharacteristic = getOptionalStringValueFromRow(
      GEOmetadbNames.SAMPLE_TABLE_CHARACTERISTIC_CH2_COLUMN_NAME, sampleRow);
    Map<String, String> channel2Characteristics = new HashMap<>(); // TODO
    String channel2Molecule = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_MOLECULE_CH2_COLUMN_NAME,
      sampleRow, currentRowNumber);
    String channel2Label = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_LABEL_CH2_COLUMN_NAME, sampleRow,
      currentRowNumber);
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
    List<String> rawDataFiles = new ArrayList<>(); // TODO semi-colon separated
    List<String> organisms = new ArrayList<>();// TODO

    Map<Integer, PerChannelSampleInfo> perChannelInformation = new HashMap<>();
    PerChannelSampleInfo channel1SampleInfo = new PerChannelSampleInfo(1, channel1Source, organisms,
      channel1Characteristics, channel1Molecule, channel1Label);
    PerChannelSampleInfo channel2SampleInfo = new PerChannelSampleInfo(2, channel2Source, organisms,
      channel2Characteristics, channel2Molecule, channel2Label);
    perChannelInformation.put(1, channel1SampleInfo);
    perChannelInformation.put(2, channel2SampleInfo);

    // GEOmetadbNames.SAMPLE_TABLE_TYPE_COLUMN_NAME)) {
    // [other, SRA, RNA, genomic, SARST, protein, MPSS, SAGE, mixed]

    //GEOmetadbNames.SAMPLE_TABLE_CHARACTERISTIC_CH1_COLUMN_NAME)) {
    // characteristic_name1: value1, value2; characteristic_name2: value1, value2;
    // Some have errors - no name: value at all. Some have http:// as field
    // GEOmetadbNames.SAMPLE_TABLE_MOLECULE_CH1_COLUMN_NAME)) {
    // [genomic DNA, other, total RNA, nuclear RNA, protein, cytoplasmic RNA, polyA RNA]
    // GEOmetadbNames.SAMPLE_TABLE_DESCRIPTION_COLUMN_NAME)) {
    // Free form plus semi-colon separated lists of colon-separated attribute value pairs
    // GEOmetadbNames.SAMPLE_TABLE_CONTACT_COLUMN_NAME)) {
    // As per contact in series
    // GEOmetadbNames.SAMPLE_TABLE_SUPPLEMENTARY_FILE_COLUMN_NAME)) {
    // Often empty (not just null) but also has URLs

    //      Sample(gsm, title, label, description, platform, perChannelInformation, biomaterialProvider,
    //          rawDataFiles, Optional.empty(), Optional.empty(), Optional.empty())

  }

  private Series extractSeriesFromRow(Map<String, String> seriesRow, int currentRowNumber) throws GEOIngestorException
  {
    String title = getRequiredStringValueFromRow(GEOmetadbNames.SERIES_TABLE_TITLE_COLUMN_NAME, seriesRow,
      currentRowNumber);
    String summary = getRequiredStringValueFromRow(GEOmetadbNames.SERIES_TABLE_SUMMARY_COLUMN_NAME, seriesRow,
      currentRowNumber);
    String overallDesign = getRequiredStringValueFromRow(GEOmetadbNames.SERIES_TABLE_OVERALL_DESIGN_COLUMN_NAME,
      seriesRow, currentRowNumber);
    Optional<String> contributor = getOptionalStringValueFromRow(GEOmetadbNames.SERIES_TABLE_CONTRIBUTOR_COLUMN_NAME,
      seriesRow);
    List<Contributor> contributors = contributor.isPresent() ?
      extractContributors(contributor.get()) :
      Collections.emptyList();
    Optional<String> webLink = getOptionalStringValueFromRow(GEOmetadbNames.SERIES_TABLE_WEB_LINK_COLUMN_NAME,
      seriesRow);
    String type = getRequiredStringValueFromRow(GEOmetadbNames.SERIES_TABLE_TYPE_COLUMN_NAME, seriesRow,
      currentRowNumber);
    Optional<String> status = getOptionalStringValueFromRow(GEOmetadbNames.SERIES_TABLE_STATUS_COLUMN_NAME, seriesRow);
    Optional<String> submissionDate = getOptionalStringValueFromRow(
      GEOmetadbNames.SERIES_TABLE_SUBMISSION_DATE_COLUMN_NAME, seriesRow);
    Optional<String> lastUpdateDate = getOptionalStringValueFromRow(
      GEOmetadbNames.SERIES_TABLE_LAST_UPDATE_DATE_COLUMN_NAME, seriesRow);
    Optional<String> contact = getOptionalStringValueFromRow(GEOmetadbNames.SERIES_TABLE_CONTACT_COLUMN_NAME,
      seriesRow);
    Optional<String> pubmedID = getOptionalStringValueFromRow(GEOmetadbNames.SERIES_TABLE_PUBMED_ID_COLUMN_NAME,
      seriesRow);
    List<String> pubmedIDs = pubmedID.isPresent() ? Collections.singletonList(pubmedID.get()) : Collections.emptyList();
    Optional<String> repeats = getOptionalStringValueFromRow(GEOmetadbNames.SERIES_TABLE_REPEATS_COLUMN_NAME,
      seriesRow);
    Optional<String> repeatsSamples = getOptionalStringValueFromRow(
      GEOmetadbNames.SERIES_TABLE_REPEATS_SAMPLE_LIST_COLUMN_NAME, seriesRow);
    Optional<String> variable = getOptionalStringValueFromRow(GEOmetadbNames.SERIES_TABLE_VARIABLE_COLUMN_NAME,
      seriesRow);
    Map<String, Map<String, String>> variables = variable.isPresent() ?
      Collections.emptyMap() :
      extractVariables(variable.get());
    Optional<String> variableDescription = getOptionalStringValueFromRow(
      GEOmetadbNames.SERIES_TABLE_VARIABLE_DESCRIPTION_COLUMN_NAME, seriesRow);
    Optional<String> supplementaryFile = getOptionalStringValueFromRow(
      GEOmetadbNames.SERIES_TABLE_SUPPLEMENTARY_FILE_COLUMN_NAME, seriesRow);

    // TODO Use: type (comma separated), webLink, status, submissionDate, lastUpdateDate, contact,
    // supplementaryFile (comma separated), repeats
    return new Series(title, Collections.singletonList(summary), Collections.singletonList(overallDesign), contributors,
      pubmedIDs, variables, Collections.emptyMap());
  }

  /**
   * @param variableString The raw string containing the
   * @return (gsm -> (variable name -> variable value))
   */
  private Map<String, Map<String, String>> extractVariables(String variableString)
  {
    Map<String, Map<String, String>> variables = new HashMap<>();

    // TODO

    return variables;
  }

  private Platform extractPlatformFromRow(Map<String, String> platformRow, int currentRowNumber)
    throws GEOIngestorException
  {
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
    String technology = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_TECHNOLOGY_COLUMN_NAME, platformRow,
      currentRowNumber);
    String distribution = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_DISTRIBUTION_COLUMN_NAME,
      platformRow, currentRowNumber);
    String organism = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_ORGANISM_COLUMN_NAME, platformRow,
      currentRowNumber);
    String manufacturer = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_MANUFACTURER_COLUMN_NAME,
      platformRow, currentRowNumber);
    String manufactureProtocol = getRequiredStringValueFromRow(
      GEOmetadbNames.PLATFORM_TABLE_MANUFACTURE_PROTOCOL_COLUMN_NAME, platformRow, currentRowNumber);
    Optional<String> coating = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_COATING_COLUMN_NAME,
      platformRow);
    Optional<String> catalogNumber = getOptionalStringValueFromRow(
      GEOmetadbNames.PLATFORM_TABLE_CATALOG_NUMBER_COLUMN_NAME, platformRow);
    Optional<String> support = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_SUPPORT_COLUMN_NAME,
      platformRow);
    String description = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_DESCRIPTION_COLUMN_NAME,
      platformRow, currentRowNumber);
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

    // TODO Use: status, submissionDate, lastUpdateDate, contact, dataRowCount, supplementaryFile, biocPackage
    return new Platform(title, distribution, technology, organism, manufacturer,
      Collections.singletonList(manufactureProtocol), Collections.singletonList(description), catalogNumber, webLink,
      support, coating, Collections.emptyList(), Collections.emptyList());
  }

  /**
   * @param selectStatement      The prepared statement to extract all rows from the table
   * @param tableName            The table name
   * @param primaryKeyColumnName The primary key column
   * @param columnNames          All relevant columns in the table
   * @return (key -> (column name -> column value))
   * @throws SQLException         If a SQL error error occurs
   * @throws GEOIngestorException If an ingestor error occurs
   */
  private Map<String, Map<String, String>> extractTableRows(PreparedStatement selectStatement, String tableName,
    String primaryKeyColumnName, List<String> columnNames) throws SQLException, GEOIngestorException
  {
    Map<String, Map<String, String>> tableData = new HashMap<>();
    ResultSet rs = selectStatement.executeQuery();

    int currentRowNumber = 1;
    while (rs.next()) {
      String key = rs.getString(primaryKeyColumnName);

      if (key == null || key.isEmpty())
        throw new GEOIngestorException(
          "empty or missing primary key " + primaryKeyColumnName + " at row " + rs.getRow());

      if (tableData.containsKey(key))
        throw new GEOIngestorException("duplicate entries for primary key " + key);

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

  /**
   * @param contributorsString
   * @return
   */
  private List<Contributor> extractContributors(String contributorsString)
  {
    List<Contributor> contributors = new ArrayList<>();

    for (String contributorString : Arrays.stream(contributorsString.split(",")).map(String::trim)
      .filter(s -> !s.isEmpty()).toArray(String[]::new)) {
      Map<String, String> fieldValues = new HashMap<>();
      for (String field : Arrays.stream(contributorString.split(",")).map(String::trim).filter(s -> !s.isEmpty())
        .toArray(String[]::new)) {
        String attributeValue[] = field.split(":");
        if (attributeValue.length == 2) {
          String fieldName = attributeValue[0].trim();
          String fieldValue = attributeValue[1].trim();
          if (GEOmetadbNames.ContactFieldNames.contains(fieldName) && !fieldValue.isEmpty())
            fieldValues.put(fieldName, fieldValue);
        }
      }
      if (!fieldValues.isEmpty()) {
        String name = getOrElse(fieldValues, GEOmetadbNames.NAME_CONTACT_FIELD, "");
        String email = getOrElse(fieldValues, GEOmetadbNames.EMAIL_CONTACT_FIELD, "");
        String phone = getOrElse(fieldValues, GEOmetadbNames.PHONE_CONTACT_FIELD, "");
        String fax = getOrElse(fieldValues, GEOmetadbNames.FAX_CONTACT_FIELD, "");
        String laboratory = getOrElse(fieldValues, GEOmetadbNames.LABORATORY_CONTACT_FIELD, "");
        String department = getOrElse(fieldValues, GEOmetadbNames.DEPARTMENT_CONTACT_FIELD, "");
        String institute = getOrElse(fieldValues, GEOmetadbNames.INSTITUTE_CONTACT_FIELD, "");
        String address = getOrElse(fieldValues, GEOmetadbNames.ADDRESS_CONTACT_FIELD, "");
        String city = getOrElse(fieldValues, GEOmetadbNames.CITY_CONTACT_FIELD, "");
        String state = getOrElse(fieldValues, GEOmetadbNames.STATE_CONTACT_FIELD, "");
        String zipOrPostalCode = getOrElse(fieldValues, GEOmetadbNames.ZIP_POSTAL_CODE_CONTACT_FIELD, "");
        String country = getOrElse(fieldValues, GEOmetadbNames.COUNTRY_CONTACT_FIELD, "");
        String webLink = getOrElse(fieldValues, GEOmetadbNames.WEB_LINK_CONTACT_FIELD, "");
        Contributor contributor = new Contributor(name, email, phone, fax, laboratory, department, institute, address,
          city, state, zipOrPostalCode, country, webLink);

        contributors.add(contributor);
      }
    }
    return contributors;
  }

  private String getOrElse(Map<String, String> fieldValues, String fieldName, String defaultValue)
  {
    if (fieldValues.containsKey(fieldName))
      return fieldValues.get(fieldName);
    else
      return defaultValue;
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
}