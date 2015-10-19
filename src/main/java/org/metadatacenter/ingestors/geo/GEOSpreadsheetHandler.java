package org.metadatacenter.ingestors.geo;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.metadatacenter.ingestors.geo.metadata.ContributorName;
import org.metadatacenter.ingestors.geo.metadata.GEOMetadata;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.metadatacenter.ingestors.geo.GEONames.CHARACTERISTICS_FIELD_PREFIX;
import static org.metadatacenter.ingestors.geo.GEONames.FIELD_NAMES_COLUMN_NUMBER;
import static org.metadatacenter.ingestors.geo.GEONames.FIELD_VALUES_COLUMN_NUMBER;
import static org.metadatacenter.ingestors.geo.GEONames.GEO_METADATA_SHEET_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_CATALOG_NUMBER_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_COATING_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_CONTRIBUTOR_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_DESCRIPTION_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_DISTRIBUTION_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_HEADER_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_MANUFACTURER_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_MANUFACTURE_PROTOCOL_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_ORGANISM_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_PUBMED_ID_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_SUPPORT_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_TECHNOLOGY_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_TITLE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PLATFORM_WEB_LINK_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PROTOCOLS_HEADER_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PROTOCOL_DATA_PROCESSING_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PROTOCOL_EXTRACT_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PROTOCOL_GROWTH_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PROTOCOL_HYB_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PROTOCOL_LABEL_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PROTOCOL_SCAN_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PROTOCOL_TREATMENT_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PROTOCOL_VALUE_DEFINITION_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.PlatformFieldNames;
import static org.metadatacenter.ingestors.geo.GEONames.SAMPLES_BIOMATERIAL_PROVIDER_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SAMPLES_CEL_FILE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SAMPLES_CHP_FILE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SAMPLES_DESCRIPTION_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SAMPLES_EXP_FILE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SAMPLES_HEADER_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SAMPLES_LABEL_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SAMPLES_MOLECULE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SAMPLES_ORGANISM_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SAMPLES_RAW_DATA_FILE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SAMPLES_SOURCE_NAME_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SAMPLES_TITLE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SERIES_HEADER_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SERIES_OVERALL_DESIGN_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SERIES_PUBMED_ID_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SERIES_SUMMARY_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SERIES_TITLE_FIELD_NAME;
import static org.metadatacenter.ingestors.geo.GEONames.SeriesFieldNames;
import static org.metadatacenter.ingestors.geo.SpreadsheetUtil.getCellLocation;
import static org.metadatacenter.ingestors.geo.SpreadsheetUtil.getStringCellValue;

public class GEOSpreadsheetHandler
{
  private final String spreadsheetFileName;

  public GEOSpreadsheetHandler(String spreadsheetFileName) throws GEOIngestorException
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
    String summary = getRequiredMultiValueFieldValue(seriesFields, SERIES_SUMMARY_FIELD_NAME, SERIES_HEADER_NAME);
    String overallDesign = getRequiredMultiValueFieldValue(seriesFields, SERIES_OVERALL_DESIGN_FIELD_NAME,
      SERIES_HEADER_NAME);
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
      throw new GEOIngestorException("could not find required field " + fieldName + " in " + fieldCollectionName);
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
    if (seriesHeaderRowNumber.isPresent()) {
      Optional<Integer> samplesHeaderRowNumber = findFieldRowNumber(geoMetadataSheet, SAMPLES_HEADER_NAME,
        FIELD_NAMES_COLUMN_NUMBER);
      if (samplesHeaderRowNumber.isPresent()) {
        Map<String, List<String>> fieldName2Values = findFieldValues(geoMetadataSheet, FIELD_NAMES_COLUMN_NUMBER,
          FIELD_VALUES_COLUMN_NUMBER, seriesHeaderRowNumber.get() + 1, samplesHeaderRowNumber.get() - 1);

        if (fieldName2Values.isEmpty())
          throw new GEOIngestorException("no series fields found in metadata spreadsheet");

        if (!SeriesFieldNames.containsAll(fieldName2Values.keySet()))
          throw new GEOIngestorException(
            "unknown series fields " + fieldName2Values.keySet().removeAll(SeriesFieldNames));

        return fieldName2Values;
      } else
        throw new GEOIngestorException("no samples header field named " + SAMPLES_HEADER_NAME + " in metadata sheet");
    } else
      throw new GEOIngestorException("no series header field named " + SERIES_HEADER_NAME + " in metadata sheet");
  }

  private Protocol extractProtocol(Sheet geoMetadataSheet) throws GEOIngestorException
  {
    Map<String, String> protocolFields = extractProtocolFields(geoMetadataSheet);

    if (protocolFields.isEmpty())
      throw new GEOIngestorException("no protocols found in metadata sheet");

    Optional<String> growth = getSingleValueFieldValue(protocolFields, PROTOCOL_GROWTH_FIELD_NAME);
    Optional<String> treatment = getSingleValueFieldValue(protocolFields, PROTOCOL_TREATMENT_FIELD_NAME);
    String extract = getRequiredSingleValueFieldValue(protocolFields, PROTOCOL_EXTRACT_FIELD_NAME,
      PROTOCOLS_HEADER_NAME);
    String label = getRequiredSingleValueFieldValue(protocolFields, PROTOCOL_LABEL_FIELD_NAME, PROTOCOLS_HEADER_NAME);
    String hyb = getRequiredSingleValueFieldValue(protocolFields, PROTOCOL_HYB_FIELD_NAME, PROTOCOLS_HEADER_NAME);
    String scan = getRequiredSingleValueFieldValue(protocolFields, PROTOCOL_SCAN_FIELD_NAME, PROTOCOLS_HEADER_NAME);
    String dataProcessing = getRequiredSingleValueFieldValue(protocolFields, PROTOCOL_DATA_PROCESSING_FIELD_NAME,
      PROTOCOLS_HEADER_NAME);
    String valueDefinition = getRequiredSingleValueFieldValue(protocolFields, PROTOCOL_VALUE_DEFINITION_FIELD_NAME,
      PROTOCOLS_HEADER_NAME);

    return new Protocol(growth, treatment, extract, label, hyb, scan, dataProcessing, valueDefinition);
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
        FIELD_VALUES_COLUMN_NUMBER, platformHeaderRowNumber.get() + 1, geoMetadataSheet.getLastRowNum());

      if (!fieldName2Values.isEmpty()) {

        if (!PlatformFieldNames.containsAll(fieldName2Values.keySet()))
          throw new GEOIngestorException(
            "unknown platform fields " + fieldName2Values.keySet().removeAll(PlatformFieldNames));

        return fieldName2Values;
      } else
        return Collections.emptyMap();
    } else
      return Collections.emptyMap();
  }

  private Map<String, String> extractProtocolFields(Sheet geoMetadataSheet) throws GEOIngestorException
  {
    Optional<Integer> seriesHeaderRowNumber = findFieldRowNumber(geoMetadataSheet, PROTOCOLS_HEADER_NAME,
      FIELD_NAMES_COLUMN_NUMBER);
    if (seriesHeaderRowNumber.isPresent()) {

      Map<String, String> fieldName2Value = findFieldValue(geoMetadataSheet, FIELD_NAMES_COLUMN_NUMBER,
        FIELD_VALUES_COLUMN_NUMBER, seriesHeaderRowNumber.get() + 1, geoMetadataSheet.getLastRowNum());

      if (fieldName2Value.isEmpty())
        throw new GEOIngestorException("no protocol fields found in metadata spreadsheet");

      if (!SeriesFieldNames.containsAll(fieldName2Value.keySet()))
        throw new GEOIngestorException("unknown series fields " + fieldName2Value.keySet().removeAll(SeriesFieldNames));

      return fieldName2Value;
    } else
      throw new GEOIngestorException("no protocols header field named " + PROTOCOLS_HEADER_NAME + " in metadata sheet");
  }

  private Map<String, Sample> extractSamples(Sheet geoMetadataSheet) throws GEOIngestorException
  {
    Map<String, Sample> samples = new HashMap<>();

    Optional<Integer> samplesHeaderRowNumber = findFieldRowNumber(geoMetadataSheet, SAMPLES_HEADER_NAME,
      FIELD_NAMES_COLUMN_NUMBER);
    if (samplesHeaderRowNumber.isPresent()) {
      Optional<Integer> protocolsHeaderRowNumber = findFieldRowNumber(geoMetadataSheet, PROTOCOLS_HEADER_NAME,
        FIELD_NAMES_COLUMN_NUMBER);
      if (protocolsHeaderRowNumber.isPresent()) {
        int samplesStartRowNumber = samplesHeaderRowNumber.get() + 1;
        int sampledEndRowNumber = protocolsHeaderRowNumber.get() - 1;

        Map<String, Map<String, List<String>>> samplesFields = extractSampleFields(geoMetadataSheet,
          samplesStartRowNumber, sampledEndRowNumber);

        for (String sampleName : samplesFields.keySet()) {
          Map<String, List<String>> sampleFields = samplesFields.get(sampleName);

          String sampleTitle = getRequiredMultiValueFieldValue(sampleFields, SAMPLES_TITLE_FIELD_NAME,
            SAMPLES_HEADER_NAME);
          List<String> rawDataFiles = getRequiredRepeatedValueFieldValues(sampleFields,
            SAMPLES_RAW_DATA_FILE_FIELD_NAME, SAMPLES_HEADER_NAME);
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

          Sample sample = new Sample(sampleName, sampleTitle, rawDataFiles, celFile, expFile, chpFile, sourceName,
            organisms, characteristics, biomaterialProvider, molecule, label, description, platform);

          if (samples.containsKey(sampleName))
            throw new GEOIngestorException("multiple entires for sample " + sampleName);

          samples.put(sampleName, sample);
        }
      } else
        throw new GEOIngestorException(
          "no protocols header field named " + PROTOCOLS_HEADER_NAME + " in metadata sheet");
    } else
      throw new GEOIngestorException("no samples header field named " + SAMPLES_HEADER_NAME + " in metadata sheet");

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

  // Returns: sample name -> (field name -> [field values])
  // Some sample columns can be repeated (e.g., raw data file).
  private Map<String, Map<String, List<String>>> extractSampleFields(Sheet geoMetadataSheet, int samplesHeaderRowNumber,
    int sampledEndRowNumber) throws GEOIngestorException
  {
    Map<String, Map<String, List<String>>> sampleFields = new HashMap<>();
    List<String> headerFieldNames = new ArrayList<>();
    Row headerRow = geoMetadataSheet.getRow(samplesHeaderRowNumber);

    if (headerRow == null)
      throw new GEOIngestorException("could not find header row at row number " + samplesHeaderRowNumber);

    for (int currentColumnNumber = 0; currentColumnNumber < headerRow.getLastCellNum(); currentColumnNumber++) {
      Cell headerFieldCell = headerRow.getCell(currentColumnNumber);

      if (headerFieldCell == null)
        throw new GEOIngestorException(
          "invalid samples header cell at row " + samplesHeaderRowNumber + ", column " + currentColumnNumber);

      String headerFieldValue = getStringCellValue(headerFieldCell);

      if (headerFieldValue.isEmpty())
        throw new GEOIngestorException(
          "empty samples header cell at row " + samplesHeaderRowNumber + ", column " + currentColumnNumber);

      headerFieldNames.add(headerFieldValue);
    }

    for (int currentRowNumber = samplesHeaderRowNumber + 1;
         currentRowNumber <= sampledEndRowNumber; currentRowNumber++) {
      Row valueRow = geoMetadataSheet.getRow(currentRowNumber);
      if (valueRow != null && valueRow.getPhysicalNumberOfCells() > 0) {
        Cell sampleNameCell = valueRow.getCell(0);
        String sampleName = getStringCellValue(sampleNameCell);
        if (sampleName.isEmpty())
          throw new GEOIngestorException("empty sample name at row " + currentRowNumber);
        if (sampleFields.containsKey(sampleName))
          throw new GEOIngestorException("duplicate sample name " + sampleName + " found at row " + currentRowNumber);

        sampleFields.put(sampleName, new HashMap<>());

        for (int currentColumnNumber = 0; currentColumnNumber < headerRow.getLastCellNum(); currentColumnNumber++) {
          Cell fieldValueCell = headerRow.getCell(currentColumnNumber);
          if (fieldValueCell != null) {
            String fieldValue = getStringCellValue(fieldValueCell);
            if (!fieldValue.isEmpty() && currentColumnNumber < headerFieldNames.size()) {
              String headerFieldName = headerFieldNames.get(currentColumnNumber);
              if (sampleFields.get(sampleName).containsKey(headerFieldName)) {
                sampleFields.get(sampleName).get(headerFieldName).add(fieldValue);
              } else {
                List<String> fieldValues = new ArrayList<>();
                fieldValues.add(fieldValue);
                sampleFields.get(sampleName).put(headerFieldName, fieldValues);
              }
            }
          }
        }
      }
    }
    return sampleFields;
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
    int firstRow = sheet.getFirstRowNum();
    int lastRow = sheet.getLastRowNum();

    for (int currentRow = firstRow; currentRow <= lastRow; currentRow++) {
      Row row = sheet.getRow(currentRow);
      Cell cell = row.getCell(fieldColumnNumber);
      String value = SpreadsheetUtil.getStringCellValue(cell);
      if (fieldName.equals(value))
        return Optional.of(currentRow);
    }
    return Optional.empty();
  }

  /**
   * A field can have multiple values.
   */
  private Map<String, List<String>> findFieldValues(Sheet sheet, int fieldNameColumnNumber, int fieldValueColumnNumber,
    int startRowNumber, int finishRowNumber) throws GEOIngestorException
  {
    Map<String, List<String>> field2Values = new HashMap<>();

    for (int currentRow = startRowNumber; currentRow <= finishRowNumber; currentRow++) {
      Row row = sheet.getRow(currentRow);
      Cell fieldNameCell = row.getCell(fieldNameColumnNumber);

      if (fieldNameCell == null)
        throw new GEOIngestorException("empty field name at location " + getCellLocation(fieldNameCell));

      Cell fieldValueCell = row.getCell(fieldValueColumnNumber);

      if (fieldValueCell == null)
        throw new GEOIngestorException("empty field value at location " + getCellLocation(fieldNameCell));

      String fieldName = SpreadsheetUtil.getStringCellValue(fieldNameCell);
      if (fieldName.isEmpty())
        throw new GEOIngestorException("empty field name at location " + getCellLocation(fieldNameCell));

      String fieldValue = SpreadsheetUtil.getStringCellValue(fieldNameCell);

      if (fieldValue.isEmpty())
        throw new GEOIngestorException("empty field value at location " + getCellLocation(fieldValueCell));

      if (field2Values.containsKey(fieldName))
        field2Values.get(fieldName).add(fieldValue);
      else {
        List<String> fieldValues = new ArrayList<>();
        fieldValues.add(fieldValue);
        field2Values.put(fieldName, fieldValues);
      }
    }
    return field2Values;
  }

  /**
   * Duplicates not allowed.
   */
  private Map<String, String> findFieldValue(Sheet sheet, int fieldNameColumnNumber, int fieldValueColumnNumber,
    int startRowNumber, int finishRowNumber) throws GEOIngestorException
  {
    Map<String, String> field2Value = new HashMap<>();

    for (int currentRow = startRowNumber; currentRow <= finishRowNumber; currentRow++) {
      Row row = sheet.getRow(currentRow);
      Cell fieldNameCell = row.getCell(fieldNameColumnNumber);

      if (fieldNameCell == null)
        throw new GEOIngestorException("empty field name at location " + getCellLocation(fieldNameCell));

      Cell fieldValueCell = row.getCell(fieldValueColumnNumber);

      if (fieldValueCell == null)
        throw new GEOIngestorException("empty field value at location " + getCellLocation(fieldNameCell));

      String fieldName = SpreadsheetUtil.getStringCellValue(fieldNameCell); // Check for null cell
      if (fieldName.isEmpty())
        throw new GEOIngestorException("empty field name at location " + getCellLocation(fieldNameCell));

      String fieldValue = SpreadsheetUtil.getStringCellValue(fieldNameCell); // Check for null cell

      if (fieldValue.isEmpty())
        throw new GEOIngestorException("empty field value at location " + getCellLocation(fieldValueCell));

      if (field2Value.containsKey(fieldName))
        throw new GEOIngestorException(
          "duplicate field " + fieldName + " value at location " + getCellLocation(fieldValueCell));
      else {
        field2Value.put(fieldName, fieldValue);
      }
    }
    return field2Value;
  }

}
