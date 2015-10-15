package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.URIValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudyAssay extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections.singletonList(InvestigationNames.URI_BASE + "StudyAssay");

  private final URIValueElement measurementType;
  private final Optional<URIValueElement> platform;
  private final Optional<URIValueElement> technology;

  public StudyAssay(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, URIValueElement measurementType,
    Optional<URIValueElement> platform, Optional<URIValueElement> technology)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.measurementType = measurementType;
    this.platform = platform;
    this.technology = technology;
  }

  public StudyAssay(URIValueElement measurementType, Optional<URIValueElement> platform,
    Optional<URIValueElement> technology)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.URI_BASE));
    this.measurementType = measurementType;
    this.platform = platform;
    this.technology = technology;
  }
}
