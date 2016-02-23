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
import java.util.List;
import java.util.Optional;

import static org.metadatacenter.repository.model.RepositoryFactory.createStringTemplateFieldInstance;

/**
 * Take a {@link GEOSubmissionMetadata} object and convert it to a list of CEDAR {@link GEOFlatTemplateInstance} template instances.
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

    // protocol
    //    List<String> growth;
    //    List<String> treatment;
    //    List<String> extract;
    //    List<String> label;
    //    List<String> hyb;
    //    List<String> scan;
    //    List<String> dataProcessing;
    //    List<String> valueDefinition;
    //    Map<String, List<String>> userDefinedFields;

    for (Sample geoSample : geoSubmissionMetadata.getSamples().values()) {
      StringTemplateFieldInstance gsm = createStringTemplateFieldInstance(geoSample.getGSM());
      StringTemplateFieldInstance platform = createStringTemplateFieldInstance(geoSample.getGPL());
      StringTemplateFieldInstance sampleTitle = createStringTemplateFieldInstance(geoSample.getTitle());
      StringTemplateFieldInstance sampleLabel = createStringTemplateFieldInstance(geoSample.getLabel());
      Optional<StringTemplateFieldInstance> sampleDescription = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(geoSample.getDescription());
      Optional<StringTemplateFieldInstance> biomaterialProvider = RepositoryFactory
        .createOptionalStringTemplateFieldInstance(geoSample.getBiomaterialProvider());

      GEOFlatTemplateInstance geoFlatTemplateInstanceTemplateInstance = new GEOFlatTemplateInstance(templateID, gse,
        seriesTitle, seriesDescription, submissionDate, publicReleaseDate, gsm, platform, sampleTitle, sampleLabel,
        sampleDescription, biomaterialProvider);
      geoFlatTemplateInstanceTemplateInstances.add(geoFlatTemplateInstanceTemplateInstance);
    }

    return geoFlatTemplateInstanceTemplateInstances;
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
