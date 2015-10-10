package org.metadatacenter.converters.geo;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.metadatacenter.converters.geo.metadata.GEOMetadata;
import org.metadatacenter.converters.geo.metadata.Platform;
import org.metadatacenter.converters.geo.metadata.Protocol;
import org.metadatacenter.converters.geo.metadata.Sample;
import org.metadatacenter.converters.geo.metadata.Series;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.metadatacenter.converters.geo.GEONames.FIELD_NAMES_COLUMN_NUMBER;
import static org.metadatacenter.converters.geo.GEONames.FIELD_VALUES_COLUMN_NUMBER;
import static org.metadatacenter.converters.geo.GEONames.GEO_METADATA_SHEET_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_CATALOG_NUMBER_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_COATING_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_CONTRIBUTOR_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_DESCRIPTION_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_DISTRIBUTION_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_HEADER_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_MANUFACTURER_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_MANUFACTURE_PROTOCOL_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_ORGANISM_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_PUBMED_ID_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_SUPPORT_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_TECHNOLOGY_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_TITLE_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PLATFORM_WEB_LINK_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PROTOCOLS_HEADER_NAME;
import static org.metadatacenter.converters.geo.GEONames.PROTOCOL_DATA_PROCESSING_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PROTOCOL_EXTRACT_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PROTOCOL_GROWTH_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PROTOCOL_HYB_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PROTOCOL_LABEL_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PROTOCOL_SCAN_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PROTOCOL_TREATMENT_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PROTOCOL_VALUE_DEFINITION_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.PlatformFieldNames;
import static org.metadatacenter.converters.geo.GEONames.SAMPLES_HEADER_NAME;
import static org.metadatacenter.converters.geo.GEONames.SERIES_CONTRIBUTOR_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.SERIES_HEADER_NAME;
import static org.metadatacenter.converters.geo.GEONames.SERIES_OVERALL_DESIGN_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.SERIES_PUBMED_ID_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.SERIES_SUMMARY_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.SERIES_TITLE_FIELD_NAME;
import static org.metadatacenter.converters.geo.GEONames.SeriesFieldNames;
import static org.metadatacenter.converters.geo.SSUtil.findFieldRowNumber;
import static org.metadatacenter.converters.geo.SSUtil.findFieldValue;
import static org.metadatacenter.converters.geo.SSUtil.findFieldValues;
import static org.metadatacenter.converters.geo.SSUtil.getStringCellValue;

public class GEOSpreadsheetHandler
{
  public GEOSpreadsheetHandler(String spreadsheetFileName) throws GEOConverterException
  {
    InputStream spreadsheetStream = SSUtil.openSpreadsheetInputStream(spreadsheetFileName);
    Workbook workbook = SSUtil.createWorkbook(spreadsheetStream);
    Sheet geoMetadataSheet = getGEOMetadataSheet(workbook);
    GEOMetadata geoMetadata = extractMetadata(geoMetadataSheet);
  }

  private GEOMetadata extractMetadata(Sheet geoMetadataSheet) throws GEOConverterException
  {
    Series series = extractSeries(geoMetadataSheet);
    Map<String, Sample> samples = extractSamples(geoMetadataSheet);
    Protocol procotol = extractProtocol(geoMetadataSheet);
    Optional<Platform> platform = extractPlatform(geoMetadataSheet);

    return new GEOMetadata(series, samples, procotol, platform);
  }

  private Series extractSeries(Sheet geoMetadataSheet) throws GEOConverterException
  {
    Map<String, List<String>> seriesFields = extractSeriesFields(geoMetadataSheet);
    String title = getRequiredMultiValueFieldValue(seriesFields, SERIES_TITLE_FIELD_NAME, SERIES_HEADER_NAME);
    String summary = getRequiredMultiValueFieldValue(seriesFields, SERIES_SUMMARY_FIELD_NAME, SERIES_HEADER_NAME);
    String overallDesign = getRequiredMultiValueFieldValue(seriesFields, SERIES_OVERALL_DESIGN_FIELD_NAME,
        SERIES_HEADER_NAME);
    List<String> contributors = getMultiValueFieldValues(seriesFields, SERIES_CONTRIBUTOR_FIELD_NAME);
    List<String> pubmedIDs = getMultiValueFieldValues(seriesFields, SERIES_PUBMED_ID_FIELD_NAME);
    Map<String, Map<String, String>> variables = new HashMap<>(); // TODO
    Map<String, List<String>> repeat = new HashMap<>(); // TODO

    return new Series(title, summary, overallDesign, contributors, pubmedIDs, variables, repeat);
  }

  private String getRequiredSingleValueFieldValue(Map<String, String> fields, String fieldName,
      String fieldCollectionName) throws GEOConverterException
  {
    Optional<String> fieldValue = getSingleValueFieldValue(fields, fieldName);

    if (fieldValue.isPresent())
      return fieldValue.get();
    else
      throw new GEOConverterException("could not find required field " + fieldName + " in " + fieldCollectionName);
  }

  private String getRequiredMultiValueFieldValue(Map<String, List<String>> fields, String fieldName,
      String fieldCollectionName) throws GEOConverterException
  {
    Optional<String> fieldValue = getMultiValueFieldValue(fields, fieldName, fieldCollectionName);

    if (fieldValue.isPresent())
      return fieldValue.get();
    else
      throw new GEOConverterException("could not find required field " + fieldName + " in " + fieldCollectionName);
  }

  private Optional<String> getSingleValueFieldValue(Map<String, String> fields, String fieldName)
      throws GEOConverterException
  {
    if (fields.containsKey(fieldName)) {
      return Optional.of(fields.get(fieldName));
    } else
      return Optional.empty();
  }

  private Optional<String> getMultiValueFieldValue(Map<String, List<String>> fields, String fieldName,
      String fieldCollectionName) throws GEOConverterException
  {
    if (fields.containsKey(fieldName)) {
      if (fields.get(fieldName).size() == 1)
        return Optional.of(fields.get(fieldName).iterator().next());
      else
        throw new GEOConverterException(
            "not expecting multiple values for field " + fieldName + " in " + fieldCollectionName);
    } else
      return Optional.empty();
  }

  private List<String> getMultiValueFieldValues(Map<String, List<String>> fields, String fieldName)
      throws GEOConverterException
  {
    if (fields.containsKey(fieldName))
      return fields.get(fieldName);
    else
      return Collections.emptyList();
  }

  private List<String> getRequiredMultiValueFieldValues(Map<String, List<String>> fields, String fieldName,
      String fieldCollectionName) throws GEOConverterException
  {
    if (fields.containsKey(fieldName))
      return fields.get(fieldName);
    else
      throw new GEOConverterException("no values for field " + fieldName + " in " + fieldCollectionName);
  }

  private Map<String, List<String>> extractSeriesFields(Sheet geoMetadataSheet) throws GEOConverterException
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
          throw new GEOConverterException("no series fields found in metadata spreadsheet");

        if (!SeriesFieldNames.containsAll(fieldName2Values.keySet()))
          throw new GEOConverterException(
              "unknown series fields " + fieldName2Values.keySet().removeAll(SeriesFieldNames));

        return fieldName2Values;
      } else
        throw new GEOConverterException("no samples header field named " + SAMPLES_HEADER_NAME + " in metadata sheet");
    } else
      throw new GEOConverterException("no series header field named " + SERIES_HEADER_NAME + " in metadata sheet");
  }

  private Protocol extractProtocol(Sheet geoMetadataSheet) throws GEOConverterException
  {
    Map<String, String> protocolFields = extractProtocolFields(geoMetadataSheet);

    if (protocolFields.isEmpty())
      throw new GEOConverterException("no protocols found in metadata sheet");

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

  //  public final static String PLATFORM_TITLE_FIELD_NAME = "title";
  //  public final static String PLATFORM_DISTRIBUTION_FIELD_NAME = "distribution";
  //  public final static String PLATFORM_TECHNOLOGY_FIELD_NAME = "technology";
  //  public final static String PLATFORM_ORGANISM_FIELD_NAME = "organism";
  //  public final static String PLATFORM_MANUFACTURER_FIELD_NAME = "manufacturer";
  //  public final static String PLATFORM_MANUFACTURE_PROTOCOL_FIELD_NAME = "manufacture protocol";
  //  public final static String PLATFORM_DESCRIPTION_FIELD_NAME = "description";
  //  public final static String PLATFORM_CATALOG_NUMBER_FIELD_NAME = "catalog number";
  //  public final static String PLATFORM_WEB_LINK_FIELD_NAME = "web link";
  //  public final static String PLATFORM_SUPPORT_FIELD_NAME = "support";
  //  public final static String PLATFORM_COATING_FIELD_NAME = "coating";
  //  public final static String PLATFORM_CONTRIBUTOR_FIELD_NAME = "contributor";
  //  public final static String PLATFORM_PUBMED_ID_FIELD_NAME = "pubmed id";

  private Optional<Platform> extractPlatform(Sheet geoMetadataSheet) throws GEOConverterException
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

      return Optional
          .of(new Platform(title, distribution, technology, organism, manufacturer, manufacturerProtocol, description,
              catalogNumber, webLink, support, coating, contributor, pubmedID));
    } else
      return Optional.empty();
  }

  private Map<String, List<String>> extractPlatformFields(Sheet geoMetadataSheet) throws GEOConverterException
  {
    Optional<Integer> platformHeaderRowNumber = findFieldRowNumber(geoMetadataSheet, PLATFORM_HEADER_NAME,
        FIELD_NAMES_COLUMN_NUMBER);

    if (platformHeaderRowNumber.isPresent()) {

      Map<String, List<String>> fieldName2Values = findFieldValues(geoMetadataSheet, FIELD_NAMES_COLUMN_NUMBER,
          FIELD_VALUES_COLUMN_NUMBER, platformHeaderRowNumber.get() + 1, geoMetadataSheet.getLastRowNum());

      if (!fieldName2Values.isEmpty()) {

        if (!PlatformFieldNames.containsAll(fieldName2Values.keySet()))
          throw new GEOConverterException(
              "unknown platform fields " + fieldName2Values.keySet().removeAll(PlatformFieldNames));

        return fieldName2Values;
      } else
        return Collections.emptyMap();
    } else
      return Collections.emptyMap();
  }

  private Map<String, String> extractProtocolFields(Sheet geoMetadataSheet) throws GEOConverterException
  {
    Optional<Integer> seriesHeaderRowNumber = findFieldRowNumber(geoMetadataSheet, PROTOCOLS_HEADER_NAME,
        FIELD_NAMES_COLUMN_NUMBER);
    if (seriesHeaderRowNumber.isPresent()) {

      Map<String, String> fieldName2Value = findFieldValue(geoMetadataSheet, FIELD_NAMES_COLUMN_NUMBER,
          FIELD_VALUES_COLUMN_NUMBER, seriesHeaderRowNumber.get() + 1, geoMetadataSheet.getLastRowNum());

      if (fieldName2Value.isEmpty())
        throw new GEOConverterException("no protocol fields found in metadata spreadsheet");

      if (!SeriesFieldNames.containsAll(fieldName2Value.keySet()))
        throw new GEOConverterException(
            "unknown series fields " + fieldName2Value.keySet().removeAll(SeriesFieldNames));

      return fieldName2Value;
    } else
      throw new GEOConverterException(
          "no protocols header field named " + PROTOCOLS_HEADER_NAME + " in metadata sheet");
  }

  private Map<String, Sample> extractSamples(Sheet geoMetadataSheet) throws GEOConverterException
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

        Map<String, List<String>> sampleFields = extractSampleFields(geoMetadataSheet, samplesStartRowNumber, sampledEndRowNumber);

      } else
        throw new GEOConverterException(
            "no protocols header field named " + PROTOCOLS_HEADER_NAME + " in metadata sheet");
    } else
      throw new GEOConverterException("no samples header field named " + SAMPLES_HEADER_NAME + " in metadata sheet");

    if (samples.isEmpty())
      throw new GEOConverterException("no samples found in metadata sheet");

    return samples;
  }

  private Map<String, List<String>> extractSampleFields(Sheet geoMetadataSheet, int samplesHeaderRowNumber,
      int sampledEndRowNumber) throws GEOConverterException
  {
    Map<String, List<String>> sampleFields = new HashMap<>();
    List<String> headerFieldNames = new ArrayList<>();
    Row headerRow = geoMetadataSheet.getRow(samplesHeaderRowNumber);

    if (headerRow == null)
      throw new GEOConverterException("could not find header row at row number " + samplesHeaderRowNumber);

    for (int currentColumnNumber = 0; currentColumnNumber < headerRow.getLastCellNum(); currentColumnNumber++) {
      Cell headerFieldCell = headerRow.getCell(currentColumnNumber);

      if (headerFieldCell == null)
        throw new GEOConverterException(
            "invalid samples header cell at row " + samplesHeaderRowNumber + ", column " + currentColumnNumber);

      String headerFieldValue = getStringCellValue(headerFieldCell);

      if (headerFieldValue.isEmpty())
        throw new GEOConverterException(
            "empty samples header cell at row " + samplesHeaderRowNumber + ", column " + currentColumnNumber);

      headerFieldNames.add(headerFieldValue);
    }

    for (int currentRowNumber = samplesHeaderRowNumber + 1;
         currentRowNumber <= sampledEndRowNumber; currentRowNumber++) {
      Row valueRow = geoMetadataSheet.getRow(currentRowNumber);
      if (valueRow != null) {
        for (int currentColumnNumber = 0; currentColumnNumber < headerRow.getLastCellNum(); currentColumnNumber++) {
          Cell fieldValueCell = headerRow.getCell(currentColumnNumber);
          if (fieldValueCell != null) {
            String fieldValue = getStringCellValue(fieldValueCell);
            if (!fieldValue.isEmpty() && currentColumnNumber < headerFieldNames.size()) {
              String headerFieldName = headerFieldNames.get(currentColumnNumber);
              if (sampleFields.containsKey(headerFieldName)) {
                sampleFields.get(headerFieldName).add(fieldValue);
              } else {
                List<String> fieldValues = new ArrayList<>();
                fieldValues.add(fieldValue);
                sampleFields.put(headerFieldName, fieldValues);
              }
            }
          }
        }
      }
    }
    return sampleFields;
  }

  private Sheet getGEOMetadataSheet(Workbook workbook) throws GEOConverterException
  {
    Sheet metadataSheet = workbook.getSheet(GEO_METADATA_SHEET_NAME);

    if (metadataSheet == null)
      throw new GEOConverterException(
          "spreadsheet does not contain a GEO metadata template sheet called " + GEO_METADATA_SHEET_NAME);
    else
      return metadataSheet;
  }
}
