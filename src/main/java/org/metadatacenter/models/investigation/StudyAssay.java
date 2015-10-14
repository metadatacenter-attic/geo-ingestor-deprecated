package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.URIValueElement;

import java.util.List;
import java.util.Optional;

public class StudyAssay extends MetadataTemplateElement
{
  private final URIValueElement measurementType;
  private final URIValueElement platform;
  private final URIValueElement technology;

  public StudyAssay(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, URIValueElement measurementType,
    URIValueElement platform, URIValueElement technology)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.measurementType = measurementType;
    this.platform = platform;
    this.technology = technology;
  }
}
