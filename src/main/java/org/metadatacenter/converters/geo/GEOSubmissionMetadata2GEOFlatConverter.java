package org.metadatacenter.converters.geo;

import org.metadatacenter.ingestors.geo.formats.geosoft.GEOSoftNames;
import org.metadatacenter.ingestors.geo.metadata.GEOSubmissionMetadata;
import org.metadatacenter.ingestors.geo.metadata.Series;
import org.metadatacenter.models.geoflat.GEOFlat;
import org.metadatacenter.models.geoflat.GEOFlatNames;
import org.metadatacenter.repository.model.DateTemplateFieldInstance;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.List;
import java.util.Optional;

import static org.metadatacenter.repository.model.RepositoryFactory.createStringValueElement;

/**
 * Take a {@link GEOSubmissionMetadata} object and convert it to a CEDAR {@link GEOFlat} template instance.
 * <p>
 *
 * @see GEOSubmissionMetadata
 * @see GEOFlat
 */
public class GEOSubmissionMetadata2GEOFlatConverter
{
  public GEOFlat convertGEOSubmissionMetadata2GEOFlat(GEOSubmissionMetadata geoSubmissionMetadata)
  {
    Series geoSeries = geoSubmissionMetadata.getSeries();

    String templateID = GEOFlatNames.GEOFLAT_TEMPLATE_ID;
    StringTemplateFieldInstance title = createStringValueElement(geoSeries.getTitle());
    StringTemplateFieldInstance description = createStringValueElement(concatenateFieldValues(geoSeries.getSummary()));
    StringTemplateFieldInstance identifier = createStringValueElement(geoSeries.getTitle());
    Optional<DateTemplateFieldInstance> submissionDate = Optional.empty();
    Optional<DateTemplateFieldInstance> publicReleaseDate = Optional.empty();

    return new GEOFlat(templateID, title, description, identifier, submissionDate, publicReleaseDate);
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
