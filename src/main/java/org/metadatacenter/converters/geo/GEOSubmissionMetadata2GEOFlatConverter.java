package org.metadatacenter.converters.geo;

import org.metadatacenter.ingestors.geo.formats.geosoft.GEOSoftNames;
import org.metadatacenter.ingestors.geo.metadata.GEOSubmissionMetadata;
import org.metadatacenter.ingestors.geo.metadata.Protocol;
import org.metadatacenter.ingestors.geo.metadata.Sample;
import org.metadatacenter.ingestors.geo.metadata.Series;
import org.metadatacenter.models.geoflat.GEOFlatNames;
import org.metadatacenter.models.geoflat.GEOFlatTemplateInstance;
import org.metadatacenter.repository.model.DateTemplateFieldInstance;
import org.metadatacenter.repository.model.RepositoryFactory;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.metadatacenter.repository.model.RepositoryFactory.createStringTemplateFieldInstance;

/**
 * Take a {@link GEOSubmissionMetadata} object and convert it to a list of CEDAR {@link GEOFlatTemplateInstance}
 * template instances.
 * <p/>
 *
 * @see GEOSubmissionMetadata
 * @see GEOFlatTemplateInstance
 */
public class GEOSubmissionMetadata2GEOFlatConverter
{
  // TODO Too long. Clean up.
  public List<GEOFlatTemplateInstance> convertGEOSubmissionMetadata2GEOFlatTemplateInstances(
    GEOSubmissionMetadata geoSubmissionMetadata)
  {
    List<GEOFlatTemplateInstance> geoFlatTemplateInstanceTemplateInstances = new ArrayList<>();

    Series geoSeries = geoSubmissionMetadata.getSeries();
    String templateIDField = GEOFlatNames.GEOFLAT_TEMPLATE_ID;
    StringTemplateFieldInstance gseField = createStringTemplateFieldInstance(geoSeries.getGSE());
    StringTemplateFieldInstance seriesTitleField = createStringTemplateFieldInstance(geoSeries.getTitle());
    StringTemplateFieldInstance seriesDescriptionField = createStringTemplateFieldInstance(
      concatenateFieldValues(geoSeries.getSummary()));
    Optional<DateTemplateFieldInstance> submissionDateField = Optional.empty();
    Optional<DateTemplateFieldInstance> publicReleaseDateField = Optional.empty();

    Optional<Protocol> geoProtocol = geoSubmissionMetadata.getProtocol();

    List<String> growthProtocols = geoProtocol.isPresent() ?
      geoProtocol.get().getGrowthProtocols() :
      Collections.emptyList();
    Optional<String> growthProtocol = growthProtocols.isEmpty() ?
      Optional.empty() :
      Optional.of(growthProtocols.get(0));
    List<String> extractProtocols = geoProtocol.isPresent() ?
      geoProtocol.get().getExtractProtocols() :
      Collections.emptyList();
    Optional<String> extractProtocol = growthProtocols.isEmpty() ?
      Optional.empty() :
      Optional.of(extractProtocols.get(0));
    List<String> labelProtocols = geoProtocol.isPresent() ?
      geoProtocol.get().getLabelProtocol() :
      Collections.emptyList();
    Optional<String> labelProtocol = growthProtocols.isEmpty() ? Optional.empty() : Optional.of(labelProtocols.get(0));
    List<String> hybridizationProtocols = geoProtocol.isPresent() ?
      geoProtocol.get().getHybridizationProtocol() :
      Collections.emptyList();
    Optional<String> hybridizationProtocol = growthProtocols.isEmpty() ?
      Optional.empty() :
      Optional.of(hybridizationProtocols.get(0));
    List<String> scanProtocols = geoProtocol.isPresent() ?
      geoProtocol.get().getScanProtocols() :
      Collections.emptyList();
    Optional<String> scanProtocol = growthProtocols.isEmpty() ? Optional.empty() : Optional.of(scanProtocols.get(0));

    for (Sample geoSample : geoSubmissionMetadata.getSamples().values()) {
      StringTemplateFieldInstance gsmField = createStringTemplateFieldInstance(geoSample.getGSM());
      StringTemplateFieldInstance platformField = createStringTemplateFieldInstance(geoSample.getGPL());
      StringTemplateFieldInstance sampleTitleField = createStringTemplateFieldInstance(geoSample.getTitle());
      StringTemplateFieldInstance sampleLabelField = createStringTemplateFieldInstance(geoSample.getLabel());
      Optional<StringTemplateFieldInstance> sampleDescriptionField = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(geoSample.getDescription());
      Optional<StringTemplateFieldInstance> biomaterialProviderField = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(geoSample.getBiomaterialProvider());

      Map<String, String> characteristics = geoSample.getPerChannelInformation().containsKey(1) ?
        geoSample.getPerChannelInformation().get(1).getCharacteristics() :
        Collections.emptyMap();

      if (characteristics.isEmpty())
        continue; // TEMPORARY: Skip samples with no characteristics.

      Optional<String> diseaseValue = extractDiseaseFromCharacteristics(characteristics);
      Optional<String> tissueValue = extractTissueFromCharacteristics(characteristics);
      String sexValue = extractSexFromCharacteristics(characteristics);
      String ageValue = extractAgeFromCharacteristics(characteristics);

      if (!diseaseValue.isPresent() || !tissueValue.isPresent())
        continue; // TEMPORARY: Skip samples with no disease or tissue characteristics.

      System.err.println("CHAR " + characteristics);

      Optional<StringTemplateFieldInstance> diseaseField = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(diseaseValue);
      Optional<StringTemplateFieldInstance> tissueField = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(tissueValue);
      Optional<StringTemplateFieldInstance> sexField = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(sexValue);
      Optional<StringTemplateFieldInstance> ageField = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(ageValue);

      Optional<StringTemplateFieldInstance> growthProtocolField = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(growthProtocol);
      Optional<StringTemplateFieldInstance> extractProtocolField = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(extractProtocol);
      Optional<StringTemplateFieldInstance> labelProtocolField = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(labelProtocol);
      Optional<StringTemplateFieldInstance> hybridizationProtocolField = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(hybridizationProtocol);
      Optional<StringTemplateFieldInstance> scanProtocolField = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(scanProtocol);

      GEOFlatTemplateInstance geoFlatTemplateInstanceTemplateInstance = new GEOFlatTemplateInstance(templateIDField,
        gseField, seriesTitleField, seriesDescriptionField, submissionDateField, publicReleaseDateField, gsmField,
        platformField, sampleTitleField, sampleLabelField, sampleDescriptionField, biomaterialProviderField,
        diseaseField, tissueField, sexField, ageField, growthProtocolField, extractProtocolField, labelProtocolField,
        hybridizationProtocolField, scanProtocolField);

      geoFlatTemplateInstanceTemplateInstances.add(geoFlatTemplateInstanceTemplateInstance);
    }

    return geoFlatTemplateInstanceTemplateInstances;
  }

  private Optional<String> extractDiseaseFromCharacteristics(Map<String, String> characteristics)
  {
    for (String characteristicName : characteristics.keySet()) {
      if (characteristicName.equalsIgnoreCase("disease") || characteristicName.equalsIgnoreCase("diagnosis")
        || characteristicName.equalsIgnoreCase("disease state"))
        return Optional.of(characteristics.get(characteristicName));
    }
    return Optional.empty();
  }

  private Optional<String> extractTissueFromCharacteristics(Map<String, String> characteristics)
  {
    for (String characteristicName : characteristics.keySet()) {
      if (characteristicName.equalsIgnoreCase("tissue"))
        return Optional.of(characteristics.get(characteristicName));
    }
    return Optional.empty();
  }

  private String extractSexFromCharacteristics(Map<String, String> characteristics)
  {
    for (String characteristicName : characteristics.keySet()) {
      if (characteristicName.equalsIgnoreCase("sex") || characteristicName.equalsIgnoreCase("gender"))
        return characteristics.get(characteristicName);
    }
    return "";
  }

  private String extractAgeFromCharacteristics(Map<String, String> characteristics)
  {
    for (String characteristicName : characteristics.keySet()) {
      if (characteristicName.equalsIgnoreCase("age"))
        return characteristics.get(characteristicName);
    }
    return "";
  }

  private String concatenateFieldValues(List<String> fieldValues)
  {
    StringBuilder sb = new StringBuilder();
    boolean isFirst = true;
    for (String fieldValue : fieldValues) {
      if (!isFirst)
        sb.append(GEOSoftNames.MULTI_VALUE_FIELD_SEPARATOR);
      sb.append(fieldValue);
      isFirst = false;
    }
    return sb.toString();
  }
}
