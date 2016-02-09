package org.metadatacenter.repository.model;

import java.util.List;
import java.util.Optional;

public abstract class MetadataTemplateInstance extends MetadataTemplateElementInstance
{
  private final String _templateId;

  public MetadataTemplateInstance(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String templateId)
  {
    super(createDefaultJSONLDContext(), jsonLDTypes, jsonLDIdentifier);
    this._templateId = templateId;
  }

  public String getTemplateID()
  {
    return _templateId;
  }
}
