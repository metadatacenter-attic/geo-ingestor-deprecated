package org.metadatacenter.repository.model;

import java.util.List;
import java.util.Optional;

public abstract class ValueElement extends MetadataTemplateElement
{
  private final String value;

  public ValueElement(List<String> jsonLDTypes, Optional<String> jsonLDIdentifier, String value)
  {
    super(jsonLDTypes, jsonLDIdentifier);
    this.value = value;
  }

  public String getValue()
  {
    return value;
  }
}
