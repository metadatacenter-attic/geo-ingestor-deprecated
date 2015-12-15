package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElement;
import org.metadatacenter.repository.model.StringValueElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudyAssay extends MetadataTemplateElement
{
  public static final List<String> ElementURIs = Collections
    .singletonList("http://purl.obolibrary.org/obo/BFO_0000055");

  private final StringValueElement platform;
  private final Optional<StringValueElement> measurementType;
  private final Optional<StringValueElement> technology;

  public StudyAssay(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringValueElement platform,
    Optional<StringValueElement> measurementType, Optional<StringValueElement> technology)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.platform = platform;
    this.measurementType = measurementType;
    this.technology = technology;
  }

  public StudyAssay(StringValueElement platform, Optional<StringValueElement> measurementType,
    Optional<StringValueElement> technology)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.platform = platform;
    this.measurementType = measurementType;
    this.technology = technology;
  }

  public StudyAssay(StringValueElement platform)
  {
    super(ElementURIs, generateJSONLDIdentifier(InvestigationNames.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.platform = platform;
    this.measurementType = Optional.empty();
    this.technology = Optional.empty();
  }

}
