package org.metadatacenter.ingestors.geo.metadb;

import org.metadatacenter.ingestors.geo.GEOIngestorException;
import org.metadatacenter.ingestors.geo.metadata.Contributor;
import org.metadatacenter.ingestors.geo.metadata.GEOMetadata;
import org.metadatacenter.ingestors.geo.metadata.PerChannelSampleInfo;
import org.metadatacenter.ingestors.geo.metadata.Platform;
import org.metadatacenter.ingestors.geo.metadata.Sample;
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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * A GEOmetadb database contains a full dump of GEO metadata. Copies of this database, which is in SQLite format,
 * can be found at http://gbnci.abcc.ncifcrf.gov/geo/. Typically the file is called GEOmetadb.sqlite.
 * </p>
 * A description of GETmetadb tables can be found at http://gbnci.abcc.ncifcrf.gov/geo/geo_help.php.
 */
public class GEOmetadbIngestor
{
  private final String sqliteDatabaseFilename;

  private static final String SERIES_SELECT = "SELECT * FROM " + GEOmetadbNames.SERIES_TABLE_NAME + " LIMIT ?";
  private static final String SAMPLES_SELECT =
    "SELECT * FROM " + GEOmetadbNames.SAMPLE_TABLE_NAME + " WHERE series_id = ?";
  private static final String PLATFORM_SELECT =
    "SELECT * FROM " + GEOmetadbNames.PLATFORM_TABLE_NAME + " WHERE gpl = ?";

  public GEOmetadbIngestor(String sqliteDatabaseFilename)
  {
    this.sqliteDatabaseFilename = sqliteDatabaseFilename;
  }

  public GEOMetadata extractGEOMetadata(int maximumNumberOfSeries) throws GEOIngestorException
  {
    registerJDBCDriver();

    try (Connection connection = DriverManager.getConnection(JDBC.PREFIX + sqliteDatabaseFilename);
      PreparedStatement seriesSelectStatement = connection.prepareStatement(SERIES_SELECT)) {

      seriesSelectStatement.setInt(1, maximumNumberOfSeries);

      List<Map<String, String>> seriesRows = extractTableRows(seriesSelectStatement, GEOmetadbNames.SERIES_TABLE_NAME,
        GEOmetadbNames.SERIES_TABLE_GSE_COLUMN_NAME, GEOmetadbNames.SeriesTableColumnNames);

      int currentRowNumber = 1;
      for (Map<String, String> seriesRow : seriesRows) {

        if (!seriesRow.containsKey(GEOmetadbNames.SERIES_TABLE_GSE_COLUMN_NAME))
          throw new GEOIngestorException("Internal error: no column " + GEOmetadbNames.SERIES_TABLE_GSE_COLUMN_NAME +
            " in " + GEOmetadbNames.SERIES_TABLE_NAME);

        String gse = seriesRow.get(GEOmetadbNames.SERIES_TABLE_GSE_COLUMN_NAME);
        PreparedStatement seriesSamplesSelectStatement = connection.prepareStatement(SAMPLES_SELECT);
        seriesSamplesSelectStatement.setString(1, gse);

        Map<String, Sample> geoSamples = extractGEOSamplesForSeries(seriesSamplesSelectStatement);

        for (String gsm : geoSamples.keySet()) {
          Sample geoSample = geoSamples.get(gsm);
          String gpl = geoSample.getPlatform();

          if (gpl.isEmpty())
            throw new GEOIngestorException("No platform specified in GEO series " + gsm);

          PreparedStatement platformSelectStatement = connection.prepareStatement(PLATFORM_SELECT);

          platformSelectStatement.setString(1, gpl);

          List<Map<String, String>> platformRows = extractTableRows(platformSelectStatement,
            GEOmetadbNames.PLATFORM_TABLE_NAME, GEOmetadbNames.PLATFORM_TABLE_GPL_COLUMN_NAME,
            GEOmetadbNames.PlatformTableColumnNames);

          if (platformRows.isEmpty())
            throw new GEOIngestorException("No platform with GPL " + gpl + " found for series " + gse);
          else if (platformRows.size() > 1)
            throw new GEOIngestorException("Duplicate platform with GPL " + gpl + " found for series " + gse);

          Series geoSeries = extractGEOSeriesFromRow(seriesRow, currentRowNumber);
          Platform geoPlatform = extractGEOPlatformFromRow(platformRows.get(0));

          GEOMetadata getMetadata = new GEOMetadata(geoSeries, geoSamples, Optional.empty(), Optional.of(geoPlatform));
        }
        seriesSamplesSelectStatement.close();
        currentRowNumber++;
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

  /**
   * @param seriesSamplesSelectStatement
   * @throws SQLException
   * @throws GEOIngestorException
   */
  private Map<String, Sample> extractGEOSamplesForSeries(PreparedStatement seriesSamplesSelectStatement)
    throws SQLException, GEOIngestorException
  {
    Map<String, Sample> geoSamples = new HashMap<>();
    ResultSet rs = seriesSamplesSelectStatement.executeQuery();

    int currentRowNumber = 1;
    while (rs.next()) {
      Map<String, String> sampleRow = new LinkedHashMap<>();
      for (String columnName : GEOmetadbNames.SampleTableColumnNames) {
        String value = rs.getString(columnName).trim();
        if (value != null && !value.isEmpty())
          sampleRow.put(columnName, value);
      }
      if (sampleRow.containsKey(GEOmetadbNames.SAMPLE_TABLE_SERIES_ID_COLUMN_NAME)) {
        String gsm = sampleRow.get(GEOmetadbNames.SAMPLE_TABLE_SERIES_ID_COLUMN_NAME);
        Sample geoSample = extractGEOSampleFromRow(sampleRow, currentRowNumber);
        geoSamples.put(gsm, geoSample);
      } else
        throw new GEOIngestorException(
          "Missing value in " + GEOmetadbNames.SAMPLE_TABLE_SERIES_ID_COLUMN_NAME + " column of table "
            + GEOmetadbNames.SAMPLE_TABLE_NAME);
      currentRowNumber++;
    }

    System.out.println("" + currentRowNumber + " row(s) in " + GEOmetadbNames.SAMPLE_TABLE_GSM_COLUMN_NAME + " table");

    return geoSamples;
  }

  private Series extractGEOSeriesFromRow(Map<String, String> seriesRow, int currentRowNumber)
    throws GEOIngestorException
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

  private Sample extractGEOSampleFromRow(Map<String, String> sampleRow, int currentRowNumber)
    throws GEOIngestorException
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
    String typesString = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_TYPE_COLUMN_NAME, sampleRow,
      currentRowNumber);
    Set<String> types = extractTypes(typesString);

    String channel1Source = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_SOURCE_NAME_CH1_COLUMN_NAME,
      sampleRow, currentRowNumber);
    Optional<String> channel1RawCharacteristic = getOptionalStringValueFromRow(
      GEOmetadbNames.SAMPLE_TABLE_CHARACTERISTIC_CH1_COLUMN_NAME, sampleRow);

    Map<String, String> channel1Characteristics = extractCharacteristics(channel1RawCharacteristic);
    String channel1Molecule = getRequiredStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_MOLECULE_CH1_COLUMN_NAME,
      sampleRow, currentRowNumber);
    String channel1Label = getStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_LABEL_CH1_COLUMN_NAME, sampleRow);
    Optional<String> channel1TreatmentProtocol = getOptionalStringValueFromRow(
      GEOmetadbNames.SAMPLE_TABLE_TREATMENT_PROTOCOL_CH1_COLUMN_NAME, sampleRow);
    Optional<String> channel1ExtractProtocol = getOptionalStringValueFromRow(
      GEOmetadbNames.SAMPLE_TABLE_EXTRACT_PROTOCOL_CH1_COLUMN_NAME, sampleRow);

    String channel2Source = getStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_SOURCE_NAME_CH2_COLUMN_NAME, sampleRow);
    Optional<String> channel2RawCharacteristic = getOptionalStringValueFromRow(
      GEOmetadbNames.SAMPLE_TABLE_CHARACTERISTIC_CH2_COLUMN_NAME, sampleRow);
    Map<String, String> channel2Characteristics = extractCharacteristics(channel2RawCharacteristic);
    String channel2Molecule = getStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_MOLECULE_CH2_COLUMN_NAME, sampleRow);
    String channel2Label = getStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_LABEL_CH2_COLUMN_NAME, sampleRow);
    Optional<String> channel2TreatmentProtocol = getOptionalStringValueFromRow(
      GEOmetadbNames.SAMPLE_TABLE_TREATMENT_PROTOCOL_CH2_COLUMN_NAME, sampleRow);
    Optional<String> channel2ExtractProtocol = getOptionalStringValueFromRow(
      GEOmetadbNames.SAMPLE_TABLE_EXTRACT_PROTOCOL_CH2_COLUMN_NAME, sampleRow);

    Optional<String> hybProtocol = getOptionalStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_HYB_PROTOCOL_COLUMN_NAME,
      sampleRow);
    String description = getStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_DESCRIPTION_COLUMN_NAME, sampleRow);
    Optional<String> dataProcessing = getOptionalStringValueFromRow(
      GEOmetadbNames.SAMPLE_TABLE_DATA_PROCESSING_COLUMN_NAME, sampleRow);
    Optional<String> contact = getOptionalStringValueFromRow(GEOmetadbNames.SAMPLE_TABLE_CONTACT_COLUMN_NAME,
      sampleRow);
    Optional<String> supplementaryFile = getOptionalStringValueFromRow(
      GEOmetadbNames.SAMPLE_TABLE_SUPPLEMENTARY_FILE_COLUMN_NAME, sampleRow);

    Map<Integer, PerChannelSampleInfo> perChannelInformation = new HashMap<>();
    PerChannelSampleInfo channel1SampleInfo = new PerChannelSampleInfo(1, channel1Source, Collections.emptyList(),
      channel1Characteristics, channel1Molecule, channel1Label, channel1TreatmentProtocol, channel1ExtractProtocol);
    PerChannelSampleInfo channel2SampleInfo = new PerChannelSampleInfo(2, channel2Source, Collections.emptyList(),
      channel2Characteristics, channel2Molecule, channel2Label, channel2TreatmentProtocol, channel2ExtractProtocol);
    perChannelInformation.put(1, channel1SampleInfo);
    perChannelInformation.put(2, channel2SampleInfo);

    // TODO Use: status, types, hybProtocol, dataProcessing, supplementaryFile
    return new Sample(gsm, title, "", description, "", perChannelInformation, Optional.empty(), Collections.emptyList(),
      Optional.empty(), Optional.empty(), Optional.empty());
  }

  /**
   * @param variableString The raw string containing the variable definitions
   * @return (gsm -> (variable name -> variable value))
   */
  private Map<String, Map<String, String>> extractVariables(String variableString)
  {
    Map<String, Map<String, String>> variables = new HashMap<>();

    // TODO

    return variables;
  }

  private Platform extractGEOPlatformFromRow(Map<String, String> platformRow) throws GEOIngestorException
  {
    String title = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_TITLE_COLUMN_NAME, platformRow);
    String gpl = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_GPL_COLUMN_NAME, platformRow);
    Optional<String> status = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_STATUS_COLUMN_NAME,
      platformRow);
    Optional<String> submissionDate = getOptionalStringValueFromRow(
      GEOmetadbNames.PLATFORM_TABLE_SUBMISSION_DATE_COLUMN_NAME, platformRow);
    Optional<String> lastUpdateDate = getOptionalStringValueFromRow(
      GEOmetadbNames.PLATFORM_TABLE_LAST_UPDATE_DATE_COLUMN_NAME, platformRow);
    String technology = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_TECHNOLOGY_COLUMN_NAME,
      platformRow);
    String distribution = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_DISTRIBUTION_COLUMN_NAME,
      platformRow);
    String organism = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_ORGANISM_COLUMN_NAME, platformRow);
    String manufacturer = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_MANUFACTURER_COLUMN_NAME,
      platformRow);
    String manufactureProtocol = getRequiredStringValueFromRow(
      GEOmetadbNames.PLATFORM_TABLE_MANUFACTURE_PROTOCOL_COLUMN_NAME, platformRow);
    Optional<String> coating = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_COATING_COLUMN_NAME,
      platformRow);
    Optional<String> catalogNumber = getOptionalStringValueFromRow(
      GEOmetadbNames.PLATFORM_TABLE_CATALOG_NUMBER_COLUMN_NAME, platformRow);
    Optional<String> support = getOptionalStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_SUPPORT_COLUMN_NAME,
      platformRow);
    String description = getRequiredStringValueFromRow(GEOmetadbNames.PLATFORM_TABLE_DESCRIPTION_COLUMN_NAME,
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

    // TODO Use: status, submissionDate, lastUpdateDate, contact, dataRowCount, supplementaryFile, biocPackage
    return new Platform(title, distribution, technology, organism, manufacturer,
      Collections.singletonList(manufactureProtocol), Collections.singletonList(description), catalogNumber, webLink,
      support, coating, Collections.emptyList(), Collections.emptyList());
  }

  /**
   * @param selectStatement      The prepared statement to extract all rows from a table
   * @param tableName            The table name
   * @param primaryKeyColumnName The primary key column.
   * @param columnNames          All relevant columns in the table
   * @return [(column name -> column value)]
   * @throws SQLException         If a SQL error error occurs
   * @throws GEOIngestorException If an ingestor error occurs
   */
  private List<Map<String, String>> extractTableRows(PreparedStatement selectStatement, String tableName,
    String primaryKeyColumnName, List<String> columnNames) throws SQLException, GEOIngestorException
  {
    List<Map<String, String>> tableData = new ArrayList<>();
    Set<String> keys = new HashSet<>();
    ResultSet rs = selectStatement.executeQuery();

    int currentRowNumber = 1;
    while (rs.next()) {
      String key = rs.getString(primaryKeyColumnName).trim();

      if (key == null || key.isEmpty())
        throw new GEOIngestorException(
          "empty or missing primary key " + primaryKeyColumnName + " at row " + rs.getRow());

      if (keys.contains(key))
        throw new GEOIngestorException(
          "duplicate entries for primary key " + key + " found at row " + currentRowNumber);

      Map<String, String> row = new LinkedHashMap<>();
      for (String columnName : columnNames) {
        String value = rs.getString(columnName).trim();
        if (value != null && !value.isEmpty())
          row.put(columnName, value);
      }
      tableData.add(row);
      currentRowNumber++;
    }
    selectStatement.close();
    System.out.println("" + currentRowNumber + " row (s) in " + tableName + " table");

    return tableData;
  }

  /**
   * @param characteristicsString String of form: characteristic_name1: value1; characteristic_name2: value2; ...
   * @return (characteristic name -> characteristic value)
   */
  private Map<String, String> extractCharacteristics(Optional<String> characteristicsString)
  {
    Map<String, String> characteristics = new HashMap<>();

    if (characteristicsString.isPresent()) {
      for (String characteristicString : Arrays.stream(characteristicsString.get().split(";")).map(String::trim)
        .filter(s -> !s.isEmpty()).toArray(String[]::new)) {
        String characteristicAndValue[] = characteristicString.split(":");
        if (characteristicAndValue.length == 2) {
          String characteristicName = characteristicAndValue[0].trim();
          String characteristicValues = characteristicAndValue[1].trim();

          if (!characteristicName.isEmpty() && !characteristicValues.isEmpty()) {
            characteristics.put(characteristicName, characteristicValues);
          }
        }
      }
    }
    return characteristics;
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
        String name = getOrElse(fieldValues, GEOmetadbNames.NAME_CONTACT_ATTRIBUTE, "");
        String email = getOrElse(fieldValues, GEOmetadbNames.EMAIL_CONTACT_ATTRIBUTE, "");
        String phone = getOrElse(fieldValues, GEOmetadbNames.PHONE_CONTACT_ATTRIBUTE, "");
        String fax = getOrElse(fieldValues, GEOmetadbNames.FAX_CONTACT_ATTRIBUTE, "");
        String laboratory = getOrElse(fieldValues, GEOmetadbNames.LABORATORY_CONTACT_ATTRIBUTE, "");
        String department = getOrElse(fieldValues, GEOmetadbNames.DEPARTMENT_CONTACT_ATTRIBUTE, "");
        String institute = getOrElse(fieldValues, GEOmetadbNames.INSTITUTE_CONTACT_ATTRIBUTE, "");
        String address = getOrElse(fieldValues, GEOmetadbNames.ADDRESS_CONTACT_ATTRIBUTE, "");
        String city = getOrElse(fieldValues, GEOmetadbNames.CITY_CONTACT_ATTRIBUTE, "");
        String state = getOrElse(fieldValues, GEOmetadbNames.STATE_CONTACT_ATTRIBUTE, "");
        String zipOrPostalCode = getOrElse(fieldValues, GEOmetadbNames.ZIP_POSTAL_CODE_CONTACT_ATTRIBUTE, "");
        String country = getOrElse(fieldValues, GEOmetadbNames.COUNTRY_CONTACT_ATTRIBUTE, "");
        String webLink = getOrElse(fieldValues, GEOmetadbNames.WEB_LINK_CONTACT_ATTRIBUTE, "");
        Contributor contributor = new Contributor(name, email, phone, fax, laboratory, department, institute, address,
          city, state, zipOrPostalCode, country, webLink);

        contributors.add(contributor);
      }
    }
    return contributors;
  }

  /**
   * @param typesString The raw string containing the comma-separated types
   * @return A list of types
   */
  private Set<String> extractTypes(String typesString)
  {
    Set<String> types = new HashSet<>();

    for (String type : Arrays.stream(typesString.split(",")).map(String::trim).filter(s -> !s.isEmpty())
      .toArray(String[]::new))
      types.add(type);

    return types;
  }

  private String getOrElse(Map<String, String> fieldValues, String fieldName, String defaultValue)
  {
    if (fieldValues.containsKey(fieldName))
      return fieldValues.get(fieldName);
    else
      return defaultValue;
  }

  private String getStringValueFromRow(String columnName, Map<String, String> row) throws GEOIngestorException
  {
    if (row.containsKey(columnName))
      return row.get(columnName);
    else
      return "";
  }

  private String getRequiredStringValueFromRow(String columnName, Map<String, String> row, int rowNumber)
    throws GEOIngestorException
  {
    if (row.containsKey(columnName))
      return row.get(columnName);
    else
      throw new GEOIngestorException("missing value for required column " + columnName + " at row " + rowNumber);
  }

  private String getRequiredStringValueFromRow(String columnName, Map<String, String> row) throws GEOIngestorException
  {
    if (row.containsKey(columnName))
      return row.get(columnName);
    else
      throw new GEOIngestorException("missing value for required column " + columnName);
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
