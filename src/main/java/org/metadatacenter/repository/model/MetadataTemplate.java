package org.metadatacenter.repository.model;

import java.util.List;
import java.util.Optional;

public abstract class MetadataTemplate extends MetadataTemplateElement
{
  private final String template_id;

  public MetadataTemplate(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String template_id)
  {
    super(createDefaultJSONLDContext(), jsonLDTypes, jsonLDIdentifier);
    this.template_id = template_id;
  }

  public String getTemplateID()
  {
    return template_id;
  }
}
