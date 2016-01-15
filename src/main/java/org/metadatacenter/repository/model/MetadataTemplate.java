package org.metadatacenter.repository.model;

import java.util.List;
import java.util.Optional;

public abstract class MetadataTemplate extends MetadataTemplateElement
{
  private final String templateId;

  public MetadataTemplate(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String templateId)
  {
    super(createDefaultJSONLDContext(), jsonLDTypes, jsonLDIdentifier);
    this.templateId = templateId;
  }

  public String getTemplateID()
  {
    return templateId;
  }
}
