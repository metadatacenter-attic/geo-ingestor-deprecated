package org.metadatacenter.models.investigation;

import org.metadatacenter.repository.model.MetadataTemplateElementInstance;
import org.metadatacenter.repository.model.Namespaces;
import org.metadatacenter.repository.model.StringTemplateFieldInstance;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudyAssay extends MetadataTemplateElementInstance
{
  public static final List<String> ElementURIs = Collections
    .singletonList("http://purl.obolibrary.org/obo/BFO_0000055");

  private final StringTemplateFieldInstance platform;
  private final Optional<StringTemplateFieldInstance> measurementType;
  private final Optional<StringTemplateFieldInstance> technology;

  public StudyAssay(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, StringTemplateFieldInstance platform,
    Optional<StringTemplateFieldInstance> measurementType, Optional<StringTemplateFieldInstance> technology)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.platform = platform;
    this.measurementType = measurementType;
    this.technology = technology;
  }

  public StudyAssay(StringTemplateFieldInstance platform, Optional<StringTemplateFieldInstance> measurementType,
    Optional<StringTemplateFieldInstance> technology)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.platform = platform;
    this.measurementType = measurementType;
    this.technology = technology;
  }

  public StudyAssay(StringTemplateFieldInstance platform)
  {
    super(ElementURIs, generateJSONLDIdentifier(Namespaces.TEMPLATE_ELEMENT_INSTANCES_URI_BASE));
    this.platform = platform;
    this.measurementType = Optional.empty();
    this.technology = Optional.empty();
  }

}
