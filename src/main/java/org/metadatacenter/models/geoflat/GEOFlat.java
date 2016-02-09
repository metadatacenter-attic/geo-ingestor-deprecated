package org.metadatacenter.models.geoflat;

import org.metadatacenter.repository.model.DateTemplateFieldInstance;
import org.metadatacenter.repository.model.MetadataTemplateInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GEOFlat extends MetadataTemplateInstance
{
  public static final List<String> ElementURIs = Collections.singletonList(Namespaces.TEMPLATE_URI_BASE + "GEOFlat");

  private final StringTemplateFieldInstance title;
  private final StringTemplateFieldInstance description;
  private final StringTemplateFieldInstance identifier;
  private final Optional<DateTemplateFieldInstance> submissionDate;
  private final Optional<DateTemplateFieldInstance> publicReleaseDate;

  public GEOFlat(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String templateID,
    StringTemplateFieldInstance title, StringTemplateFieldInstance description, StringTemplateFieldInstance identifier,
    Optional<DateTemplateFieldInstance> submissionDate, Optional<DateTemplateFieldInstance> publicReleaseDate)
  {
    super(jsonLDTypes, jsonLDIdentifier, templateID);
    this.title = title;
    this.description = description;
    this.identifier = identifier;
    this.submissionDate = submissionDate;
    this.publicReleaseDate = publicReleaseDate;
  }

  public GEOFlat(String templateID, StringTemplateFieldInstance title, StringTemplateFieldInstance description,
    StringTemplateFieldInstance identifier, Optional<DateTemplateFieldInstance> submissionDate,
    Optional<DateTemplateFieldInstance> publicReleaseDate)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_INSTANCES_URI_BASE), templateID);
    this.title = title;
    this.description = description;
    this.identifier = identifier;
    this.submissionDate = submissionDate;
    this.publicReleaseDate = publicReleaseDate;
  }
}
