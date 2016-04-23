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
  public List<GEOFlatTemplateInstance> convertGEOSubmissionMetadata2GEOFlatTemplateInstances(
    GEOSubmissionMetadata geoSubmissionMetadata)
  {
    List<GEOFlatTemplateInstance> geoFlatTemplateInstanceTemplateInstances = new ArrayList<>();

    Optional<Protocol> geoProtocol = geoSubmissionMetadata.getProtocol();

    Series geoSeries = geoSubmissionMetadata.getSeries();
    String templateID = GEOFlatNames.GEOFLAT_TEMPLATE_ID;
    StringTemplateFieldInstance gse = createStringTemplateFieldInstance(geoSeries.getGSE());
    StringTemplateFieldInstance seriesTitle = createStringTemplateFieldInstance(geoSeries.getTitle());
    StringTemplateFieldInstance seriesDescription = createStringTemplateFieldInstance(
      concatenateFieldValues(geoSeries.getSummary()));
    Optional<DateTemplateFieldInstance> submissionDate = Optional.empty();
    Optional<DateTemplateFieldInstance> publicReleaseDate = Optional.empty();

    for (Sample geoSample : geoSubmissionMetadata.getSamples().values()) {
      StringTemplateFieldInstance gsm = createStringTemplateFieldInstance(geoSample.getGSM());
      StringTemplateFieldInstance platform = createStringTemplateFieldInstance(geoSample.getGPL());
      StringTemplateFieldInstance sampleTitle = createStringTemplateFieldInstance(geoSample.getTitle());
      StringTemplateFieldInstance sampleLabel = createStringTemplateFieldInstance(geoSample.getLabel());
      Optional<StringTemplateFieldInstance> sampleDescription = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(geoSample.getDescription());
      Optional<StringTemplateFieldInstance> biomaterialProvider = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(geoSample.getBiomaterialProvider());

      Map<String, String> characteristics = geoSample.getPerChannelInformation().containsKey(1) ?
        geoSample.getPerChannelInformation().get(1).getCharacteristics() :
        Collections.emptyMap();

      if (characteristics.isEmpty())
        continue;

      Optional<String> diseaseValue = extractDiseaseFromCharacteristics(characteristics);

      if (!diseaseValue.isPresent())
        continue;

      System.err.println("CHAR" + characteristics);

      Optional<StringTemplateFieldInstance> disease = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(diseaseValue);
      Optional<StringTemplateFieldInstance> tissue = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(extractTissueFromCharacteristics(characteristics));
      Optional<StringTemplateFieldInstance> sex = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(extractSexFromCharacteristics(characteristics));
      Optional<StringTemplateFieldInstance> age = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(extractAgeFromCharacteristics(characteristics));

      GEOFlatTemplateInstance geoFlatTemplateInstanceTemplateInstance = new GEOFlatTemplateInstance(templateID, gse,
        seriesTitle, seriesDescription, submissionDate, publicReleaseDate, gsm, platform, sampleTitle, sampleLabel,
        sampleDescription, biomaterialProvider, disease, tissue, sex, age);
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

  private Optional<String> extractSexFromCharacteristics(Map<String, String> characteristics)
  {
    for (String characteristicName : characteristics.keySet()) {
      if (characteristicName.equalsIgnoreCase("sex") || characteristicName.equalsIgnoreCase("gender"))
        return Optional.of(characteristics.get(characteristicName));
    }
    return Optional.empty();
  }

  private Optional<String> extractAgeFromCharacteristics(Map<String, String> characteristics)
  {
    for (String characteristicName : characteristics.keySet()) {
      if (characteristicName.equalsIgnoreCase("age"))
        return Optional.of(characteristics.get(characteristicName));
    }
    return Optional.empty();
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
