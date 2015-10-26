package org.metadatacenter.ingestors.geo.soft;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.metadatacenter.ingestors.geo.GEOIngestorException;
import org.metadatacenter.ingestors.geo.metadata.ContributorName;
import org.metadatacenter.ingestors.geo.metadata.GEOMetadata;
import org.metadatacenter.ingestors.geo.metadata.PerChannelSampleInfo;
import org.metadatacenter.ingestors.geo.metadata.Platform;
import org.metadatacenter.ingestors.geo.metadata.Protocol;
import org.metadatacenter.ingestors.geo.metadata.Sample;
import org.metadatacenter.ingestors.geo.metadata.Series;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.CHARACTERISTICS_FIELD_PREFIX;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.FIELD_NAMES_COLUMN_NUMBER;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.FIELD_VALUES_COLUMN_NUMBER;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.GEO_METADATA_SHEET_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_CATALOG_NUMBER_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_COATING_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_CONTRIBUTOR_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_DESCRIPTION_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_DISTRIBUTION_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_HEADER_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_MANUFACTURER_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_MANUFACTURE_PROTOCOL_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_ORGANISM_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_PUBMED_ID_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_SUPPORT_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_TECHNOLOGY_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_TITLE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PLATFORM_WEB_LINK_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PROTOCOLS_HEADER_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PROTOCOL_DATA_PROCESSING_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PROTOCOL_EXTRACT_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PROTOCOL_GROWTH_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PROTOCOL_HYB_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PROTOCOL_LABEL_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PROTOCOL_SCAN_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PROTOCOL_TREATMENT_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PROTOCOL_VALUE_DEFINITION_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.PlatformFieldNames;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.ProtocolFieldNames;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SAMPLES_BIOMATERIAL_PROVIDER_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SAMPLES_CEL_FILE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SAMPLES_CHP_FILE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SAMPLES_DESCRIPTION_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SAMPLES_EXP_FILE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SAMPLES_HEADER_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SAMPLES_LABEL_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SAMPLES_MOLECULE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SAMPLES_ORGANISM_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SAMPLES_RAW_DATA_FILE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SAMPLES_SAMPLE_NAME_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SAMPLES_SOURCE_NAME_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SAMPLES_TITLE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SERIES_HEADER_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SERIES_OVERALL_DESIGN_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SERIES_PUBMED_ID_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SERIES_SUMMARY_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SERIES_TITLE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.soft.GEOSoftNames.SeriesFieldNames;

public class GEOSoftIngestor
{
  private final String spreadsheetFileName;

  public GEOSoftIngestor(String spreadsheetFileName) throws GEOIngestorException
  {
    this.spreadsheetFileName = spreadsheetFileName;
  }

  public GEOMetadata extractGEOMetadata() throws GEOIngestorException
  {
    InputStream spreadsheetStream = SpreadsheetUtil.openSpreadsheetInputStream(spreadsheetFileName);
    Workbook workbook = SpreadsheetUtil.createReadonlyWorkbook(spreadsheetStream);
    Sheet geoMetadataSheet = getGEOMetadataSheet(workbook);

    return extractGEOMetadata(geoMetadataSheet);
  }

  private GEOMetadata extractGEOMetadata(Sheet geoMetadataSheet) throws GEOIngestorException
  {
    Series series = extractSeries(geoMetadataSheet);
    Map<String, Sample> samples = extractSamples(geoMetadataSheet);
    Protocol protocol = extractProtocol(geoMetadataSheet);
    Optional<Platform> platform = extractPlatform(geoMetadataSheet);

    return new GEOMetadata(series, samples, protocol, platform);
  }

  private Series extractSeries(Sheet geoMetadataSheet) throws GEOIngestorException
  {
    Map<String, List<String>> seriesFields = extractSeriesFields(geoMetadataSheet);
    String title = getRequiredMultiValueFieldValue(seriesFields, SERIES_TITLE_FIELD_NAME, SERIES_HEADER_NAME);
    List<String> summary = getMultiValueFieldValues(seriesFields, SERIES_SUMMARY_FIELD_NAME);
    List<String> overallDesign = getMultiValueFieldValues(seriesFields, SERIES_OVERALL_DESIGN_FIELD_NAME);
    List<ContributorName> contributors = extractContributorNames(seriesFields, SERIES_PUBMED_ID_FIELD_NAME);
    List<String> pubmedIDs = getMultiValueFieldValues(seriesFields, SERIES_PUBMED_ID_FIELD_NAME);
    Map<String, Map<String, String>> variables = new HashMap<>(); // TODO
    Map<String, List<String>> repeat = new HashMap<>(); // TODO

    return new Series(title, summary, overallDesign, contributors, pubmedIDs, variables, repeat);
  }

  private List<ContributorName> extractContributorNames(Map<String, List<String>> seriesFields,
    String SERIES_CONTRIBUTOR_FIELD_NAME) throws GEOIngestorException
  {
    List<ContributorName> contributorNames = new ArrayList<>();
    List<String> contributorNameFieldValues = getMultiValueFieldValues(seriesFields, SERIES_CONTRIBUTOR_FIELD_NAME);

    String regexp = "([A-Za-z]+),\\s+([A-Za-z]+)\\s+([A-Za-z]+)";
    Pattern pattern = Pattern.compile(regexp);
    for (String contributorName : contributorNameFieldValues) {
      Matcher matcher = pattern.matcher(contributorName);
      if (matcher.matches()) {
        String firstName = matcher.group(1);
        String middleInitial = matcher.group(2);
        String lastName = matcher.group(3);
        contributorNames.add(new ContributorName(firstName, middleInitial, lastName));
      } else { // Just add entire string as last name
        contributorNames.add(new ContributorName("", "", contributorName));
      }
    }
    return contributorNames;
  }

  private String getRequiredSingleValueFieldValue(Map<String, String> fields, String fieldName,
    String fieldCollectionName) throws GEOIngestorException
  {
    Optional<String> fieldValue = getSingleValueFieldValue(fields, fieldName);

    if (fieldValue.isPresent())
      return fieldValue.get();
    else
      throw new GEOIngestorException("could not find required field " + fieldName + " in " + fieldCollectionName);
  }

  private String getRequiredMultiValueFieldValue(Map<String, List<String>> fields, String fieldName,
    String fieldCollectionName) throws GEOIngestorException
  {
    Optional<String> fieldValue = getMultiValueFieldValue(fields, fieldName, fieldCollectionName);

    if (fieldValue.isPresent())
      return fieldValue.get();
    else
      throw new GEOIngestorException(
        "could not find required multi-value field " + fieldName + " in " + fieldCollectionName);
  }

  private Optional<String> getSingleValueFieldValue(Map<String, String> fields, String fieldName)
    throws GEOIngestorException
  {
    if (fields.containsKey(fieldName)) {
      return Optional.of(fields.get(fieldName));
    } else
      return Optional.empty();
  }

  private Optional<String> getMultiValueFieldValue(Map<String, List<String>> fields, String fieldName,
    String fieldCollectionName) throws GEOIngestorException
  {
    if (fields.containsKey(fieldName)) {
      if (fields.get(fieldName).size() == 1)
        return Optional.of(fields.get(fieldName).iterator().next());
      else
        throw new GEOIngestorException(
          "not expecting multiple values for field " + fieldName + " in " + fieldCollectionName);
    } else
      return Optional.empty();
  }

  private List<String> getMultiValueFieldValues(Map<String, List<String>> fields, String fieldName)
    throws GEOIngestorException
  {
    if (fields.containsKey(fieldName))
      return fields.get(fieldName);
    else
      return Collections.emptyList();
  }

  private List<String> getRequiredMultiValueFieldValues(Map<String, List<String>> fields, String fieldName,
    String fieldCollectionName) throws GEOIngestorException
  {
    if (fields.containsKey(fieldName))
      return fields.get(fieldName);
    else
      throw new GEOIngestorException("no values for field " + fieldName + " in " + fieldCollectionName);
  }

  private Map<String, List<String>> extractSeriesFields(Sheet geoMetadataSheet) throws GEOIngestorException
  {
    Optional<Integer> seriesHeaderRowNumber = findFieldRowNumber(geoMetadataSheet, SERIES_HEADER_NAME,
      FIELD_NAMES_COLUMN_NUMBER);

    if (!seriesHeaderRowNumber.isPresent())
      throw new GEOIngestorException("no series header found in metadata spreadsheet");

    Optional<Integer> seriesTitleRowNumber = findFieldRowNumber(geoMetadataSheet, SERIES_TITLE_FIELD_NAME,
      FIELD_NAMES_COLUMN_NUMBER);

    if (seriesTitleRowNumber.isPresent()) {
      Map<String, List<String>> fieldName2Values = findFieldValues(geoMetadataSheet, FIELD_NAMES_COLUMN_NUMBER,
        FIELD_VALUES_COLUMN_NUMBER, seriesTitleRowNumber.get());

      if (fieldName2Values.isEmpty())
        throw new GEOIngestorException("no series fields found in metadata spreadsheet");

      if (!SeriesFieldNames.containsAll(fieldName2Values.keySet())) {
        Set<String> fieldNames = fieldName2Values.keySet();
        fieldNames.removeAll(SeriesFieldNames);
        throw new GEOIngestorException("unknown series fields " + fieldNames);
      }

      return fieldName2Values;
    } else
      throw new GEOIngestorException("no series title field named " + SERIES_TITLE_FIELD_NAME + " in metadata sheet");
  }

  private Protocol extractProtocol(Sheet geoMetadataSheet) throws GEOIngestorException
  {
    Map<String, List<String>> protocolFields = extractProtocolFields(geoMetadataSheet);

    if (protocolFields.isEmpty())
      throw new GEOIngestorException("no protocol fields found in metadata sheet");

    List<String> growth = getMultiValueFieldValues(protocolFields, PROTOCOL_GROWTH_FIELD_NAME);
    List<String> treatment = getMultiValueFieldValues(protocolFields, PROTOCOL_TREATMENT_FIELD_NAME);
    List<String> extract = getMultiValueFieldValues(protocolFields, PROTOCOL_EXTRACT_FIELD_NAME);
    List<String> label = getMultiValueFieldValues(protocolFields, PROTOCOL_LABEL_FIELD_NAME);
    List<String> hyb = getMultiValueFieldValues(protocolFields, PROTOCOL_HYB_FIELD_NAME);
    List<String> scan = getMultiValueFieldValues(protocolFields, PROTOCOL_SCAN_FIELD_NAME);
    List<String> dataProcessing = getMultiValueFieldValues(protocolFields, PROTOCOL_DATA_PROCESSING_FIELD_NAME);
    List<String> valueDefinition = getMultiValueFieldValues(protocolFields, PROTOCOL_VALUE_DEFINITION_FIELD_NAME);

    // The fields not in the predefined field set become user-defined fields
    protocolFields.keySet().removeAll(ProtocolFieldNames);

    return new Protocol(growth, treatment, extract, label, hyb, scan, dataProcessing, valueDefinition, protocolFields);
  }

  private Optional<Platform> extractPlatform(Sheet geoMetadataSheet) throws GEOIngestorException
  {
    Map<String, List<String>> platformFields = extractPlatformFields(geoMetadataSheet);

    if (!platformFields.isEmpty()) {
      String title = getRequiredMultiValueFieldValue(platformFields, PLATFORM_TITLE_FIELD_NAME, PLATFORM_HEADER_NAME);
      String distribution = getRequiredMultiValueFieldValue(platformFields, PLATFORM_DISTRIBUTION_FIELD_NAME,
        PLATFORM_HEADER_NAME);
      String technology = getRequiredMultiValueFieldValue(platformFields, PLATFORM_TECHNOLOGY_FIELD_NAME,
        PLATFORM_HEADER_NAME);
      String organism = getRequiredMultiValueFieldValue(platformFields, PLATFORM_ORGANISM_FIELD_NAME,
        PLATFORM_HEADER_NAME);
      String manufacturer = getRequiredMultiValueFieldValue(platformFields, PLATFORM_MANUFACTURER_FIELD_NAME,
        PLATFORM_HEADER_NAME);
      List<String> manufacturerProtocol = getMultiValueFieldValues(platformFields,
        PLATFORM_MANUFACTURE_PROTOCOL_FIELD_NAME);
      List<String> description = getMultiValueFieldValues(platformFields, PLATFORM_DESCRIPTION_FIELD_NAME);
      Optional<String> catalogNumber = getMultiValueFieldValue(platformFields, PLATFORM_CATALOG_NUMBER_FIELD_NAME,
        PLATFORM_HEADER_NAME);
      Optional<String> webLink = getMultiValueFieldValue(platformFields, PLATFORM_WEB_LINK_FIELD_NAME,
        PLATFORM_HEADER_NAME);
      Optional<String> support = getMultiValueFieldValue(platformFields, PLATFORM_SUPPORT_FIELD_NAME,
        PLATFORM_HEADER_NAME);
      Optional<String> coating = getMultiValueFieldValue(platformFields, PLATFORM_COATING_FIELD_NAME,
        PLATFORM_HEADER_NAME);
      List<String> contributor = getMultiValueFieldValues(platformFields, PLATFORM_CONTRIBUTOR_FIELD_NAME);
      List<String> pubmedID = getMultiValueFieldValues(platformFields, PLATFORM_PUBMED_ID_FIELD_NAME);

      return Optional.of(
        new Platform(title, distribution, technology, organism, manufacturer, manufacturerProtocol, description,
          catalogNumber, webLink, support, coating, contributor, pubmedID));
    } else
      return Optional.empty();
  }

  private Map<String, List<String>> extractPlatformFields(Sheet geoMetadataSheet) throws GEOIngestorException
  {
    Optional<Integer> platformHeaderRowNumber = findFieldRowNumber(geoMetadataSheet, PLATFORM_HEADER_NAME,
      FIELD_NAMES_COLUMN_NUMBER);

    if (platformHeaderRowNumber.isPresent()) {

      Map<String, List<String>> fieldName2Values = findFieldValues(geoMetadataSheet, FIELD_NAMES_COLUMN_NUMBER,
        FIELD_VALUES_COLUMN_NUMBER, platformHeaderRowNumber.get() + 1);

      if (!fieldName2Values.isEmpty()) {

        if (!PlatformFieldNames.containsAll(fieldName2Values.keySet())) {
          Set<String> fieldNames = fieldName2Values.keySet();
          fieldNames.removeAll(PlatformFieldNames);
          throw new GEOIngestorException("unknown platform fields " + fieldNames);
        }

        return fieldName2Values;
      } else
        return Collections.emptyMap();
    } else
      return Collections.emptyMap();
  }

  private Map<String, List<String>> extractProtocolFields(Sheet geoMetadataSheet) throws GEOIngestorException
  {
    Optional<Integer> protocolsHeaderRowNumber = findFieldRowNumber(geoMetadataSheet, PROTOCOLS_HEADER_NAME,
      FIELD_NAMES_COLUMN_NUMBER);

    if (!protocolsHeaderRowNumber.isPresent())
      throw new GEOIngestorException("no protocols header field named " + PROTOCOLS_HEADER_NAME + " in metadata sheet");

    Map<String, List<String>> fieldName2Values = findProtocolFieldValues(geoMetadataSheet, FIELD_NAMES_COLUMN_NUMBER,
      FIELD_VALUES_COLUMN_NUMBER, protocolsHeaderRowNumber.get() + 1);

    if (fieldName2Values.isEmpty())
      throw new GEOIngestorException("no protocol fields found in metadata spreadsheet");

    // We do not indicate an error if there fields beyond the set defined in GEONames.ProtocolFieldNames
    // because it appears that user are allowed to define arbitrary fields.

    return fieldName2Values;
  }

  private Map<String, Sample> extractSamples(Sheet geoMetadataSheet) throws GEOIngestorException
  {
    Map<String, Sample> samples = new HashMap<>();

    Optional<Integer> samplesHeaderRowNumber = findFieldRowNumber(geoMetadataSheet, SAMPLES_HEADER_NAME,
      FIELD_NAMES_COLUMN_NUMBER);

    if (!samplesHeaderRowNumber.isPresent())
      throw new GEOIngestorException("no samples header found in metadata spreadsheet");

    Optional<Integer> samplesColumnNamesRowNumber = findFieldRowNumber(geoMetadataSheet, SAMPLES_SAMPLE_NAME_FIELD_NAME,
      FIELD_NAMES_COLUMN_NUMBER);

    if (samplesColumnNamesRowNumber.isPresent()) {

      // sample name -> (sample column name -> [value])
      Map<String, Map<String, List<String>>> samplesColumns = extractSamplesColumnValues(geoMetadataSheet,
        samplesColumnNamesRowNumber.get());

      for (String sampleName : samplesColumns.keySet()) {
        Map<String, List<String>> sampleFields = samplesColumns.get(sampleName);

        String sampleTitle = getRequiredMultiValueFieldValue(sampleFields, SAMPLES_TITLE_FIELD_NAME,
          SAMPLES_HEADER_NAME);
        List<String> rawDataFiles = getRepeatedValueFieldValues(sampleFields, SAMPLES_RAW_DATA_FILE_FIELD_NAME,
          SAMPLES_HEADER_NAME);
        Optional<String> celFile = getMultiValueFieldValue(sampleFields, SAMPLES_CEL_FILE_FIELD_NAME,
          SAMPLES_HEADER_NAME);
        Optional<String> expFile = getMultiValueFieldValue(sampleFields, SAMPLES_EXP_FILE_FIELD_NAME,
          SAMPLES_HEADER_NAME);
        Optional<String> chpFile = getMultiValueFieldValue(sampleFields, SAMPLES_CHP_FILE_FIELD_NAME,
          SAMPLES_HEADER_NAME);
        String sourceName = getRequiredMultiValueFieldValue(sampleFields, SAMPLES_SOURCE_NAME_FIELD_NAME,
          SAMPLES_HEADER_NAME);
        List<String> organisms = getRequiredRepeatedValueFieldValues(sampleFields, SAMPLES_ORGANISM_FIELD_NAME,
          SAMPLES_HEADER_NAME);
        Map<String, String> characteristics = extractCharacteristicsFromSampleFields(sampleFields);
        Optional<String> biomaterialProvider = getMultiValueFieldValue(sampleFields,
          SAMPLES_BIOMATERIAL_PROVIDER_FIELD_NAME, SAMPLES_HEADER_NAME);
        String molecule = getRequiredMultiValueFieldValue(sampleFields, SAMPLES_MOLECULE_FIELD_NAME,
          SAMPLES_HEADER_NAME);
        String label = getRequiredMultiValueFieldValue(sampleFields, SAMPLES_LABEL_FIELD_NAME, SAMPLES_HEADER_NAME);
        String description = getRequiredMultiValueFieldValue(sampleFields, SAMPLES_DESCRIPTION_FIELD_NAME,
          SAMPLES_HEADER_NAME);
        String platform = getRequiredMultiValueFieldValue(sampleFields, SAMPLES_MOLECULE_FIELD_NAME,
          SAMPLES_HEADER_NAME);

        PerChannelSampleInfo perChannelSampleInfo = new PerChannelSampleInfo(0, sourceName, organisms, characteristics,
          molecule, label);
        Map<Integer, PerChannelSampleInfo> perChannelInformation = new HashMap<>();
        perChannelInformation.put(0, perChannelSampleInfo);

        Sample sample = new Sample(sampleName, sampleTitle, label, description, platform, perChannelInformation,
          biomaterialProvider, rawDataFiles, celFile, expFile, chpFile);

        if (samples.containsKey(sampleName))
          throw new GEOIngestorException("multiple entries for sample " + sampleName);

        samples.put(sampleName, sample);
      }
    } else
      throw new GEOIngestorException(
        "no samples header field named " + SAMPLES_SAMPLE_NAME_FIELD_NAME + " in metadata sheet");

    if (samples.isEmpty())
      throw new GEOIngestorException("no samples found in metadata sheet");

    return samples;
  }

  private List<String> getRequiredRepeatedValueFieldValues(Map<String, List<String>> sampleFields, String fieldName,
    String fieldCollectionName) throws GEOIngestorException
  {
    if (sampleFields.containsKey(fieldName)) {
      if (!sampleFields.get(fieldName).isEmpty())
        return sampleFields.get(fieldName);
      else
        throw new GEOIngestorException("no values for required field " + fieldName + " in " + fieldCollectionName);
    } else
      throw new GEOIngestorException("no required field " + fieldName + " in " + fieldCollectionName);
  }

  private List<String> getRepeatedValueFieldValues(Map<String, List<String>> sampleFields, String fieldName,
    String fieldCollectionName) throws GEOIngestorException
  {
    if (sampleFields.containsKey(fieldName))
      return sampleFields.get(fieldName);
    else
      return new ArrayList<>();
  }

  private Map<String, String> extractCharacteristicsFromSampleFields(Map<String, List<String>> sampleFields)
    throws GEOIngestorException
  {
    Map<String, String> characteristics = new HashMap<>();

    for (String fieldName : sampleFields.keySet()) {
      if (fieldName.startsWith(CHARACTERISTICS_FIELD_PREFIX)) {
        String characteristicName = fieldName.substring(CHARACTERISTICS_FIELD_PREFIX.length());
        if (characteristics.containsKey(characteristicName))
          throw new GEOIngestorException("repeated characteristic " + characteristicName + " in metadata spreadsheet");

        List<String> characteristicValues = sampleFields.get(fieldName);
        if (!characteristicValues.isEmpty()) {
          if (characteristicValues.size() > 1)
            throw new GEOIngestorException(
              "multiple values for characteristic " + characteristicName + " in metadata spreadsheet");
          String characteristicValue = characteristicValues.get(0);
          characteristics.put(characteristicName, characteristicValue);
        }
      }
    }
    return characteristics;
  }

  // Returns: sample name -> (sample field name -> [field values])
  // Some sample columns can be repeated (e.g., raw data file).
  private Map<String, Map<String, List<String>>> extractSamplesColumnValues(Sheet geoMetadataSheet,
    int samplesColumnNamesRowNumber) throws GEOIngestorException
  {
    Map<String, Map<String, List<String>>> samplesColumnValues = new HashMap<>();
    List<String> samplesColumnNames = new ArrayList<>();
    Row samplesColumnNamesRow = geoMetadataSheet.getRow(samplesColumnNamesRowNumber);

    if (samplesColumnNamesRow == null)
      throw new GEOIngestorException("could not find sample column names row at row " + samplesColumnNamesRowNumber);

    boolean lastColumnReached = false;
    for (int currentColumnNumber = 0;
         currentColumnNumber <= samplesColumnNamesRow.getLastCellNum() && !lastColumnReached; currentColumnNumber++) {
      Cell fieldNameCell = samplesColumnNamesRow.getCell(currentColumnNumber);

      if (fieldNameCell == null || SpreadsheetUtil.isBlankCellType(fieldNameCell)) {
        lastColumnReached = true;
      } else {
        String fieldNameValue = SpreadsheetUtil.getStringCellValue(fieldNameCell);

        if (fieldNameValue.isEmpty())
          throw new GEOIngestorException(
            "empty samples title cell at row " + samplesColumnNamesRowNumber + ", column " + currentColumnNumber);

        samplesColumnNames.add(fieldNameValue);
      }
    }

    int numberOfSamplesColumns = samplesColumnNames.size();
    boolean blankRowReached = false;
    for (int currentRowNumber = samplesColumnNamesRowNumber + 1;
         currentRowNumber <= geoMetadataSheet.getLastRowNum() && !blankRowReached; currentRowNumber++) {
      Row valueRow = geoMetadataSheet.getRow(currentRowNumber);

      if (valueRow != null && valueRow.getPhysicalNumberOfCells() > 0) {
        Cell sampleNameCell = valueRow.getCell(0);
        if (sampleNameCell != null && !SpreadsheetUtil.isBlankCellType(sampleNameCell)) {
          String sampleName = SpreadsheetUtil.getCellValueAsString(sampleNameCell);

          if (sampleName.isEmpty())
            throw new GEOIngestorException("empty sample name at row " + currentRowNumber);

          if (samplesColumnValues.containsKey(sampleName))
            throw new GEOIngestorException("duplicate sample name " + sampleName + " found at row " + currentRowNumber);

          samplesColumnValues.put(sampleName, new HashMap<>());

          for (int currentColumnNumber = 0; currentColumnNumber < numberOfSamplesColumns; currentColumnNumber++) {
            Cell fieldValueCell = valueRow.getCell(currentColumnNumber);
            if (fieldValueCell != null && !SpreadsheetUtil.isBlankCellType(fieldValueCell)) {
              String fieldValue = SpreadsheetUtil.getCellValueAsString(fieldValueCell);
              if (!fieldValue.isEmpty()) {
                String headerFieldName = samplesColumnNames.get(currentColumnNumber);
                if (samplesColumnValues.get(sampleName).containsKey(headerFieldName)) {
                  samplesColumnValues.get(sampleName).get(headerFieldName).add(fieldValue);
                } else {
                  List<String> fieldValues = new ArrayList<>();
                  fieldValues.add(fieldValue);
                  samplesColumnValues.get(sampleName).put(headerFieldName, fieldValues);
                }
              }
            }
          }
        } else
          blankRowReached = true;
      } else
        blankRowReached = true;
    }
    return samplesColumnValues;
  }

  private Sheet getGEOMetadataSheet(Workbook workbook) throws GEOIngestorException
  {
    Sheet metadataSheet = workbook.getSheet(GEO_METADATA_SHEET_NAME);

    if (metadataSheet == null)
      throw new GEOIngestorException(
        "spreadsheet does not contain a GEO metadata template sheet called " + GEO_METADATA_SHEET_NAME);
    else
      return metadataSheet;
  }

  public static Optional<Integer> findFieldRowNumber(Sheet sheet, String fieldName, int fieldColumnNumber)
    throws GEOIngestorException
  {
    int firstRow = 0;
    int lastRow = sheet.getLastRowNum();

    for (int currentRow = firstRow; currentRow <= lastRow; currentRow++) {
      Row row = sheet.getRow(currentRow);
      if (row != null) {
        Cell cell = row.getCell(fieldColumnNumber);
        if (cell != null && SpreadsheetUtil.isStringCellType(cell)) {
          String value = SpreadsheetUtil.getCellValueAsString(cell);
          if (fieldName.equals(value))
            return Optional.of(currentRow);
        }
      }
    }
    return Optional.empty();
  }

  /**
   * A field can have multiple values.
   */
  private Map<String, List<String>> findFieldValues(Sheet sheet, int fieldNameColumnNumber, int fieldValueColumnNumber,
    int startRowNumber) throws GEOIngestorException
  {
    Map<String, List<String>> field2Values = new HashMap<>();
    int finishRowNumber = sheet.getLastRowNum();
    boolean blankRowReached = false;

    for (int currentRow = startRowNumber; currentRow <= finishRowNumber && !blankRowReached; currentRow++) {
      Row row = sheet.getRow(currentRow);
      if (row == null)
        blankRowReached = true;
      else {
        Cell fieldNameCell = row.getCell(fieldNameColumnNumber);

        if (fieldNameCell == null || SpreadsheetUtil.isBlankCellType(fieldNameCell)) {
          blankRowReached = true;
          continue;
        }

        Cell fieldValueCell = row.getCell(fieldValueColumnNumber);

        if (fieldValueCell == null || SpreadsheetUtil.isBlankCellType(fieldValueCell)) {
          blankRowReached = true;
          continue;
        }

        String fieldName = SpreadsheetUtil.getStringCellValue(fieldNameCell);

        if (fieldName.isEmpty())
          throw new GEOIngestorException("empty field name at location " + SpreadsheetUtil
            .getCellLocation(fieldNameCell));

        String fieldValue = SpreadsheetUtil.getCellValueAsString(fieldValueCell);

        if (fieldValue.isEmpty())
          throw new GEOIngestorException("empty field value at location " + SpreadsheetUtil
            .getCellLocation(fieldValueCell));

        if (field2Values.containsKey(fieldName))
          field2Values.get(fieldName).add(fieldValue);
        else {
          List<String> fieldValues = new ArrayList<>();
          fieldValues.add(fieldValue);
          field2Values.put(fieldName, fieldValues);
        }
      }
    }
    return field2Values;
  }

  /**
   * Duplicates not allowed.
   */
  private Map<String, List<String>> findProtocolFieldValues(Sheet sheet, int fieldNameColumnNumber,
    int fieldValueColumnNumber, int startRowNumber) throws GEOIngestorException
  {
    Map<String, List<String>> field2Values = new HashMap<>();
    boolean blankRowReached = false;

    for (int currentRow = startRowNumber; currentRow <= sheet.getLastRowNum() && !blankRowReached; currentRow++) {
      Row row = sheet.getRow(currentRow);

      if (row == null)
        blankRowReached = true;
      else {
        Cell fieldNameCell = row.getCell(fieldNameColumnNumber);

        if (fieldNameCell == null || SpreadsheetUtil.isBlankCellType(fieldNameCell))
          blankRowReached = true;
        else {
          String fieldName = SpreadsheetUtil.getStringCellValue(fieldNameCell);
          if (fieldName.isEmpty())
            blankRowReached = true;
          else {
            Cell fieldValueCell = row.getCell(fieldValueColumnNumber);

            if (fieldValueCell != null && !SpreadsheetUtil.isBlankCellType(fieldValueCell)) {

              String fieldValue = SpreadsheetUtil.getCellValueAsString(fieldValueCell); // Check for null cell

              if (fieldValue.isEmpty())
                throw new GEOIngestorException("empty field value at location " + SpreadsheetUtil
                  .getCellLocation(fieldValueCell));

              if (field2Values.containsKey(fieldName))
                field2Values.get(fieldName).add(fieldValue);
              else {
                List<String> fieldValues = new ArrayList<>();
                fieldValues.add(fieldValue);
                field2Values.put(fieldName, fieldValues);
              }
            }
          }
        }
      }
    }
    return field2Values;
  }
}
